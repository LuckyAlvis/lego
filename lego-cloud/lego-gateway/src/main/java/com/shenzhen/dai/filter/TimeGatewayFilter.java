package com.shenzhen.dai.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @description: 计算响应时间过滤器
 * @createDate: 2021/7/26 16:47
 * @author: ivan
 */
public class TimeGatewayFilter implements GatewayFilter, Ordered {

    private TimeGatewayFilterParam config;

    public TimeGatewayFilter() {
    }

    public TimeGatewayFilter(TimeGatewayFilterParam config) {
        this.config = config;
    }

    /**
     * 编写过滤器的业务逻辑
     *
     * @param exchange 转换器，封装了来自请求的全部信息，比如：请求方法，请求参数，请求路径，请求头，cookie等
     * @param chain    过滤器链，使用责任链模式，决定当前过滤器是放行还是拒绝
     * @return Mono    响应式编程的返回值规范，一般后置拦截才会用到
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) { //这个括号到return之间的代码前置过滤器的核心逻辑
        System.out.println("自定义参数：config = " + config.toString());
        // 记录请求进入时间
        Long begin = System.currentTimeMillis();
        System.out.println("请求进入时间：" + begin);
        // 后置拦截
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {// 这个括号到最后的代码后置过滤器的核心逻辑
            // 记录请求结束时间
            Long end = System.currentTimeMillis();
            System.out.println("请求结束时间：" + end);
            // 计算请求耗时
            System.out.println("请求耗时：" + (end - begin));
        }));
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
