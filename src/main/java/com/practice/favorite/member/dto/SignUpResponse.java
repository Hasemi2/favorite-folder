package com.practice.favorite.member.dto;

import com.practice.favorite.member.domain.Member;
import lombok.Getter;

@Getter
public class SignUpResponse {

  private final Long id;
  private final String email;

  public SignUpResponse(Member member) {
    this.id = member.getId();
    this.email = member.getEmail();
  }
}
