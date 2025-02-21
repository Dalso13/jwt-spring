package com.almond.jwt_spring.config.jwt;

import com.almond.jwt_spring.config.auth.PrincipalDetails;
import com.almond.jwt_spring.dto.User;
import com.almond.jwt_spring.mapper.UserMapper;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

// 시큐리티가 filter를 가지고 있는데 그 필터중에 BasicAuthenticationFilter라는 것이 있다.
// 권환이나 인증이 필요한 특정 주소를 요청했을 때 위 필터를 무조건 타게 되어있다.
// 만약 권한이나 인증이 필요한 주소가 아니라면 위 필터를 안탄다.
@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final UserMapper mapper;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserMapper mapper) {
        super(authenticationManager);
        this.mapper = mapper;
    }

    // 기본적으로 모든 요청에 대해 필터링을 하게 된다.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        log.info("인증 시도");

        String jwtHeader = request.getHeader("Authorization");

        // header가 있는지, Bearer로 시작하는지 확인
        // 검증을 통해 header에 인증토큰이 있는 경우에만 이 필터를 진행하게 된다.
        if (jwtHeader == null || !jwtHeader.startsWith("Bearer")) {
            chain.doFilter(request, response);
            return;
        }

        // JWT 토큰 검증
        String jwtToken = request.getHeader("Authorization").replace("Bearer ", "");
        // 복호화
        String username;
        try {
            username = JWT.require(Algorithm.HMAC512("COS"))
                    .build().verify(jwtToken).getClaim("username").asString();
        } catch (Exception e) {
            chain.doFilter(request, response);
            return;
        }

        // 서명이 정상적으로 됐다면
        if (username != null) {
            User user = mapper.findByUsername(username);

            PrincipalDetails principalDetails = new PrincipalDetails(user);

            // JWT 토큰 서명을 통해 서명이 정상이면 Authentication 객체를 만들어준다.
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    principalDetails,null, principalDetails.getAuthorities());

            // 강제로 시큐리티 세션에 접근하여 Authentication 객체를 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);

            log.info("인증 완료");

            chain.doFilter(request, response);
        }
    }
}
