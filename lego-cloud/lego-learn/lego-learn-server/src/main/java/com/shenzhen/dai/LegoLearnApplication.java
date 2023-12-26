package com.shenzhen.dai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * Feign是spring-cloud组件中的一个声明式伪Http客户端，它使得调用远程服务就像调用本地服务一样简单，只需要创建一个接口并在接口上添加注解即可。
 * Nacos很好地兼容了Feign，Feign默认集成了Ribbon，所以使用Feign时也具备了客户端负载均衡的能力。
 * 它的目标是为了替代Ribbon+RestTemplate的方式。
 * 添加pom
 * <dependency>
 *   <groupId>org.springframework.cloud</groupId>
 *   <artifactId>spring-cloud-starter-openfeign</artifactId>
 * </dependency>
 */
@EnableFeignClients // 开启Feign
@EnableDiscoveryClient // 开启服务发现
@SpringBootApplication(scanBasePackages = {"com.shenzhen.dai"})
public class LegoLearnApplication {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(LegoLearnApplication.class, args);
    }
}
