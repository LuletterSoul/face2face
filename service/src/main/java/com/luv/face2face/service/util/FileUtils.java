package com.luv.face2face.service.util;


import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 19:06 2018/1/8.
 * @since face2face
 */

@Slf4j
public class FileUtils
{
    /**
     * 根据相对文件名得到绝对路径;
     * @param relativePath
     * @return
     */
    public static String getAbsolutePath(String relativePath)
    {
        try {
            String projectRoot = getProjectRoot();
            File file =  new File(projectRoot, relativePath);
            if (!file.exists()) {
                file.mkdirs();
            }
            return file.getAbsolutePath();
        } catch (FileNotFoundException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public static String getProjectRoot()
        throws FileNotFoundException
    {
        File path = new File(ResourceUtils.getURL("classpath:").getPath());
        if (!path.exists()) path = new File("");
        return path.getAbsolutePath();
    }
}
