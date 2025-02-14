package com.practice.favorite.member.dto;

import com.practice.favorite.member.domain.Member;
import lombok.Getter;

@Getter
public class MemberInfoResponse {

  private String email;

  public MemberInfoResponse(Member member) {
    this.email = member.getEmail();
  }
}
