package com.example.dbcio.entity;

/**
 * @author zhang
 * @date 2023/4/12
 * @description
 */
import com.example.dbcio.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDateTime;

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

