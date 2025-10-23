package com.example.test123;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

  // GLOBAL

  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버에 문제가 발생했습니다."),

  // AUTH
  // AUTH (인증/권한 부여 관련 오류)
  AUTH_FAILED(HttpStatus.UNAUTHORIZED, "아이디 또는 비밀번호를 확인해 주세요."),

  NETWORK_CONNECTION_ERROR(HttpStatus.SERVICE_UNAVAILABLE, "포털 서버 연결에 실패했습니다. 잠시 후 다시 시도해 주세요."),

  PARSING_STRUCTURE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "포털 시스템 구조가 변경되어 정보를 가져올 수 없습니다. 관리자에게 문의하세요.");

  private final HttpStatus status;
  private final String message;
}