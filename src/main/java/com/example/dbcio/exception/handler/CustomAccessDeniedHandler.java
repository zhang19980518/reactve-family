package com.example.dbcio.exception.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * @author zhang
 * @date 2023/4/14
 * @description
 */
@Component
public class CustomAccessDeniedHandler implements ServerAccessDeniedHandler{
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException denied) {
        String errorMessage= denied.getMessage();
        exchange.getResponse().setStatusCode(HttpStatusCode.valueOf(403));
        String finalErrorMessage = errorMessage;
        return exchange.getResponse().writeWith(Mono.fromSupplier(() -> {
            DataBufferFactory bufferFactory = exchange.getResponse().bufferFactory();
            // Customize your error message here
            ObjectMapper objectMapper=new ObjectMapper();
            String s = null;
            try {
                s = objectMapper.writeValueAsString(finalErrorMessage);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            //todo 定义统一返回格式
            return bufferFactory.wrap("暂无权限".getBytes(StandardCharsets.UTF_8));
        }));
    }
}
