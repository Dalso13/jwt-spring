package com.almond.jwt_spring.config.filter;

import jakarta.servlet.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

// 필터 걸기
@Slf4j
public class TestFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("테스트 필터 작동");
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
