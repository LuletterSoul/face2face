package com.luv.face2face.repository;


import com.luv.face2face.domain.Remark;
import com.luv.face2face.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 10:51 2018/1/8.
 * @since face2face
 */

public interface RemarkJpaDao extends JpaRepository<Remark, Long>
{
    public Remark findByMarkerAndAndBioMarker(User marker, User bioMarker);

}
