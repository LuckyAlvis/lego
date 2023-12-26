package com.shenzhen.dai.lego.finance.api.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "lego-finance-server")
public interface IFinancePingRemoteService {

    /**
     * ping
     *
     * @return  String
     */
    @GetMapping("/pingRemote")
    String pingRemote();
}
