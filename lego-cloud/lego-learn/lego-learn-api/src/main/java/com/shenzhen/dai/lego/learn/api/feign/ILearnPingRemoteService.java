package com.shenzhen.dai.lego.learn.api.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "lego-learn-server")
public interface ILearnPingRemoteService {

    /**
     * ping
     *
     * @return  String
     */
    @GetMapping("/pingRemote")
    String pingRemote();
}
