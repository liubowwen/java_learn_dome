package com.lbw.jd.servcie;

import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;


import java.util.Map;

/**
 * @author LBW
 * @Classname RequestUtil
 * @Description TODO
 * @Date 2020/12/08 0008 13:55
 */

public class RequestUtil {

    public static Connection.Response request(String url, Connection.Method method) {
        return request(null, url, null, method, null, null);
    }

    public static Connection.Response request(String cookie, String url, Connection.Method method) {
        return request(cookie, url, null, method, null, null);
    }

    public static Connection.Response request(String cookie, String url, Connection.Method method, Map<String, String> data) {
        return request(cookie, url, data, method, null, null);
    }

    public static Connection.Response request(String url, Map<String, String> data, Connection.Method method) {
        return request(null, url, data, method, null, null);
    }

    public static Connection.Response request(String cookie, String url, Connection.Method method, String requestBody) {
        return request(cookie, url, null, method, requestBody, null);
    }

    public static Connection.Response request(String url, Connection.Method method, String requestBody) {
        return request(null, url, null, method, requestBody, null);
    }

    public static Connection.Response request(String url, Connection.Method method, String requestBody, Map<String, String> headers) {
        return request(null, url, null, method, requestBody, headers);
    }


    public static Connection.Response request(String url, Connection.Method method, Map<String, String> headers) {
        return request(null, url, null, method, null, headers);
    }
    public static Connection.Response request(String url, Connection.Method method, Map<String, String> headers,Map<String, String> data) {
        return request(null, url, data, method, null, headers);
    }


    public static Connection.Response request(String cookie, String url, Map<String, String> data,
                                 Connection.Method method, String requestBody, Map<String, String> headers) {
        Connection con = Jsoup
                .connect(url);
        con.header("User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.107 Safari/537.36 Edg/92.0.902.62");
        if (headers != null) {
            con.headers(headers);
        }
        Connection.Response respesonData = null;
        // 设置cookie和post上面的map数据
        try {
            if (data != null) {
                con.data(data);
            }
            if (requestBody != null) {
                con.requestBody(requestBody);
            }
            respesonData = con.ignoreContentType(true).method(method)
                    .execute();
            return respesonData;
        } catch (HttpStatusException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e1) {
            e1.printStackTrace();
            return null;
        }

    }

}
