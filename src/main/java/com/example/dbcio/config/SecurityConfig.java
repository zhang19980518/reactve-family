package com.example.dbcio.config;

import com.example.dbcio.exception.handler.CustomAccessDeniedHandler;
import com.example.dbcio.exception.handler.CustomServerAuthenticationEntryPoint;
import com.example.dbcio.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

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


    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                .securityContextRepository(jwtAuthenticationFilter)
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/login","/refresh-token").permitAll()
                        .anyExchange().access(databaseReactiveAuthorizationManager)
                )
                .csrf().disable()
                .httpBasic().disable()
                .formLogin().disable()
                .exceptionHandling()
                //定义未登录或需要重新登陆的
                .authenticationEntryPoint(customServerAuthenticationEntryPoint)
                //定义资源服务访问拒绝的
                .accessDeniedHandler(customAccessDeniedHandler)
        ;

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
