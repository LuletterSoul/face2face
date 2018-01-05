package com.luv.face2face.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 16:49 2018/1/5.
 * @since luv-face2face
 */

@Configuration
@EnableAsync
public class ServerConfiguration
{
    @Bean
    public ThreadPoolTaskExecutor getExecutor()
    {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(25);
        executor.initialize();
        return executor;
    }
}
