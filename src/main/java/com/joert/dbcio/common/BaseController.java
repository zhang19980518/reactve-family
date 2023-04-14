package com.joert.dbcio.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public abstract class BaseController<T, ID> {

    private final BaseService<T, ID> baseService;

    public BaseController(BaseService<T, ID> baseService) {
        this.baseService = baseService;
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<T>> findById(@PathVariable("id") ID id) {
        return baseService.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping
    public Flux<T> findAll() {
        return baseService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<T> create(@RequestBody T entity) {
        return baseService.save(entity);
    }

    @PutMapping
    public Mono<ResponseEntity<Boolean>> update(@RequestBody T entity) {
        return baseService.updateById(entity)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteById(@PathVariable("id") ID id) {
        return baseService.deleteById(id);
    }
}

