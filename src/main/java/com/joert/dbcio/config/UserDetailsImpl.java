package com.joert.dbcio.config;

import com.joert.dbcio.repository.PermissionRepository;
import com.joert.dbcio.repository.RolePermissionRepository;
import com.joert.dbcio.repository.UserRepository;
import com.joert.dbcio.repository.UserRoleRepository;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
/**
 * @author zhang
 * @date 2023/4/13
 * @description
 */
@Service
public class UserDetailsImpl implements ReactiveUserDetailsService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final RolePermissionRepository rolePermissionRepository;
    private final PermissionRepository permissionRepository;

    public UserDetailsImpl(UserRepository userRepository,
                           UserRoleRepository userRoleRepository,
                           RolePermissionRepository rolePermissionRepository,
                           PermissionRepository permissionRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.rolePermissionRepository = rolePermissionRepository;
        this.permissionRepository = permissionRepository;
    }


    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return userRepository.findByUsername(username)
                .flatMap(user -> userRoleRepository.findRolesByUserId(user.getId())
                        .flatMap(role -> {
                            System.out.println(role.toString());
                            return rolePermissionRepository.findPermissionByRoleId(role.getRoleId());
                        })
                        .flatMap(permission -> {
                            System.out.println(permission.toString());
                            return permissionRepository.findById(permission.getPermissionId());
                        })
                        .map(p -> new SimpleGrantedAuthority(p.getAuthority()))
                        .collectList()
                        .map(authorities -> User.withUsername(user.getUsername())
                                .password(user.getPassword())
                                .authorities(authorities)
                                .build()));
    }

}

