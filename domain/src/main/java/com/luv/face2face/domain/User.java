package com.luv.face2face.domain;


import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;


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

    @OneToMany(mappedBy = "friend")
    private Set<FriendView> friendViews;



//    /**
//     * 一个用户有多个好友
//     */
//    private Set<User> friends;

    /**
     * 一个用户有多个组
     * 组信息
     */
    @ManyToMany
    @JoinTable(name = "user_group_rel", joinColumns = @JoinColumn(name = "memberId"
            , referencedColumnName = "userId"), inverseJoinColumns = @JoinColumn(name = "groupViewId", referencedColumnName = "groupViewId"))
    private Set<GroupView> groupViews;


    public User()
    {}

    protected boolean canEqual(Object other)
    {
        return other instanceof User;
    }

}
