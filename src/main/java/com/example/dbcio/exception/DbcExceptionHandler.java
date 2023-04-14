package com.example.dbcio.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

import java.nio.ByteBuffer;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author zhang
 * @date 2023/4/13
 * @description
 */
@Component
@Order(-2)
public class DbcExceptionHandler implements WebExceptionHandler {
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        String message = "";
        if (ex instanceof IllegalArgumentException exception) {
            message = exception.getMessage();
        } else {
            message = "请求错误";
        }
        ex.printStackTrace();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String s = objectMapper.writeValueAsString(message);
            ByteBuffer byteBuffer = ByteBuffer.wrap(s.getBytes(UTF_8));
            DataBuffer dataBuffer = new DefaultDataBufferFactory().wrap(byteBuffer);
            //todo 定义统一返回
            exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
            return exchange.getResponse().writeWith(Mono.just(dataBuffer));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    }

