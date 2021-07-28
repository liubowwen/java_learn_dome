package com.lbw.kafka;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class FaceRecognitionInterfaceController  {

    /**
     *
     * 获取session_id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "login", method = RequestMethod.GET)
    public static String getSessionId() {
        JSONObject result = new JSONObject();
        try {
            CloseableHttpClient client = HttpClientBuilder.create().build();
            HttpPost post = new HttpPost("http://56.80.185.50:11180/business/api/login");
            post.addHeader("Content-Type", "application/json");
            JSONObject obj = new JSONObject();
            obj.put("name", "ytjj");
            obj.put("password", "3dd54eef0a48a49a22ce12d02611787e");
            StringEntity params = new StringEntity(obj.toString());
            params.setContentEncoding("utf-8");
            params.setContentType("application/json");//发送json数据需要设置contentType
            post.setEntity(params);

            CloseableHttpResponse response = client.execute(post);
            String responseString = EntityUtils.toString(response.getEntity());
            result = JSONObject.parseObject(responseString);
//
        } catch (UnsupportedEncodingException e) {
            System.out.print(e);
        } catch (ClientProtocolException e) {
            System.out.print(e);
        } catch (IOException e) {
            System.out.print(e);
        }
        return result.getString("session_id");
    }


    @RequestMapping(value = "facepic")
    @ResponseBody
    public static Object getPicUrl(HttpServletRequest request1, HttpServletResponse response1, @RequestParam("file") MultipartFile file) throws Exception {
        String data=multipartFileToBASE64(file);
        data = data.substring(22);
        Map map = new HashMap();
        String sessionid = getSessionId();
        JSONObject result = new JSONObject();
        List<FaceRecognitionInterface> faceRecognitionInterfaceList = new ArrayList<>();
        try {
            CloseableHttpClient client = HttpClientBuilder.create().build();
            HttpPost post = new HttpPost("http://56.80.185.50:11180/business/api/retrieval_repository");
            post.addHeader("Content-Type", "application/json");
            post.addHeader("session_id",sessionid);
            System.out.println("######################################");
            String pic = data;
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("limit",50);
            jsonObject.put("start",0);
            JSONObject order = new JSONObject();
            order.put("similarity",-1);
            jsonObject.put("order",order);
            JSONObject retrieval = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            jsonArray.add(66);
            retrieval.put("picture_image_content_base64",pic);
            retrieval.put("repository_ids",jsonArray);
            retrieval.put("threshold",0);
            retrieval.put("fast",true);
            jsonObject.put("retrieval",retrieval);

            System.out.println("jsonObject----"+jsonObject);

            StringEntity params = new StringEntity(jsonObject.toString());
            params.setContentEncoding("utf-8");
            params.setContentType("application/json");//发送json数据需要设置contentType
            post.setEntity(params);
            CloseableHttpResponse response = client.execute(post);
            String responseString = EntityUtils.toString(response.getEntity(),"UTF-8");

            result = JSONObject.parseObject(responseString);
            JSONArray resultArray = (JSONArray)result.get("results");
            System.out.println("&&&&&&&&restult:"+result.toJSONString());

            BASE64Encoder encode = new BASE64Encoder();

            if(resultArray!=null){
            for (int i=0;i<resultArray.size();i++){
                String base64 = encode.encode(resultArray.getJSONObject(i).get("face_image_uri").toString().getBytes());
                String picurl = "http://56.80.185.50:11180/business/api/storage/image?uri_base64="+base64;
                picurl = picurl.replaceAll("\r|\n", "");
                FaceRecognitionInterface faceRecognitionInterface = new FaceRecognitionInterface();
                faceRecognitionInterface.setName((resultArray.getJSONObject(i).get("name").toString()));
                faceRecognitionInterface.setFaceimageurl(picurl);
                faceRecognitionInterface.setPictureurl(resultArray.getJSONObject(i).get("picture_uri").toString());
                faceRecognitionInterface.setPersonid(resultArray.getJSONObject(i).get("person_id").toString());
                faceRecognitionInterface.setBornyear(resultArray.getJSONObject(i).get("born_year").toString());
                faceRecognitionInterface.setFaceimageid(resultArray.getJSONObject(i).get("face_image_id").toString());
                faceRecognitionInterface.setGender(resultArray.getJSONObject(i).get("gender").toString());
                faceRecognitionInterface.setSimilarity(resultArray.getJSONObject(i).get("similarity").toString());
                faceRecognitionInterfaceList.add(faceRecognitionInterface);
            }
            }else {
                map.put("code",1);
                map.put("msg","未获取到人脸信息");
                return map;
            }

        } catch (UnsupportedEncodingException e) {
            System.out.print(e);
        } catch (ClientProtocolException e) {
            System.out.print(e);
        } catch (IOException e) {
            System.out.print(e);
        }
        map.put("code",0);
        map.put("msg","");
        map.put("data",faceRecognitionInterfaceList);

        return map;

    }

    /**
     *
     * 获取公民身份证信息
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getGMSFHMInfo", method = RequestMethod.GET)
    public static Object getGMSFHMInfo(HttpServletRequest request1,HttpServletResponse response1) {
        String gmsfhm = request1.getParameter("gmsfhm");
        List<GmsfhmInfo> list = new ArrayList<>();
        Map map = new HashMap();
        GmsfhmInfo gmsfhmInfo = new GmsfhmInfo();
        JSONObject result = new JSONObject();
        try {
            CloseableHttpClient client = HttpClientBuilder.create().build();
            HttpPost post = new HttpPost("http://56.82.130.11:8084/GatewayMsg/rest/invoke");
            post.addHeader("Content-Type", "application/json");
            post.addHeader("sender_id", "C00000000032");
            post.addHeader("fingerprint", "{\"endUser\":\"ytkx,370602197205171315,370600210600,042832\"}");
            post.addHeader("service_id", "S00000000095");

            JSONObject obj = new JSONObject();
            gmsfhm="GMSFHM ='"+gmsfhm+"'";
            obj.put("condition", gmsfhm);
            obj.put("fields", "FQHM,GMSFHM,JHREHM,JHRYHM,LXDH,MQHM,POHM,SJJZDZXZ,XM,XZ,ZY,SSXQ");
            obj.put("sortFields", "GMSFHM");
            obj.put("codeMode", "0");
            obj.put("page", "1");
            obj.put("size", "10");

            StringEntity params = new StringEntity(obj.toString());
            params.setContentEncoding("utf-8");
            params.setContentType("application/json");//发送json数据需要设置contentType
            post.setEntity(params);
            CloseableHttpResponse response = client.execute(post);
            String responseString = EntityUtils.toString(response.getEntity());
            result = JSONObject.parseObject(responseString);
            JSONArray resultArray = (JSONArray)result.get("result");

            if(resultArray!=null) {
                for (int i = 0; i < resultArray.size(); i++) {
                    gmsfhmInfo.setGmsfzh(resultArray.getJSONObject(i).get("GMSFHM").toString());
                    gmsfhmInfo.setLxdh(resultArray.getJSONObject(i).get("LXDH").toString());
                    gmsfhmInfo.setXz(resultArray.getJSONObject(i).get("XZ").toString());
                    gmsfhmInfo.setXm(resultArray.getJSONObject(i).get("XM").toString());
                    gmsfhmInfo.setZy(resultArray.getJSONObject(i).get("ZY").toString());
                    gmsfhmInfo.setSsqh(resultArray.getJSONObject(i).get("SSXQ").toString());
                    System.out.println("XZ------"+gmsfhmInfo.getXz());
                    System.out.println("GMSFHM------"+gmsfhmInfo.getGmsfzh());
                }
            }
        } catch (Exception e) {
            System.out.print(e);
        }
        list.add(gmsfhmInfo);
        map.put("code","0");
        map.put("msg","");
        map.put("data",list);
        return map;
    }

 
    public static String multipartFileToBASE64(MultipartFile mFile) throws Exception{
        BASE64Encoder bEncoder=new BASE64Encoder();
        String[] suffixArra=mFile.getOriginalFilename().split("\\.");
        String preffix="data:image/jpg;base64,".replace("jpg", suffixArra[suffixArra.length - 1]);
        String base64EncoderImg=preffix + bEncoder.encode(mFile.getBytes()).replaceAll("[\\s*\t\n\r]", "");
        return base64EncoderImg;
    }
  
    private  String base64ToString (String str){
        String stringUrl="";
        byte[] bytes = Base64.getMimeDecoder().decode(str);
        try {
            stringUrl = new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return stringUrl;
    }
    }
