package com.almond.jwt_spring.config.jwt;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


// 로그인 요청시 작동하는 필터
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    // 로그인 요청시 필터링 되며 로그인 시도를 위해 실행된다.
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        log.info("JwtAuthenticationFilter 로그인 시도");

        // username, password를 받아서 Authentication 객체를 만들어서 인증을 시도한다.
        // PrincipalDetailsService의 loadUserByUsername() 메서드가 실행된다.
        // PrincipalDetails 를 세션에 담고
        // JWT 토큰을 만들어서 응답한다.

        return super.attemptAuthentication(request, response);
    }
}
