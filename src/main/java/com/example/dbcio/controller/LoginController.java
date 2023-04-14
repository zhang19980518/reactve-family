package com.example.dbcio.controller;

import com.example.dbcio.utils.JwtUtils;
import com.example.dbcio.dto.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhang
 * @date 2023/4/13
 * @description
 */
@RestController
public class LoginController {

    @Autowired
    private ReactiveAuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;


    @PostMapping("/login")
    public Mono<ResponseEntity<Map<String, String>>> login(@RequestBody LoginRequest loginRequest) {
        //String
        return Mono.just(loginRequest)
                .map(lr -> new UsernamePasswordAuthenticationToken(lr.getUsername(),lr.getPassword()))
                .flatMap(authenticationManager::authenticate)
                .map(auth -> {
                    String token = jwtUtils.generateAccessToken(auth);
                    Map<String, String> response = new HashMap<>();
                    response.put("token", "Bearer "+token);
                    return ResponseEntity.ok(response);
                })
                .onErrorResume(AuthenticationException.class, e -> Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));
    }
}
