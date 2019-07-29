package com.aihulk.tech.entity.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@TableName(value = "user_info")
@EqualsAndHashCode(callSuper = true)
public class UserInfo extends BaseEntity{

    @TableField(value = "user_id")
    private int userId;

    @TableField(value = "name")
    private String name;

    @TableField(value = "nickname")
    private String nickname;

    @TableField(value = "avatar")
    private String avatar;

    @TableField(value = "signature")
    private String signature;

    @TableField(value = "title")
    private String title;

    @TableField(value = "team")
    private String team;

    @TableField(value = "country")
    private String country;

    @TableField(value = "province")
    private String province;

    @TableField(value = "city")
    private String city;

    //详细地址
    @TableField(value = "address")
    private String address;
}
