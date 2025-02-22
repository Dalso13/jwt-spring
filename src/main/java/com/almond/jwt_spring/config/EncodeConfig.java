package com.almond.jwt_spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class EncodeConfig {
    // 비밀번호 암호화
     @Bean
     public BCryptPasswordEncoder encode() {
         return new BCryptPasswordEncoder();
     }
}
