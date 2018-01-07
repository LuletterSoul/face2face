package com.luv.face2face.domain;


import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 21:26 2018/1/7.
 * @since face2face
 */

@Data
@Getter
@Entity
@ToString
@Table(name = "friend_view")
public class FriendView
{
    @Id
    @GenericGenerator(name = "increment", strategy = "increment")
    @GeneratedValue(generator = "increment")
    private Long userId;

    private String nickname;

    /** 备注 */
    private String remark;

    /** 个性签名 */
    private String signature;

    private String sex;

    /** 所属好友分组 */
    private int groupId;

    /** 分组备注 */
    private String groupName;


    @ManyToOne
    @JoinColumn(name = "friendId")
    private User friend;

}
