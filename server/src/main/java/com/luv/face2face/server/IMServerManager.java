package com.luv.face2face.server;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 19:57 2018/1/6.
 * @since luv-face2face
 */
@Slf4j
@Component
public class IMServerManager
{

    private IMServer server;

    @Autowired
    @Qualifier("logicNettyServer")
    public void setServer(IMServer server)
    {
        this.server = server;
    }

    @EventListener
    public void onApplicationReadyEvent(ApplicationReadyEvent readyEvent)
        throws Exception
    {
        server.start();
        log.info("Logic netty server start..................");
    }
}
