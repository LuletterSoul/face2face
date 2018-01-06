package com.luv.face2face.server;


import com.luv.face2face.logic.LogicServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 22:17 2018/1/4.
 * @since face2face
 */

@ComponentScan(basePackages = {"com.luv.face2face"}) // 扫描该包路径下的所有spring组件
@EntityScan({"com.luv.face2face.domain"})
@SpringBootApplication
public class FaceToFaceApplication
{
    private IMServer server;

    @Qualifier("logicNettyServer")
    public void setServer(IMServer server)
    {
        this.server = server;
    }

    public static void main(String[] args)
    {

        SpringApplication app = new SpringApplication(FaceToFaceApplication.class);
        app.setWebEnvironment(false);
        app.run(args);
        // SpringApplication.run(FaceToFaceApplication.class, args);
    }

    @PostConstruct
    public void start()
        throws Exception
    {
        server.start();
    }

    @PreDestroy
    public void stop()
        throws Exception
    {
        server.stop();
    }

}
