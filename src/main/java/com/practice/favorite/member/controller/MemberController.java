package com.practice.favorite.member.controller;

import com.practice.favorite.member.dto.LoginRequest;
import com.practice.favorite.member.dto.LoginResponse;
import com.practice.favorite.member.dto.MemberInfoResponse;
import com.practice.favorite.member.dto.SignUpRequest;
import com.practice.favorite.member.dto.SignUpResponse;
import com.practice.favorite.member.service.MemberService;
import com.practice.favorite.security.CustomUserDetailsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping
@Tag(name = "01. 회원 관리")
public class MemberController {

  private final MemberService memberService;
  private final CustomUserDetailsService customUserDetailsService;


  @Operation(summary = "회원 가입 API")
  @PostMapping("sign-up")
  public SignUpResponse signUp(@Valid @RequestBody SignUpRequest request) {
    return new SignUpResponse(memberService.save(request));
  }


  @Operation(summary = "로그인 API")
  @PostMapping("sign-in")
  public LoginResponse login(@Valid @RequestBody LoginRequest request) {
    return new LoginResponse(memberService.login(request));
  }


  @Operation(summary = "내 정보 조회 API")
  @GetMapping("me")
  public MemberInfoResponse memberInfo() {
    return new MemberInfoResponse(customUserDetailsService.getCurrentMember());
  }
}
