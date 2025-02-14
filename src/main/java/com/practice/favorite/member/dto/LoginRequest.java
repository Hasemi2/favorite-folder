package com.practice.favorite.member.dto;

import static com.practice.favorite.error.ErrorMessage.BAD_REQUEST_EMAIL_FORMAT;
import static com.practice.favorite.error.ErrorMessage.BAD_REQUEST_EMAIL_IS_NOT_BLANK;
import static com.practice.favorite.error.ErrorMessage.BAD_REQUEST_PASSWORD_IS_NOT_BLANK;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Getter;

@Schema(description = "로그인 요청 request")
@Getter
public class LoginRequest {

  @Email(message = BAD_REQUEST_EMAIL_FORMAT)
  @NotBlank(message = BAD_REQUEST_EMAIL_IS_NOT_BLANK)
  @Schema(description = "이메일", example = "susemi@susemi.com")
  private String email;

  @NotBlank(message = BAD_REQUEST_PASSWORD_IS_NOT_BLANK)
  @Schema(description = "패스워드", example = "12345")
  private String password;
}
