package com.joert.dbcio.entity;


import java.io.Serializable;

import com.joert.dbcio.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * @author zhang
 * @date 2023/4/13
 * @description
 */
@Table("permission")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Permission extends BaseEntity<Permission> implements Serializable {


    @Column("name")
    private String name;

    @Column("description")
    private String description;

    @Column("authority")
    private String authority;

    @Column("path")
    private String path;

    @Column("method")
    private String method;

    @Column("type")
    private Integer type;

}
