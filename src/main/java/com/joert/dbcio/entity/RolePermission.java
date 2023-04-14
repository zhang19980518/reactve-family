package com.joert.dbcio.entity;

import com.joert.dbcio.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;

/**
 * @author zhang
 * @date 2023/4/13
 * @description
 */
@Table("role_permission")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RolePermission extends BaseEntity<RolePermission> implements Serializable {

    @Column("role_id")
    private Long roleId;

    @Column("permission_id")
    private Long permissionId;
}
