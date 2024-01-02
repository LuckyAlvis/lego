package com.shenzhen.dai.filter;

import com.alibaba.nacos.common.utils.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Component
public class AuthGlobalFilter implements GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        // 请求http://localhost:9999/finance/ping?token=ss
        String token = request.getQueryParams().getFirst("token");
        if (StringUtils.isBlank(token)) {
            // 没有token直接返回401
            // response.setStatusCode(HttpStatus.UNAUTHORIZED);
            // return response.setComplete();
            HttpHeaders httpHeaders = response.getHeaders();
            // 设置返回数据格式json，否则可能会乱码
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            // 设置返回内容，注意是String类型，否则会报错
            String body = "{\"code\":401,\"msg\":\"未登录\", data:\"\"}";
            // 设置编码，否则中文乱码
            byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
            // 设置返回数据长度，否则返回数据可能会不全，因为response是基于流的，所以需要设置数据长度，否则可能会导致浏览器一直在等待数据
            DataBuffer dataBuffer = response.bufferFactory().wrap(bytes);
            // 返回数据，注意这里是flux的写法，所以需要使用response.writeWith，如果是mvc的话，直接response.getWriter.write就可以了
            return response.writeWith(Mono.just(dataBuffer));
        }
        return chain.filter(exchange);
    }
}
