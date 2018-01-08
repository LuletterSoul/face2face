package com.luv.face2face.repository;

import com.luv.face2face.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 * created in  11:44 2018/1/8.
 * @since face2face
 */

public interface FriendJpaDao extends JpaRepository<User, Long> {

    @Query(value = "select f from User u left join u.friends f where u =?1")
    List<User> findByUser(User user);
}
