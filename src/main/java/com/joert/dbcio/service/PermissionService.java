package com.joert.dbcio.service;


import com.joert.dbcio.common.BaseService;
import com.joert.dbcio.entity.Permission;
import reactor.core.publisher.Mono;

/**
 * @author zhang
 * @date 2023/4/12
 * @description
 */
public interface PermissionService extends BaseService<Permission, Long> {

    /**
     * 根据路径和方法查找权限
     * @param path
     * @param httpMethod
     * @return
     */
    Mono<Permission> findPermissionsByPathAndMethod(String path, String httpMethod);
}
