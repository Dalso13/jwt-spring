package com.almond.jwt_spring.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SequrityConfig {

    private final UrlBasedCorsConfigurationSource corsConfigurationSource;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(cors -> cors.configurationSource(corsConfigurationSource)); // cors 설정
        http.sessionManagement(h -> h.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // 세션인증 비활성화
        http.formLogin(AbstractHttpConfigurer::disable); // form 로그인 비활성화
        http.httpBasic(AbstractHttpConfigurer::disable); // basic 로그인 비활성화 (header에 username, password를 넣어서 요청하는 방식)

        http.authorizeHttpRequests(request -> request
                .requestMatchers("/api/v1/user/**").hasAnyRole("MANAGER", "ADMIN","USER")
                .requestMatchers("/api/v1/manager/**").hasAnyRole("MANAGER", "ADMIN")
                .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
                .anyRequest().permitAll()
        );
        return http.build();
    }

}
