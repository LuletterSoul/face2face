package com.luv.face2face.domain;


import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;


@Data
@Getter
@Entity
@ToString(exclude = {"friends", "toFriends", "groupViews", "friendGroupViews", "remarks",
    "bioMarkers",})
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

    // @OneToMany(mappedBy = "friend")
    // private Set<FriendView> friendViews;

    /**
     * 一个用户有多个好友
     */
    @ManyToMany
    @JoinTable(name = "user_friend_rel", joinColumns = @JoinColumn(name = "userId", referencedColumnName = "userId"), inverseJoinColumns = @JoinColumn(name = "friendId", referencedColumnName = "userId"))
    private Set<User> friends;

    /**
     * 一个用户也可以作为多名好友的好友
     */
    @ManyToMany
    @JoinTable(name = "user_friend_rel", joinColumns = @JoinColumn(name = "friendId", referencedColumnName = "userId"), inverseJoinColumns = @JoinColumn(name = "userId", referencedColumnName = "userId"))
    private Set<User> toFriends;

    /**
     * 一个用户可以位于多个好友分组 组信息
     */
    @ManyToMany
    @JoinTable(name = "user_group_rel", joinColumns = @JoinColumn(name = "memberId", referencedColumnName = "userId"), inverseJoinColumns = @JoinColumn(name = "groupViewId", referencedColumnName = "groupViewId"))
    private Set<FriendGroupView> friendGroupViews;

    /**
     * 一个用户拥有多个好友分组
     */
    @OneToMany(mappedBy = "owner")
    private Set<FriendGroupView> groupViews;

    /**
     * 一个用户可以备注多名好友
     */
    @OneToMany(mappedBy = "marker")
    private Set<Remark> remarks;

    /**
     * 一个用户也可以被多名好友备注
     */
    @OneToMany(mappedBy = "bioMarker")
    private Set<Remark> bioMarkers;

    public User()
    {}

    protected boolean canEqual(Object other)
    {
        return other instanceof User;
    }
}
