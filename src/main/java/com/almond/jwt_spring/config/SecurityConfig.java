package com.almond.jwt_spring.config;

import com.almond.jwt_spring.config.jwt.JwtAuthenticationFilter;
import com.almond.jwt_spring.config.jwt.JwtAuthorizationFilter;
import com.almond.jwt_spring.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UrlBasedCorsConfigurationSource corsConfigurationSource;
    private final UserMapper mapper;
    
    // AuthenticationManager 가 필요해서 Bean으로 등록 후 FilterChain 에서 매개변수로 주입
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,  AuthenticationManager authenticationManager) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable);

        // 이렇게 등록해도 되지만 필터 config에서 등록하는 것이 더 좋다.
//        http.addFilterBefore(new MyFilter(), BasicAuthenticationFilter.class); // 커스텀 필터 추가
        http.cors(cors -> cors.configurationSource(corsConfigurationSource)); // cors 설정
        http.sessionManagement(h -> h.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // 세션인증 비활성화
        http.formLogin(AbstractHttpConfigurer::disable); // form 로그인 비활성화
        http.httpBasic(AbstractHttpConfigurer::disable); // basic 로그인 비활성화 (header에 username, password를 넣어서 요청하는 방식)
        // 로그인 시도시 작동하는 필터
        // AuthenticationManager 매개변수를 던져줘야 함
        http.addFilter(new JwtAuthenticationFilter(authenticationManager));
        // 권환이 필요한 url 접근시 작동하는 필터
        http.addFilter(new JwtAuthorizationFilter(authenticationManager, mapper));

        http.authorizeHttpRequests(request -> request
                .requestMatchers("/api/v1/user/**").hasAnyRole("MANAGER", "ADMIN","USER")
                .requestMatchers("/api/v1/manager/**").hasAnyRole("MANAGER", "ADMIN")
                .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
                .anyRequest().permitAll()
        );
        return http.build();
    }

}
