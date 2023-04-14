package com.joert.dbcio.exception.handler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author zhang
 * @date 2023/4/14
 * @description
 */
@Component
public class CustomServerAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {

    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException ex) {

        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().writeWith(
                Mono.fromSupplier(() -> {
                    DataBufferFactory bufferFactory = exchange.getResponse().bufferFactory();
                    //todo 定义统一返回格式
                    String errorMessage = "{\"error\": \"请重新登陆\"}";
                    return bufferFactory.wrap(errorMessage.getBytes(StandardCharsets.UTF_8));
                }));
    }
}
