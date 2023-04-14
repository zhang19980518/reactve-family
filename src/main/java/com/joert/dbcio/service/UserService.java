package com.joert.dbcio.service;


import com.joert.dbcio.common.BaseService;
import com.joert.dbcio.entity.User;
import reactor.core.publisher.Mono;

/**
 * @author zhang
 * @date 2023/4/12
 * @description
 */
public interface UserService extends BaseService<User, Long> {

    /**
     * 根据用户名查找
     * @param username
     * @return
     */
    Mono<User> findByUsername(String username);
}
