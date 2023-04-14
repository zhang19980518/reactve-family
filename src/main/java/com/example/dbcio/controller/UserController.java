package com.example.dbcio.controller;

import com.example.dbcio.common.BaseController;
import com.example.dbcio.entity.User;
import com.example.dbcio.service.UserService;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

/**
 * @author zhang
 * @date 2023/4/12
 * @description
 */
@RestController
@RequestMapping("user")
public class UserController extends BaseController<User, Long> {

    private final UserService userService;

    public UserController(UserService userService) {
        super(userService);
        this.userService = userService;
    }

    @GetMapping("/info")
    public Mono<User> info() {
        return ReactiveSecurityContextHolder.getContext().map(it -> {
            UserDetails details = (UserDetails) it.getAuthentication().getPrincipal();
            String username = details.getUsername();
            return userService.findByUsername(username);
        }).flatMap(it -> it);
    }


}
