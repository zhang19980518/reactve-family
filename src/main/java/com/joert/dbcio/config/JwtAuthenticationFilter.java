package com.joert.dbcio.config;


import com.joert.dbcio.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

/**
 * @author zhang
 * @date 2023/4/14
 * @description
 */
@Component
public class JwtAuthenticationFilter implements ServerSecurityContextRepository{

    @Value("${xiaos.ignore-global}")
    private List<String> ignoreGlobal;

    private final UserDetailsImpl userDetailsImpl;

    private final JwtUtils jwtUtils;

    public JwtAuthenticationFilter(UserDetailsImpl userDetailsImpl, JwtUtils jwtUtils) {
        this.userDetailsImpl = userDetailsImpl;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        throw new UnsupportedOperationException("Save method not supported");
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        String path = exchange.getRequest().getPath().toString();
        if(ignoreGlobal.contains(path)){
            return Mono.empty();
        }
        String token = getTokenFromRequest(exchange.getRequest());
        Optional<String> optionalUsername = jwtUtils.validateAndParseToken(token);
        if (optionalUsername.isPresent()) {
            String username = optionalUsername.get();
            return userDetailsImpl.findByUsername(username)
                    .map(userDetails -> {
                        UsernamePasswordAuthenticationToken authenticationToken =
                                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        return new SecurityContextImpl(authenticationToken);
                    });
        }
        return Mono.empty();
    }
    private String getTokenFromRequest(ServerHttpRequest request) {
        String authorizationHeader = request.getHeaders().getFirst("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }else {
            throw new IllegalArgumentException("请先登录!");
        }
    }
}
