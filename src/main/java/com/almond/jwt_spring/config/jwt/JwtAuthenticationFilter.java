package com.almond.jwt_spring.config.jwt;

import com.almond.jwt_spring.config.auth.PrincipalDetails;
import com.almond.jwt_spring.dto.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Date;


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
        try {
            // request body 꺼내는법
//            BufferedReader reader = request.getReader();
//
//            String input = null;
//            while ((input = reader.readLine()) != null) {
//                log.info(input);
//            }
            
            // 더 쉽게 꺼내는 법
            ObjectMapper om = new ObjectMapper();
            User user = om.readValue(request.getInputStream(), User.class);

            // 인증을 위한 토근 생성
            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());

            // 로그인 시도 (PrincipalDetailsService의 loadUserByUsername() 메서드가 실행된다.)
            // 로그인 성공시 정상적으로 객체가 만들어진다.
            Authentication authentication = authenticationManager.authenticate(token);

            // Authentication(Userdetails(PrincipalDetails)) 이기 때문에 형변환 가능
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();


            // JWT 토큰을 여기서 만들어 줘도 되지만 굳이 그러지 않아도 된다.


            // return을 통해 authentication 객체를 session 영역에 저장
            // 세션의 저장하는 이유는 권환 관리를 위해 사용
            return authentication;

        } catch (IOException e) {
            return null;
        }
    }

    // attemptAuthentication 실행 후 인증이 정상적으로 성공 했을때 실행되는 함수
    // JWT 토큰을 만들어서 request 요청한 사용자에게 JWT 토큰을 response 해주면 된다.
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        log.info("JWT 생성 함수 실행");

        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();

        // RSA 방식이 아닌 HMAC(Hash) 방식을 사용
        String jwtToken = JWT.create()
                .withSubject("jwtToken")
                .withExpiresAt(new Date(System.currentTimeMillis() + 60000 * 10)) // 만료시간 10분
                .withClaim("username", principalDetails.getUsername()) // 넣고싶은 키 밸류 값을 넣는다.
                .withClaim("id", principalDetails.getUser().getId())
                // 서버가 아는 고유한 값으로 지정해야됨
                .sign(Algorithm.HMAC512("COS"));

        response.addHeader("Authorization", "Bearer " + jwtToken);
    }
}
