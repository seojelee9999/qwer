package com.example.test123.domain.auth.controller;

import com.example.test123.domain.auth.dto.AuthResponse;
import com.example.test123.domain.auth.dto.LoginRequest;
import com.example.test123.domain.auth.service.AuthService;
import com.example.test123.domain.auth.service.SejongPortalLoginService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

  private final AuthService authService;

  private static final String REFRESH_TOKEN_COOKIE_NAME = "refreshToken";
  private static final int REFRESH_TOKEN_COOKIE_MAX_AGE = 7 * 24 * 60 * 60; // 7 days
  private final SejongPortalLoginService sejongPortalLoginService;

  /**
   * 통합 로그인 API
   * - DB 조회 → 기존/신규 사용자 분기 처리
   * - Access Token: JSON Response Body
   * - Refresh Token: HTTP-Only Cookie
   */
  @PostMapping("/login")
  public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) throws IOException {

    AuthResponse authResponse = authService.login(loginRequest);

    // Refresh Token 생성 (AuthResponse에는 포함되지 않음)

    log.info("로그인 성공 - 학번: {}, 신규유저: {}",
        authResponse.getStudentNumber(), authResponse.isNewUser());

    return ResponseEntity.ok(authResponse);
  }
}