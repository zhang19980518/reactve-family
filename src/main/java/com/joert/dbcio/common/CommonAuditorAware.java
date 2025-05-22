//package com.joert.dbcio.common;
//
//import com.joert.dbcio.config.UserDetailsImpl;
//import org.springframework.data.domain.ReactiveAuditorAware;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.core.context.ReactiveSecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//import reactor.core.publisher.Mono;
//
///**
// * @author zhang
// * @date 2023/4/12
// * @description
// */
//
//@Component
//
//public class CommonAuditorAware implements ReactiveAuditorAware<String> {
//
//
//
//
//    @Override
//    public Mono<String> getCurrentAuditor() {
//        return ReactiveSecurityContextHolder.getContext().map(it->{
//            UserDetails principal =(UserDetails) it.getAuthentication().getPrincipal();
//           return principal.getUsername();
//        });
//    }
//}
