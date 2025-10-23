package com.example.test123.domain.auth.service;

import com.example.test123.domain.auth.dto.LoginRequest;
import com.example.test123.domain.auth.dto.SejongMemberInfo;
import com.example.test123.domain.auth.exception.AuthenticationFailedException;
import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.SocketTimeoutException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;
@Slf4j
@Service
public class SejongPortalLoginService {


  /**
   * 1단계: 로그인 성공 여부만 확인
   */
  public OkHttpClient validateLogin(LoginRequest loginRequest) throws IOException {

    //클라이언트를 생성하고 로그인시도를 함(실패시에는 authenticate exception을 던짐
    OkHttpClient client = buildOkHttpClient();
    LoginPortal(client,loginRequest);
    return client;
  }

  /**
   * 2단계: 로그인 성공 후 학생 정보 조회
   */
  public SejongMemberInfo loginAndGetMemberInfo(LoginRequest loginRequest) {
    try {
      // 1. 인증 및 클라이언트 획득 (ID/PW 오류 시 RuntimeException 발생)
      // authenticateAndGetClient 내부에서 SSO_AUTHENTICATION_FAILED_ID_PW_MISMATCH 발생 가능
      OkHttpClient client = validateLogin(loginRequest);

      // 2. 학생 정보 페이지 접근
      final String statusUrl = "https://classic.sejong.ac.kr/classic/reading/status.do?auty=2";
      Request statusReq = new Request.Builder().url(statusUrl).get().build();

      try (Response statusResp = client.newCall(statusReq).execute()) {

        //HTTP 상태 코드 검사 (SSO가 제대로 되지 않았을 경우) 예외처리 따로 클래스 만들어서 진행해야함
        if (!statusResp.isSuccessful()) {
          // 시스템 오류: HTTP 통신 문제
          throw new RuntimeException("FAILED_FETCH_INFO_HTTP_ERROR: " + statusResp.code());
        }

        String html = statusResp.body().string();

        // 3. 파싱 (파싱 실패 시 SEJONG_PORTAL_PARSING_FAILED RuntimeException 발생)//파실실패 오류 throw할거야
        return parseStudentInfo(html);
      }

    } catch (IOException e) {
      // 네트워크 타임아웃, 연결 끊김 등 IO/시스템 오류
      // 네트워크 오류임을 명확히 알리는 새로운 예외를 던집니다.
      throw new RuntimeException("NETWORK_CONNECTION_ERROR", e);

    } catch (RuntimeException e) {
      // SSO_AUTHENTICATION_FAILED_ID_PW_MISMATCH (인증 실패)
      // SEJONG_PORTAL_PARSING_FAILED (파싱 구조 오류)
      // 이미 명확한 메시지를 가진 RuntimeException이므로 그대로 상위로 다시 던집니다.
      throw e;
    }
  }


  /**
   * HTML 파싱
   */
  private SejongMemberInfo parseStudentInfo(String html) {
    Document doc = null;
    try {
      doc = Jsoup.parse(html);
    } catch (Exception e) {
      // HTML 파싱 자체에 문제가 있을 경우 (입력된 HTML이 유효하지 않음)
      throw new RuntimeException("SEJONG_PORTAL_PARSING_FAILED: Invalid HTML format.", e);
    }

    String studentId = null;
    String studentName = null;

    // 학번 추출 로직
    Element studentIdHeader = doc.select("th:contains(학번)").first();
    if (studentIdHeader != null) {
      Element studentIdValueCell = studentIdHeader.nextElementSibling();
      if (studentIdValueCell != null) studentId = studentIdValueCell.text().trim();
    }

    // 이름 추출 로직
    Element studentNameHeader = doc.select("th:contains(이름)").first();
    if (studentNameHeader != null) {
      Element studentNameValueCell = studentNameHeader.nextElementSibling();
      if (studentNameValueCell != null) studentName = studentNameValueCell.text().trim();
    }

    // 🌟 핵심 수정: 데이터 추출에 실패한 경우 예외 발생 🌟
    if (studentId == null || studentName == null) {
      // SSO 인증은 성공했지만, 파싱 로직이 깨졌거나 (선택자 문제)
      // 데이터가 없는 경우 (예외적인 상태) 입니다.
      throw new RuntimeException("SEJONG_PORTAL_PARSING_FAILED: Student info (ID/Name) not found in HTML.");
    }

    // 성공 시, 정상적인 MemberInfo 객체를 반환합니다.
    return SejongMemberInfo.builder()
        .studentId(studentId)
        .studentName(studentName)
        .build();
  }

  public void LoginPortal(OkHttpClient okHttpClient, LoginRequest loginRequestDto) throws IOException {

    final String loginUrl = "https://portal.sejong.ac.kr/jsp/login/login_action.jsp";

    RequestBody formBody = new FormBody.Builder()
        .add("mainLogin", "Y")
        .add("rtUrl", "https://classic.sejong.ac.kr/classic/index.do")
        .add("id", loginRequestDto.getSejongPortalId())
        .add("password", loginRequestDto.getSejongPortalPw())
        .build();

    // 2. 요청 객체 (Request) 생성
    Request loginRequest = new Request.Builder()
        .url(loginUrl)
        .header("Referer", "https://portal.sejong.ac.kr/")
        .header("Cookie", "chknos=false")
        .post(formBody)
        .build();

    // 3. 응답 객체 (Response) 생성
    Response loginResponse = executeWithRetry(okHttpClient, loginRequest);
    loginResponse.body().string();
    Headers responseHeaders = loginResponse.headers();
    log.info("응답 상태 코드: {}", loginResponse.code());
    log.info("Location 헤더: {}", responseHeaders.get("Location"));
    log.info("Set-Cookie 헤더 목록: {}", responseHeaders.values("Set-Cookie"));
    loginResponse.close();

    // 핵심: 응답 헤더에서 Set-Cookie 목록을 가져옵니다.
    List<String> setCookieHeaders = responseHeaders.values("Set-Cookie");

    // 1. 성공 시 설정되는 고유한 쿠키 이름 = ssotoken.

    boolean authCookieFound = setCookieHeaders.stream()
        .anyMatch(cookie ->
            cookie.contains("ssotoken")
        );
    // 2. 인증 성공 쿠키가 설정되지 않았다면 실패로 간주
    if (!authCookieFound) {
      // ID/PW 불일치로 인해 SSO 인증 쿠키 획득 실패
      throw new AuthenticationFailedException();
    }
  }

  public Response executeWithRetry(OkHttpClient client, Request loginRequest) throws IOException {
    Response response;
    int tryCount = 0;
    while (tryCount < 3) {
      try {
        response = client.newCall(loginRequest).execute();
        return response;
      } catch (SocketTimeoutException e){
        tryCount++;
      }
    }
    throw new IOException("세종대 API 연결 오류");
  }

  public OkHttpClient buildOkHttpClient() {
    CookieManager cookieManager = new CookieManager();
    cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
    OkHttpClient client = new OkHttpClient.Builder()
        .cookieJar(new JavaNetCookieJar(cookieManager))
        .build();
    return client;
  }

}