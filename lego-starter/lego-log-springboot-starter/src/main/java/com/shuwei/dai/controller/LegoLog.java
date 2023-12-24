package com.shuwei.dai.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LegoLog {
    @GetMapping("/log")
    public String log() {
        return "log";
    }
}
