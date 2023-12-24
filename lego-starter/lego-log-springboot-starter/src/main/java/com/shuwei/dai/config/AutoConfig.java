package com.shuwei.dai.config;

import com.shuwei.dai.controller.LegoLog;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.shuwei.dai")
@ConditionalOnProperty
public class AutoConfig {
    public LegoLog log() {
        return new LegoLog();
    }
}
