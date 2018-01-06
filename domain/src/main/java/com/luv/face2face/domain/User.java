package com.luv.face2face.domain;


import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Data
@Getter
@Entity
@ToString
@Table(name = "user")
public class User
{

    @Id
    @GenericGenerator(name = "increment", strategy = "increment")
    @GeneratedValue(generator = "increment")
    private Long userId;

    private String sex;

    /**
     * 性别 private byte sex; /** 用户名字
     */
    private String nickname;

    /**
     * 密码
     */
    private String password;

    /**
     * 个性签名
     */
    private String signature;

    public User()
    {}

    protected boolean canEqual(Object other)
    {
        return other instanceof User;
    }

}
