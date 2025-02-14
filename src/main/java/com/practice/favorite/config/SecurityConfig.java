package com.practice.favorite.config;

import com.practice.favorite.security.EntryPointUnauthorizedHandler;
import com.practice.favorite.security.JwtAuthenticationTokenFilter;
import com.practice.favorite.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final EntryPointUnauthorizedHandler entryPointUnauthorizedHandler;
    private final JwtProvider jwtProvider;

    private static final String[] ANT_PATTERNS = {
            "/swagger-ui.html",
            "/swagger-ui.html/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/webjars/**",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/api-docs",
            "/api-docs/**",
            "/error",
            "/sign-up",
            "/sign-in"
    };

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .headers()
                .disable()
                .exceptionHandling()
                .authenticationEntryPoint(entryPointUnauthorizedHandler)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(ANT_PATTERNS).permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .disable();

        http
                .addFilterBefore(new JwtAuthenticationTokenFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class);
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(ANT_PATTERNS);
    }

}