package com.example.dbcio.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author zhang
 * @date 2023/4/14
 * @description
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    @PostMapping("/page")
    public Mono<String> page(){
        return Mono.just("page Menu");
    }

    @PostMapping
    public Mono<String> save(){
        return Mono.just("save Menu");
    }

    @PutMapping
    public Mono<String> update(){
        return Mono.just("update Menu");
    }
}
