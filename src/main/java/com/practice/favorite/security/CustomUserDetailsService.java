package com.practice.favorite.security;

import static com.practice.favorite.error.ErrorCode.UNAUTHORIZED_USER;

import com.practice.favorite.error.GeneralException;
import com.practice.favorite.member.domain.Member;
import com.practice.favorite.member.domain.MemberRepository;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

  private final MemberRepository memberRepository;

  public Member getCurrentMember() {
    UserDetails userDetails = getCurrentUserDetails();
    if (Objects.isNull(userDetails)) {
      throw new GeneralException(UNAUTHORIZED_USER);
    }
    if (!StringUtils.isNumeric(userDetails.getUsername())) {
      throw new GeneralException(UNAUTHORIZED_USER);
    }
    Long id = Long.parseLong(userDetails.getUsername());
    return memberRepository.findById(id)
        .orElseThrow(() -> new GeneralException(UNAUTHORIZED_USER));
  }

  public UserDetails getCurrentUserDetails() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null) {
      return null;
    }
    return (UserDetails) authentication.getPrincipal();
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    if (!StringUtils.isNumeric(username)) {
      throw new GeneralException(UNAUTHORIZED_USER);
    }

    Long id = Long.parseLong(username);

    Member member = memberRepository.findById(id)
        .orElseThrow(() -> new GeneralException(UNAUTHORIZED_USER));

    return CustomUserDetails.builder()
        .username(member.getId().toString())
        .authorities(null)
        .credentialsNonExpired(true)
        .accountNonExpired(true)
        .accountNonLocked(true)
        .enabled(true)
        .build();

  }
}
