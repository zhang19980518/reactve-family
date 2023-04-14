package com.joert.dbcio.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.InsertOnlyProperty;

import java.time.LocalDateTime;

/**
 * @author zhang
 * @date 2023/4/12
 * @description
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity<T> {

    @Id
    @Column("id")
    private Long id;

    @Column("create_time")
    @InsertOnlyProperty
    @CreatedDate
    private LocalDateTime createTime;

    @Column("create_user")
    @InsertOnlyProperty
    @CreatedBy
    private String createUser;

    @Column("update_time")
    @LastModifiedDate
    private LocalDateTime updateTime;

    @Column("update_user")
    @LastModifiedBy
    private String updateUser;

    @Column("del")
    @JsonIgnore
    private Integer del;
}
