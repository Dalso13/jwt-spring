package com.almond.jwt_spring.config;

import com.almond.jwt_spring.config.filter.MyFilter;
import com.almond.jwt_spring.config.filter.TestFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 만들어논 커스텀 필터 등록
@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<MyFilter> filter() {
        FilterRegistrationBean<MyFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new MyFilter());
        bean.addUrlPatterns("/*");  // 모든 url에 필터 적용
        bean.setOrder(0); // 낮은 번호가 가장 먼저 실행

        return bean;
    }

    // 테스트 용 필터
    @Bean
    public FilterRegistrationBean<TestFilter> filter2() {
        FilterRegistrationBean<TestFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new TestFilter());
        bean.addUrlPatterns("/*");  // 모든 url에 필터 적용
        bean.setOrder(1); // 낮은 번호가 가장 먼저 실행

        return bean;
    }
}
