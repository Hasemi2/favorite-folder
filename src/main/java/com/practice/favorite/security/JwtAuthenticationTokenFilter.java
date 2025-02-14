package com.practice.favorite.security;

import static com.practice.favorite.error.ErrorCode.UNAUTHORIZED_USER;

import com.practice.favorite.error.ErrorResponse;
import com.practice.favorite.error.GeneralException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;


@Slf4j
public class JwtAuthenticationTokenFilter extends GenericFilterBean {

  private final JwtProvider jwtProvider;

  public JwtAuthenticationTokenFilter(JwtProvider jwtProvider) {
    this.jwtProvider = jwtProvider;
  }


  @Override
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) req;
    HttpServletResponse response = (HttpServletResponse) res;

    if (SecurityContextHolder.getContext().getAuthentication() == null) {
      try {
        String authorizationToken = jwtProvider.resolveToken(request);
        if (authorizationToken != null && jwtProvider.validateToken(authorizationToken)) {
          Authentication auth = jwtProvider.getAuthentication(authorizationToken);
          SecurityContextHolder.getContext().setAuthentication(auth);
        } else {
          throw new GeneralException(UNAUTHORIZED_USER);
        }
      } catch (GeneralException e) {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        ObjectMapper mapper = new ObjectMapper();
        String errorJson =
            mapper.writeValueAsString(
                ErrorResponse.builder().message(UNAUTHORIZED_USER.getMessage()).build());
        response.getWriter().write(errorJson);
        return;
      }

    } else {
      log.debug("getAuthentication : '{}'",
          SecurityContextHolder.getContext().getAuthentication());
    }

    chain.doFilter(request, response);
  }
}