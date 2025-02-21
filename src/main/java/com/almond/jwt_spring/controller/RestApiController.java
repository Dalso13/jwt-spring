package com.almond.jwt_spring.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestApiController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home() {
        return "Hello World!";
    }

    @RequestMapping(value = "/token", method = RequestMethod.POST)
    public String token() {
        return "Hello World!";
    }
}
