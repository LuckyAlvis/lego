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

    @GetMapping("/pingLearn")
    public String pingLearn() {
        return restTemplate.getForObject("http://localhost:8082/ping", String.class).toString();
    }

    /**
     * 启动多个实例，可以copy configuration 添加VM options -Dserver.port=808x
     *
     * @return String
     */
    @GetMapping("/loadBalanceManually")
    public String loadBalanceManually() {
        List<ServiceInstance> instances = discoveryClient.getInstances("lego-learn-server");
        int index = new Random().nextInt(instances.size());
        ServiceInstance serviceInstance = instances.get(index);
        String url = "http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort() + "/ping";
        System.out.println("ping " + url);
        return restTemplate.getForObject(url, String.class).toString();
    }

    /**
     * RestTemplate增加@LoadBalanced注解
     * 自动负载均衡，RestTemplate 会自动选择一个合适的服务实例
     * 当前方法未成功，可能跟版本有关
     *
     * @return String
     */
    @Deprecated
    @GetMapping("/loadBalanceAuto")
    public String loadBalanceAuto() {
        String url = "http://lego-learn-server/ping";
        System.out.println("ping " + url);
        return restTemplate.getForObject(url, String.class).toString();
    }
}
