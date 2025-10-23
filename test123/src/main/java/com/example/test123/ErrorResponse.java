package com.example.test123;


import lombok.Builder;

@Builder
public record ErrorResponse(ErrorCode errorCode, String errorMessage) {

}
