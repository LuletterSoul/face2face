package com.luv.face2face.auth.domain;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;


@Entity
@Table(name = "user")
public class User
{

    @GeneratedValue(generator = "incrementGenerator")
    @GenericGenerator(name = "incrementGenerator", strategy = "increment")
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

    public long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public String getNickname()
    {
        return nickname;
    }

    public void setNickname(String nickname)
    {
        this.nickname = nickname;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getSex()
    {
        return sex;
    }

    public void setSex(String sex)
    {
        this.sex = sex;
    }

    public String getSignature()
    {
        return signature;
    }

    public void setSignature(String signature)
    {
        this.signature = signature;
    }
}