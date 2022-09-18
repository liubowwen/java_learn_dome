package com.lbw.jd.servcie;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Connection;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author ：lbw
 * @date ：Created in 2022/9/18 10:17
 * @description：
 */
public class BuyIphone {
    public static void main(String[] args) throws Exception {

        BuyIphone buyIphone = new BuyIphone();
        long lastTime = System.currentTimeMillis();
        SimpleDateFormat sd = new SimpleDateFormat("HH:mm:ss");
        while (true) {
            try {
                Thread.sleep(1000);
                List<String> buy = buyIphone.buy();
                if (buy.size() > 0) {
                    for (String s : buy) {
                        if (s.contains("256")) {
                            System.out.println(sd.format(new Date()) + ": " + s + ",  ");
                            long currentTimeMillis = System.currentTimeMillis();
                            if (currentTimeMillis - lastTime >= 1000 * 60 && (s.contains("深空黑色") || s.contains("银"))) {
                                SendEamil.eamil(s, "有货了");
                                lastTime = currentTimeMillis;
                            }
                        }else {
                            System.out.println(sd.format(new Date()) + ": " + s + ",  ");
                        }
                    }
                } else {
                    System.out.println(sd.format(new Date()) + ":无货");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    public List<String> buy() {
        String url = "https://www.apple.com.cn/shop/pickup-message-recommendations?mts.0=regular&location=%E5%B1%B1%E4%B8%9C%20%E6%B5%8E%E5%8D%97%20%E5%8E%86%E4%B8%8B%E5%8C%BA&product=MQ0W3CH/A";
        Connection.Response response = RequestUtil.request(url, Connection.Method.GET);
        String body = response.body();
        JSONObject jsonObject = JSONObject.parseObject(body);
        JSONObject partsAvailability = Optional.of(jsonObject.getObject("body", JSONObject.class))
                .map(bodyJsonObject -> bodyJsonObject.getObject("PickupMessage", JSONObject.class))
                .map(pickupMessage -> pickupMessage.getObject("stores", JSONArray.class))
                .map(stores -> stores.getJSONObject(0))
                .map(stores1 -> stores1.getObject("partsAvailability", JSONObject.class))
                .get();
        List<String> list = new ArrayList<>();
        if (partsAvailability.size() != 0) {
            for (Map.Entry<String, Object> entry : partsAvailability.entrySet()) {
                JSONObject entryValue = (JSONObject) entry.getValue();
                JSONObject jsonObject1 = entryValue.getObject("messageTypes", JSONObject.class).getObject("regular", JSONObject.class);
                list.add(jsonObject1.get("storePickupQuote") + " " + jsonObject1.get("storePickupProductTitle"));
            }
        }
        return list;
    }
}
