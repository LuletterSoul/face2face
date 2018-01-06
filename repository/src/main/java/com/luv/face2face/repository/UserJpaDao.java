package com.luv.face2face.repository;


import com.luv.face2face.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 19:22 2018/1/5.
 * @since luv-face2face
 */

public interface UserJpaDao extends JpaRepository<User, Long>
{}
