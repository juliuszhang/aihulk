package com.aihulk.tech.common.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

/**
 * @author zhangyibo
 * @title: User
 * @projectName aihulk
 * @description: 用户
 * @date 2019-06-2811:18
 */
@Data
@TableName(value = "user")
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {

    public static final Integer EMAIL_CHECKED = 1;
    public static final Integer EMAIL_UNCHECKED = 0;

    @TableField(value = "username")
    private String username;

    @TableField(value = "password")
    private String password;

    @TableField(value = "email")
    private String email;

    @TableField(value = "mobile")
    private String mobile;

    //邮箱是否已经认证
    @TableField(value = "email_checked")
    private Integer emailChecked;

    @TableField(value = "token")
    private String token;

    public String initToken() {
        //暂时使用uuid作为token
        this.token = UUID.randomUUID().toString();
        return this.token;
    }

}
