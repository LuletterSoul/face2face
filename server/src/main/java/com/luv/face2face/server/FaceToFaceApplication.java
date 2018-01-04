package com.luv.face2face.server;

import com.luv.face2face.logic.LogicServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 * created in  22:17 2018/1/4.
 * @since face2face
 */

@ComponentScan(basePackages = {"com.luv.face2face"}) // 扫描该包路径下的所有spring组件
@EntityScan({"com.luv.face2face.domain"})
@SpringBootApplication
public class FaceToFaceApplication {
    private LogicServer server;

    @Autowired
    public void setServer(LogicServer server) {
        this.server = server;
    }

    public static void main(String[] args)
    {
        SpringApplication.run(FaceToFaceApplication.class, args);
    }
}
