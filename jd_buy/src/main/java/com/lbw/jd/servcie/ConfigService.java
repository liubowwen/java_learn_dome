package com.lbw.jd.servcie;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * @author ：lbw
 * @date ：Created in 2021/8/1 16:10
 * @description：TODO
 */
public class ConfigService {
    private static final Properties PROPERTIES = new Properties();

    static {
        // 使用InPutStream流读取properties文件
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader("./application.properties"));
            PROPERTIES.load(bufferedReader);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                assert bufferedReader != null;
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static String getProperties(String key) throws Exception {
        if (PROPERTIES == null) {
            throw new Exception("配置文件读取失败");
        }
        return PROPERTIES.getProperty(key);
    }

}
