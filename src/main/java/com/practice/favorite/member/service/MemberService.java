package com.practice.favorite.member.service;

import static com.practice.favorite.error.ErrorCode.BAD_REQUEST_SIGN_IN;
import static com.practice.favorite.error.ErrorCode.CONFLICT_SIGN_UP;

import com.practice.favorite.error.GeneralException;
import com.practice.favorite.member.domain.Member;
import com.practice.favorite.member.domain.MemberRepository;
import com.practice.favorite.member.dto.LoginRequest;
import com.practice.favorite.member.dto.SignUpRequest;
import com.practice.favorite.security.JwtProvider;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberService {

  private final MemberRepository memberRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtProvider jwtProvider;

  @Transactional
  public Member save(SignUpRequest request) {
    memberRepository.findByEmail(request.getEmail())
        .ifPresent(c -> {
          throw new GeneralException(CONFLICT_SIGN_UP);
        });

    return memberRepository.save(
        Member.builder()
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .build()
    );
  }

  @Transactional
  public String login(LoginRequest request) {
    Member member = memberRepository.findByEmail(request.getEmail())
        .orElseThrow(() -> new GeneralException(BAD_REQUEST_SIGN_IN));

    if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
      throw new GeneralException(BAD_REQUEST_SIGN_IN);
    }

    if (Objects.isNull(member.getAccessToken())
        || !jwtProvider.validateToken("Bearer " + member.getAccessToken())) {

      return member
          .issueAccessToken(jwtProvider.generateToken(member.getId()))
          .getAccessToken();
    }

    return member.getAccessToken();
  }

}