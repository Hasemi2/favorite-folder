package com.practice.favorite.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "로그인 response")
@Getter
public class LoginResponse {

  @Schema(description = "엑세스 토큰")
  private final String accessToken;

  public LoginResponse(String accessToken) {
    this.accessToken = accessToken;
  }
}
