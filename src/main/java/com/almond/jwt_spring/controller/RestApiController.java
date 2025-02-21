package com.almond.jwt_spring.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestApiController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public  String index() {
        return "Hello World!";
    }
}
