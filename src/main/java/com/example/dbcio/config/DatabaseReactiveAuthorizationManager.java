package com.example.dbcio.config;

import com.example.dbcio.entity.Permission;
import com.example.dbcio.service.PermissionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import java.net.URI;
import java.util.List;


/**
 * @author zhang
 * @date 2023/4/13
 * @description
 */
@Component
public class DatabaseReactiveAuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    @Value("${xiaos.ignore}")
    private List<String> ignore;
    private final PermissionService permissionService;

    public DatabaseReactiveAuthorizationManager(PermissionService permissionService) {
        this.permissionService = permissionService;
    }



    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, AuthorizationContext context) {
        URI uri = context.getExchange().getRequest().getURI();
        String httpMethod = context.getExchange().getRequest().getMethod().name();
        if (!isAccessCheckRequired(uri.getPath())) {
            return Mono.just(new AuthorizationDecision(true));
        }
        Mono<Permission> requiredPermissions = permissionService.findPermissionsByPathAndMethod(uri.getPath(), httpMethod);
        requiredPermissions.subscribe(System.out::println);
        return authentication.flatMap(auth -> {
            List<String> userAuthorities = auth.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .toList();

            return requiredPermissions
                    .map(Permission::getAuthority)
                    .map(userAuthorities::contains)
                    .map(AuthorizationDecision::new);
        });
    }

    private boolean isAccessCheckRequired(String path) {
        boolean contains = ignore.contains(path);
        return !contains;
    }
}
