package com.joert.dbcio.service.impl;


import com.joert.dbcio.common.BaseServiceImpl;
import com.joert.dbcio.entity.User;
import com.joert.dbcio.repository.UserRepository;
import com.joert.dbcio.service.UserService;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * @author zhang
 * @date 2023/4/12
 * @description
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User,Long, UserRepository> implements UserService {
    public UserServiceImpl(R2dbcEntityTemplate r2dbcEntityTemplate, UserRepository userRepository) {
        super(userRepository, r2dbcEntityTemplate, User.class);
    }


    @Override
    public Mono<User> findByUsername(String username) {
        return getRepository().findByUsername(username);
    }
}
