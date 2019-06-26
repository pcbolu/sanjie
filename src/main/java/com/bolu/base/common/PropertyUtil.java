package com.bolu.base.common;

import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * 获取 set.properties 属性配置类
 */
public class PropertyUtil {
	private static final Logger logger = Logger.getLogger(PropertyUtil.class);
	
    private static Properties props;
    static{
        loadProps();
    }

    synchronized static private void loadProps(){
        logger.info("开始加载properties文件内容.......");
        props = new Properties();
        InputStream in = null;
        try {
            in = PropertyUtil.class.getResourceAsStream("/set.properties");
            props.load(new InputStreamReader(in, "utf-8"));
        } catch (FileNotFoundException e) {
            logger.error("加载文件异常:"+e.getMessage());
        } catch (IOException e) {
            logger.error("读取文件异常:"+e.getMessage());
        } finally {
            try {
                if(null != in) {
                    in.close();
                }
            } catch (IOException e) {
                logger.error("关闭文件流异常:"+e.getMessage());
            }
        }
        logger.info("加载properties文件内容完成...........");
    }

    public static String getProperty(String key){
        if(null == props) {
            loadProps();
        }
        return props.getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        if(null == props) {
            loadProps();
        }
        return props.getProperty(key, defaultValue);
    }
}
