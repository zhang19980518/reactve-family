package com.joert.dbcio.config;

import com.joert.dbcio.common.CommonAuditorAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.auditing.ReactiveIsNewAwareAuditingHandler;
import org.springframework.data.domain.ReactiveAuditorAware;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.data.r2dbc.mapping.event.ReactiveAuditingEntityCallback;
import org.springframework.data.relational.core.mapping.RelationalMappingContext;
import org.springframework.data.relational.core.mapping.RelationalPersistentEntity;
import org.springframework.data.relational.core.mapping.RelationalPersistentProperty;

/**
 * @author zhang
 * @date 2023/4/12
 * @description
 */
@Configuration
@EnableR2dbcAuditing
public class R2dbcConfig {


    @Bean
    public ReactiveAuditorAware<String> auditorAware() {
        return new CommonAuditorAware();
    }

    @Bean
    public MappingContext<? extends RelationalPersistentEntity<?>, RelationalPersistentProperty> mappingContext() {
        return new RelationalMappingContext();
    }

    @Bean
    @Primary
    public ReactiveIsNewAwareAuditingHandler reactiveIsNewAwareAuditingHandler(ReactiveAuditorAware<String> auditorAware, MappingContext<? extends RelationalPersistentEntity<?>, RelationalPersistentProperty> mappingContext) {
        ReactiveIsNewAwareAuditingHandler auditingHandler = ReactiveIsNewAwareAuditingHandler.from(mappingContext);
        auditingHandler.setAuditorAware(auditorAware);
        return auditingHandler;
    }

    @Bean
    public ReactiveAuditingEntityCallback reactiveAuditingEntityCallback(ReactiveIsNewAwareAuditingHandler auditingHandler) {
        return new ReactiveAuditingEntityCallback(() -> auditingHandler);
    }
}
