package com.luv.face2face.repository;


import com.luv.face2face.domain.FriendView;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 21:25 2018/1/7.
 * @since face2face
 */

public interface FriendJpaDao extends JpaRepository<FriendView, Long>
{}
