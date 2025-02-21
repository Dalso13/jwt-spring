package com.almond.jwt_spring.controller;

import com.almond.jwt_spring.dto.User;
import com.almond.jwt_spring.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class RestApiController {

    @Autowired
    private UserMapper mapper;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home() {
        return "Hello World!";
    }

    @RequestMapping(value = "/token", method = RequestMethod.POST)
    public String token() {
        return "Hello World!";
    }

    @RequestMapping(value = "/join", method = RequestMethod.PUT)
    public String user(@RequestBody User user) {
        log.info("회원가입 시도");
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");

        mapper.join(user);
        return "가입 완료";
    }
}
