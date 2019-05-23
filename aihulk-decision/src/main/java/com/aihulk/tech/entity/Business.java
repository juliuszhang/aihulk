package com.aihulk.tech.entity;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Table(name = "business")
public class Business implements Serializable {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;

    private String name;

    @Column(name = "en_name")
    private String enName;

}
