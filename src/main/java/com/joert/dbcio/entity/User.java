package com.joert.dbcio.entity;

/**
 * @author zhang
 * @date 2023/4/12
 * @description
 */
import com.joert.dbcio.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User extends BaseEntity<User> {

    @Column("nick_name")
    private String nickName;

    @Column("password")
    private String password;

    @Column("username")
    private String username;

    @Column("phone")
    private String phone;

}

