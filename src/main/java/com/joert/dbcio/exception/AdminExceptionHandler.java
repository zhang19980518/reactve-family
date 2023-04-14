package com.joert.dbcio.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

/**
 * @author zhang
 * @date 2023/4/13
 * @description
 */
@RestControllerAdvice
public class AdminExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Mono<ResponseEntity> saTokenExceptionLogin(IllegalArgumentException illegalArgumentException) {
        illegalArgumentException.printStackTrace();
        //todo 定义统一返回
        return Mono.just(ResponseEntity.internalServerError().body(illegalArgumentException.getMessage()));
    }
}
