package com.joert.dbcio.config;

import com.joert.dbcio.exception.handler.CustomAccessDeniedHandler;
import com.joert.dbcio.exception.handler.CustomServerAuthenticationEntryPoint;
import com.joert.dbcio.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author zhang
 * @date 2023/4/13
 * @description
 */
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsImpl userDetailsRepository;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private DatabaseReactiveAuthorizationManager databaseReactiveAuthorizationManager;

    @Autowired
    private CustomServerAuthenticationEntryPoint customServerAuthenticationEntryPoint;

    @Value("${xiaos.ignore-global}")
    private List<String> ignoreGlobal;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http

                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers(ignoreGlobal.toString()).permitAll()
                        .anyExchange().access(databaseReactiveAuthorizationManager)
                )
                .securityContextRepository(jwtAuthenticationFilter)
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .exceptionHandling(exceptionHandlingSpec -> exceptionHandlingSpec
                        .authenticationEntryPoint((exchange, ex) -> {
                            String path = exchange.getRequest().getPath().toString();
                            if (ignoreGlobal.contains(path)) {
                                return Mono.empty();
                            } else {
                                return customServerAuthenticationEntryPoint.commence(exchange, ex);
                            }
                        })
                        .accessDeniedHandler((exchange, ex) -> {
                            String path = exchange.getRequest().getPath().toString();
                            if (ignoreGlobal.contains(path)) {
                                return Mono.empty();
                            } else {
                                return customAccessDeniedHandler.handle(exchange, ex);
                            }
                        })
                );
        return http.build();
    }

    @Bean
    public ReactiveAuthenticationManager authenticationManager() {
        UserDetailsRepositoryReactiveAuthenticationManager authManager = new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsRepository);
        authManager.setPasswordEncoder(passwordEncoder());
        return authManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
