package com.shenzhen.dai.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

/**
 * 自定义网关局部过滤器工厂类，TimeGatewayFilterFactory，构建TimeGatewayFilter对象
 * 要求：
 * 1. 过滤器工厂类名必须按照固定格式，否则报错，是spring-cloud-gateway的约定
 * 格式：XxxGatewayFilterFactory，Xxx为过滤器类名，也就是yml文件中，filters配置的名称
 * 此时就应该配置为
 * filters:
 * - StripPrefix=1
 * - Time
 * 2. 过滤器工厂类必须继承AbstractGatewayFilterFactory类，默认指定泛型为Object，后续可以根据filters接收的参数类型进行明确指定
 */
@Component
public class TimeGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {

    // 将自定义过滤器类注册到网关中
    @Override
    public GatewayFilter apply(Object config) {
        return new TimeGatewayFilter();
    }
}
