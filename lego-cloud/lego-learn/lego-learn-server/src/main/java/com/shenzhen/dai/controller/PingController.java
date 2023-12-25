package com.shenzhen.dai.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Random;

@Slf4j
@RestController
public class PingController {

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${server.port}")
    private String port;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("/ping")
    public String ping() {
        String result = "pong, this is " + applicationName + " server! port: " + port;
        log.info(result);
        return result;
    }

    @GetMapping("/pingFinance")
    public String pingFinance() {
        return restTemplate.getForObject("http://localhost:8084/ping", String.class).toString();
    }

    /**
     * 启动多个实例，可以copy configuration 添加VM options -Dserver.port=908x
     *
     * @return
     */
    @GetMapping("/loadBalanceManually")
    public String loadBalanceManually() {
        List<ServiceInstance> instances = discoveryClient.getInstances("lego-finance-server");
        Integer index = new Random().nextInt(instances.size());
        ServiceInstance serviceInstance = instances.get(index);
        String url = "http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort() + "/ping";
        System.out.println("ping " + url);
        return restTemplate.getForObject(url, String.class).toString();
    }
}
