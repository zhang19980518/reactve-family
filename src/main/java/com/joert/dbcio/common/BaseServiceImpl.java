package com.joert.dbcio.common;


import com.joert.dbcio.utils.EntityUtils;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.data.relational.core.query.Update;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author zhang
 * @date 2023/4/12
 * @description
 */
public class BaseServiceImpl<T extends BaseEntity<T>, ID, R extends R2dbcRepository<T, ID>> implements BaseService<T, ID> {

    private final R repository;


    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final Class<T> entityClass;

    public BaseServiceImpl(R repository, R2dbcEntityTemplate r2dbcEntityTemplate, Class<T> entityClass) {
        this.repository = repository;
        this.r2dbcEntityTemplate = r2dbcEntityTemplate;
        this.entityClass = entityClass;
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public Mono<T> save(T entity) {
        return repository.save(entity);
    }

    @Override
    public Mono<T> findById(ID id) {
        return repository.findById(id);
    }

    @Override
    public Flux<T> findAll() {
        return repository.findAll();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Mono<Void> deleteById(ID id) {
        return repository.deleteById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Mono<Boolean> updateById(T entity) {
        String currentUser = "admin"; // Replace this with your custom AuditorAware implementation
        // Set updateUser and updateTime
        entity.setUpdateUser(currentUser);
        entity.setUpdateTime(LocalDateTime.now());
        Map<String, Object> nonNullProperties = EntityUtils.getNonNullProperties(entity);
        Update update = EntityUtils.createUpdateFromNonNullProperties(nonNullProperties);
        update=null;
        if (update != null) {
            Query query = Query.query(Criteria.where("id").is(entity.getId()));
            return r2dbcEntityTemplate.update(query, update, entityClass).map(
                   it -> it > 0
            );
        } else {
            return Mono.error(new IllegalArgumentException("There are no non-null properties to update."));
        }
    }

    @Override
    public Mono<Boolean> existsById(ID id) {
        return repository.existsById(id);
    }





    protected R getRepository() {
        return repository;
    }

    protected R2dbcEntityTemplate getR2dbcEntityTemplate() {
        return r2dbcEntityTemplate;
    }
}






