package com.lbw.es;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Connection;

import javax.mail.MessagingException;
import java.io.*;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author LBW
 * @Classname JdTest
 * @Description TODO
 * @Date 2021/6/21 15:13
 */
public class JdTest {
    private final Map<String, String> headers = new HashMap<>();
    private Map<String, String> data = new HashMap<>();
    private AtomicReference<Boolean> login = new AtomicReference<>(false);


    /**
     * 等待扫描二维码
     *
     * @return 扫描二维码后获得的令牌
     */
    public String qRCode() {
        //获取二维码
        String QRCode = "https://qr.m.jd.com/show?appid=133&size=147&t=" + System.currentTimeMillis();
        Connection.Response QRCodeResponse = RequestUtil.request(QRCode, Connection.Method.GET);
        Map<String, String> cookies = QRCodeResponse.cookies();
        copyFileByBuffer("./test.jpg", QRCodeResponse.bodyStream());
        //监听是否扫描二维码
        String QRCodeMonitor = "https://qr.m.jd.com/check?callback=jQuery7836539&appid=133&token=" + cookies.get("wlfstk_smdl") + "&_=" + System.currentTimeMillis();
        String cookie = cookieToString(cookies);
        headers.put("Referer", "https://passport.jd.com/");
        headers.put("Cookie", cookie);
        String ticket = "";
        while (true) {
            Connection.Response qRCodeMonitorResponse = RequestUtil.request(QRCodeMonitor, Connection.Method.GET, headers);
            String qRCodeMonitorBody = qRCodeMonitorResponse.body();
            String replace = qRCodeMonitorBody.replace("jQuery7836539(", "");
            String replace1 = replace.replace(")", "");
            JSONObject jsonObject = JSON.parseObject(replace1);
            if ("200".equals(jsonObject.get("code").toString())) {
                ticket = jsonObject.get("ticket").toString();
                break;
            } else {
                System.out.println(qRCodeMonitorBody);
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return ticket;
    }

    /**
     * 登录 将cookie存到headers中
     *
     * @param ticket 令牌
     */
    public void jdLogin(String ticket) {

        //登录
        headers.put("Accept", "application/json, text/javascript, */*; q=0.01");
        headers.put("Referer", "https://passport.jd.com/uc/login?ltype=logout");
        headers.put("Connection", "Connection");
        String login = "https://passport.jd.com/uc/qrCodeTicketValidation?t=" + ticket + "&ReturnUrl=";
        Connection.Response response = RequestUtil.request(login, Connection.Method.GET, headers);
        String cookie = cookieToString(response.cookies());
        headers.put("Cookie", cookie);
    }

    /**
     * 添加商品到购物车
     *
     * @param productId 商品Id
     */
    public void addProduct(String productId) {
        //添加商品到购物车
        String addProduct = "https://cart.jd.com/gate.action?pid=" + productId + "&pcount=1&ptype=1";
        Connection.Response addProductRequest = RequestUtil.request(addProduct, Connection.Method.GET, headers);
        String body = addProductRequest.body();
    }

    public JSONObject createOrder(String productId) {
        //获取购物车信息
        String getShoppingCarInfo = "https://api.m.jd.com/api?functionId=pcCart_jc_getCurrentCart&appid=JDC_mall_cart&loginType=3&" +
                "body=%7B%22serInfo%22:%7B%22area%22:%2213_1042_3528_59596%22,%22" +
                "user-key%22:%22b002f4ad-bc6e-486f-afde-7eb462497fe3%22%7D,%22cartExt%22" +
                ":%7B%22specialId%22:1%7D%7D";
        headers.put("Referer", "https://cart.jd.com/");
        headers.put("Origin", "https://cart.jd.com");
        Connection.Response shoppingCarInfoResponse = RequestUtil.request(getShoppingCarInfo, Connection.Method.GET, headers);
        String productInfo = shoppingCarInfoResponse.body();
        JSONObject jsonObject = JSON.parseObject(productInfo);
        JSONArray jsonArray = jsonObject.getJSONObject("resultData").getJSONObject("cartInfo").getJSONArray("vendors");
        JSONObject product = new JSONObject();
        for (Object o : jsonArray) {
            JSONObject jsonObject1 = (JSONObject) o;
            JSONObject jsonObject2 = jsonObject1.getJSONArray("sorted").getJSONObject(0).getJSONObject("item");
            if (productId.equals(jsonObject2.get("Id").toString())) {
                product = jsonObject2;
                break;
            }
        }
        //结算
        String ordersUrl = "https://api.m.jd.com/api";

        String json = "{\"operations\":[{\"TheSkus\":[{\"Id\":\"" + product.get("id") + "\",\"num\":" + product.get("Num")
                + ",\"skuUuid\":\"" + product.get("skuUuid") + "\",\"useUuid\":false}]}],\"serInfo\":{\"area\":\"13_1042_3528_59596\",\"user-key\":\"b002f4ad-bc6e-486f-afde-7eb462497fe3\"}}";

        Map<String, String> data = new HashMap<>();
        data.put("functionId", "pcCart_jc_cartCheckSingle");
        data.put("appid", "JDC_mall_cart");
        data.put("body", json);
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        Connection.Response request = RequestUtil.request(ordersUrl, Connection.Method.GET, headers, data);
        //
        String url = "https://trade.jd.com/shopping/order/getOrderInfo.action";
        Connection.Response addOrder = RequestUtil.request(url, Connection.Method.GET, headers);
        return product;
    }

    /**
     * 提交订单
     *
     * @param product 订单的商品信息
     */
    public void submitOrder(JSONObject product) {
        //提交订单
        data.clear();
        data.put("overseaPurchaseCookies", "");
        data.put("vendorRemarks", "[{\"venderId\":\"" + product.get("olderVendorId") + "\",\"remark\":\"\"}]");
        data.put("submitOrderParam.sopNotPutInvoice", "true");
        data.put("submitOrderParam.trackID", "TestTrackId");
        data.put("presaleStockSign", "1");
        data.put("submitOrderParam.ignorePriceChange", "0");
        data.put("submitOrderParam.btSupport", "0");
        data.put("submitOrderParam.eid", "MMOZDKXQWJYOUBYWDVBSCIH25T7PWXUQMZCHUV6YR46ULCSJJ6N6QKVGZXSO66DANHZVMYZISL7B5MPB6ABANLXIXA");
        data.put("submitOrderParam.fp", "07d93be69af10ec42d2f2342b4dff083");
        data.put("submitOrderParam.jxj", "1");
        String submitOderUrl = "https://trade.jd.com/shopping/order/submitOrder.action?&presaleStockSign=1";
        Connection.Response submitOderResponse = RequestUtil.request(submitOderUrl, Connection.Method.POST, headers, data);

    }

    public String cookieToString(Map<String, String> cookies) {
        StringBuilder cookie = new StringBuilder();
        for (Map.Entry<String, String> stringStringEntry : cookies.entrySet()) {
            cookie.append(stringStringEntry.getKey() + "=" + stringStringEntry.getValue() + ";");
        }
        return cookie.toString();
    }


    /**
     * 拷贝文件：将src复制到target
     *
     * @param src
     * @throws IOException
     */
    private static void copyFileByBuffer(String src, BufferedInputStream bis) {
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        try {

            // 优先使用高级流，速度快
            File file = new File(src);

            fos = new FileOutputStream(file);

            bos = new BufferedOutputStream(fos);
            byte[] buf = new byte[1024];
            int lenth = -1;
            lenth = bis.read(buf);// 读
            while (lenth != -1) {
                bos.write(buf, 0, lenth);// 写
                lenth = bis.read(buf);// 读
            }
            // 先关高级流
            // 实际上关闭高级流的同时，低级流也会被关掉了
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bis.close();
                bos.close();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }

    /**
     * 监控商品库存
     *
     * @param productId
     * @throws Exception
     */
    public void listenProduct(String productId) {
        String url = "https://item-soa.jd.com/getWareBusiness?skuId=" + productId;
        int count = 0;
//        long time1 = Long.parseLong("1625068797000");
//        long time2 = Long.parseLong("1625068810000");
        while (true) {
            try {
//                long timeMillis = System.currentTimeMillis();
//                if (timeMillis >= time1 && timeMillis <= time2) {
//                    Thread.sleep(100);
//                } else {
//                    continue;
//                }

                count++;
                if (count == 20) {
                    System.out.println("----------保持登录状态-----------" + loginState());
                    count = 0;
                }
                String s = RequestUtil.request(url, Connection.Method.GET).body();
                JSONObject jsonObject = JSON.parseObject(s);
                String string = null;
                try {
                    string = jsonObject.getJSONObject("stockInfo").getString("stockDesc");
                } catch (Exception e) {
                    if (string == null) {
                        System.out.println("请求频繁等待半个小时");
                        Thread.sleep(1000 * 60 * 30);
                    }
                    Thread.sleep(1000 * 10);
                    continue;
                }
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(System.currentTimeMillis());
                if (string.contains("无货")) {
                    System.out.println(dateFormat.format(date) + string);
                } else {
                    SendEamil.eamil(dateFormat.format(date) + "有货了快去抢", "有货了");
                    break;
                }
                Thread.sleep(1000*10);

            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

        }
    }

    public String getProductInfo(String productId) {
        String url = "https://item-soa.jd.com/getWareBusiness?skuId=" + productId;
        String s = RequestUtil.request(url, Connection.Method.GET, headers).body();
        JSONObject jsonObject = JSON.parseObject(s);
        return jsonObject.toJSONString();
    }

    public Boolean loginState() {
        try {
            String callback = "jQuery7131433";
            String url = "https://home.jd.com/2014/data/user/isUserRealNameAuth.action?callback=" + callback + "&_=1624585459587";
            Connection.Response response = RequestUtil.request(url, Connection.Method.GET, headers);
            if (response.body().contains(callback)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public void buyProduct(String productId) {

        //扫描登录获得令牌
        System.out.println("--------------------------扫码登录--------------------------");
        String ticket = qRCode();
        //登录京东
        jdLogin(ticket);
        System.out.println("--------------------------登录成功--------------------------");

        System.out.print("请输入要抢购的商品(输入0退出)：");
//            Scanner scanner = new Scanner(System.in);
//            String productId = scanner.nextLine();
        if (productId.equals("0")) {
            System.out.println("--------------------------退出抢购--------------------------");
        }
        long timeMillis = System.currentTimeMillis();
        System.out.println("--------------------------开始监听商品库存--------------------------");
        listenProduct(productId);
        System.out.println("--------------------------商品有库存，开始抢购--------------------------");
        addProduct(productId);
        JSONObject order = createOrder(productId);
        submitOrder(order);
        System.out.println("--------------------------抢购成功快去付款吧--------------------------");
        System.out.println("共耗时：" + (System.currentTimeMillis() - timeMillis) + "ms");


    }

    public static void main(String[] args) throws Exception {
        JdTest jdTest = new JdTest();
        jdTest.buyProduct("100010793473");
//        String json="{\"operations\":[{\"TheSkus\":[{\"Id\":\"23274670145\",\"num\":3,\"skuUuid\":\"F2h4dQ964979953801908224\",\"useUuid\":false}]}],\"serInfo\":{\"area\":\"13_1042_3528_59596\",\"user-key\":\"b002f4ad-bc6e-486f-afde-7eb462497fe3\"}}";
//        System.out.println(json);

    }

}
