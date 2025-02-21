package com.almond.jwt_spring.config.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

// 필터 걸기
// 필터를 통해 jwt 토큰을 받아서 내가 만든 토큰인지 검증만 하면 된다. (RSA, HS256)
@Slf4j
public class MyFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        log.info("필터 작동");
        
        // 다운 캐스팅
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        String headerAuth = req.getHeader("Authorization");
        
        // 토큰이 넘어올때 처리해줄 부분
        if (headerAuth != null) {
            log.info("Authorization: {}", headerAuth);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
