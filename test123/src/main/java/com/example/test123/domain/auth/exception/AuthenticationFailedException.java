package com.example.test123.domain.auth.exception;


import com.example.test123.CustomException;
import com.example.test123.ErrorCode;
import lombok.Getter;

@Getter
public class AuthenticationFailedException extends CustomException {

  public AuthenticationFailedException() {
    super(ErrorCode.AUTH_FAILED);
  }

}
