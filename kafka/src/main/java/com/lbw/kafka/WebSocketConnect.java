package com.lbw.kafka;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.URI;
import java.net.URISyntaxException;

//WebSocketClient连接
public class WebSocketConnect {//websocket建立连接         
    public void ClientConnect() {

            try {
      WebSocketClient webSocketClient = new WebSocketClient(new URI("wss://lsjd.f-ui.cn/ws/120")) {
//打开链接
                    @Override 
                    public void onOpen(ServerHandshake shake) {
                        System.out.println("握手。。。"); 
                    }
                    //这个方法自动接收服务器发过来的信息,直接在此处调用自己写的方法即可.本人将消息存入到session中，别处可以监听，然后取出再清空
                    @Override 
                    public void onMessage(String msgString) {
                        System.out.println("websocket返回消息" + msgString); 
                    }
                    //客户端发生错误,即将关闭!
                    @Override 
                    public void onError(Exception e) { 
                        System.out.println("发生错误已关闭"); 
                    } 
                    
                    //关闭链接
                    @Override 
                    public void onClose(int arg0, String arg1, boolean arg2) { 
                        System.out.println("链接已关闭"); 
                    }
                }; 
                webSocketClient.connect();
                System.out.println("建立websocket连接");

            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
    //关闭websocket连接
        public void closeWebSocket(HttpServletRequest request) {
            HttpSession session = request.getSession();
            WebSocketClient webSocketClient = (WebSocketClient) session.getAttribute("webSocketClient");
            webSocketClient.close();
            System.out.println("websocket主动关闭！");
        }
}