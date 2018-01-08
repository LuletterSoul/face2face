package com.luv.face2face.domain;


import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 21:45 2018/1/7.
 * @since face2face
 */

/**
 * 好友分组
 */
@Data
@Getter
@Entity
@ToString(exclude = {"members"})
@Table(name = "group_view")
public class FriendGroupView
{
    @Id
    @GenericGenerator(name = "increment", strategy = "increment")
    @GeneratedValue(generator = "increment")
    private Integer groupViewId;

    private String groupName;

    @ManyToOne
    @JoinColumn(name = "ownerId")
    private User owner;

    @ManyToMany
    @JoinTable(name = "user_group_rel", joinColumns = @JoinColumn(name = "groupViewId", referencedColumnName = "groupViewId"), inverseJoinColumns = @JoinColumn(name = "memberId", referencedColumnName = "userId"))
    private Set<User> members;



//    @OneToMany()
//    private Set<FriendView> friendViews;

}
