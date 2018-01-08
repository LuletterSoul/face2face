package com.luv.face2face.repository;


import com.luv.face2face.domain.FriendGroupView;
import com.luv.face2face.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 10:36 2018/1/8.
 * @since face2face
 */

public interface GroupViewJpaDao extends JpaRepository<FriendGroupView, Long>
{
    List<FriendGroupView> findByOwner(User user);

    @Query(value = "select f from FriendGroupView f left join f.members m where f.owner.userId =?1 and m.userId = ?2")
    FriendGroupView findByOwnerAndFAndMembers(Long userId,Long memberId);
}
