package com.joert.dbcio.common;

/**
 * @author zhang
 * @date 2023/4/12
 * @description
 */
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BaseService<T, ID> {

    /**
     * 保存
     * @param entity
     * @return
     */
    Mono<T> save(T entity);

    /**
     * 根据id查找
     * @param id
     * @return
     */
    Mono<T> findById(ID id);

    /**
     * 查找所有
     * @return
     */
    Flux<T> findAll();

    /**
     * 根据Id删除
     * @param id
     * @return
     */
    Mono<Void> deleteById(ID id);


    /**
     * 根据id修改
     *
     * @param entuty
     * @return
     */
    Mono<Boolean> updateById(T entuty);

    /**
     * 是否存在
     * @param id
     * @return
     */
    Mono<Boolean> existsById(ID id);

}

