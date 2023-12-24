package com.shenzhen.dai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class PingController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/ping")
    public String ping() {
        return "pong, this is finance server!";
    }

    @GetMapping("/pingLearn")
    public String b() {
        return restTemplate.getForObject("http://localhost:8082/ping", String.class).toString();
    }
}
