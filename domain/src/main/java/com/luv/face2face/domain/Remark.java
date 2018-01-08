package com.luv.face2face.domain;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 * created in  10:46 2018/1/8.
 * @since face2face
 */
@Data
@Getter
@Entity
@ToString
@Table(name = "remark_rel")
public class Remark {


    @Id
    @GenericGenerator(name = "increment", strategy = "increment")
    @GeneratedValue(generator = "increment")
    private Long remarkId;

    /**
     * 备注者
     */
    @ManyToOne
    @JoinColumn(name = "markerId")
    private User marker;

    /**
     * 被备注者
     */
    @ManyToOne
    @JoinColumn(name = "bioMarkerId")
    private User bioMarker;

    private String markContent;

}
