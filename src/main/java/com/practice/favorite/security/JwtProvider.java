package com.practice.favorite.security;

import static com.practice.favorite.error.ErrorCode.UNAUTHORIZED_USER;

import com.practice.favorite.error.GeneralException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public final class JwtProvider {

  private static String secretKey = "favorite-folder-api";
  private static final Pattern TOKEN_TYPE = Pattern.compile("^Bearer$", Pattern.CASE_INSENSITIVE);
  private final CustomUserDetailsService customUserDetailsService;
  public static final long ACCESS_TOKEN_EXPIRATION = 1000L * 60 * 60 * 12;

  @PostConstruct
  public void init() {
    secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
  }

  public String generateToken(Long id) {
    Map<String, Object> data = new HashMap<>();
    data.put("id", id);
    Date now = new Date();
    Date accessExpiresIn = new Date(now.getTime() + ACCESS_TOKEN_EXPIRATION);
    return generateToken(data, now, accessExpiresIn);
  }


  public String generateToken(Map<String, Object> data, Date issuedAt, Date expiration) {
    Claims claims = Jwts.claims();
    claims.setId(secretKey);
    claims.putAll(data);

    Map<String, Object> headers = new HashMap<>();
    headers.put("typ", "JWT");
    headers.put("alg", "HS256");

    return Jwts.builder()
        .setHeader(headers)
        .setClaims(claims)
        .setIssuedAt(issuedAt)
        .setExpiration(expiration)
        .signWith(SignatureAlgorithm.HS256, secretKey)
        .compact();

  }

  public Long getMemberId(String accessToken) {
    return getClaims(accessToken).get("id", Long.class);
  }

  public Claims getClaims(String accessToken) {
    String[] tokenData = accessToken.split(" ");
    if (tokenData.length != 2 || !TOKEN_TYPE.matcher(tokenData[0]).matches()) {
      throw new GeneralException(UNAUTHORIZED_USER);
    }

    String token = tokenData[1];
    return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
  }


  public String resolveToken(HttpServletRequest request) {
    return request.getHeader(HttpHeaders.AUTHORIZATION);
  }


  public boolean validateToken(String accessToken) {
    try {
      Claims claims = getClaims(accessToken);
      return claims.getExpiration().after(new Date());
    } catch (Exception e) {
      log.error("Jwt validate Token Error {} ", e.getMessage());
      return false;
    }

  }

  public Authentication getAuthentication(String token) {
    Long memberId = getMemberId(token);
    UserDetails userDetails = customUserDetailsService.loadUserByUsername(memberId.toString());
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

}
