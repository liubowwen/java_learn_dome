package com.lbw.kafka;

import java.net.URI;
import java.net.URISyntaxException;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

public class MyWebSocketClient extends WebSocketClient{


 
public MyWebSocketClient(URI serverUri) { super(serverUri);
}

@Override
public void onOpen(ServerHandshake arg0) {
// TODO Auto-generated method stub
System.out.println("------ MyWebSocket onOpen ------");
}

@Override
public void onClose(int arg0, String arg1, boolean arg2) {
// TODO Auto-generated method stub
System.out.println("------ MyWebSocket onClose ------");
}

@Override
public void onError(Exception arg0) {
// TODO Auto-generated method stub
System.out.println("------ MyWebSocket onError ------");
}

@Override
public void onMessage(String arg0) {
// TODO Auto-generated method stub

}

 public static void main(String[] args) throws URISyntaxException {
  URI uri=new URI("ws://10.200.0.102:9010/ws/1");
  MyWebSocketClient myClient = new MyWebSocketClient(uri);
  WebSocketConnect connect=new WebSocketConnect();
  connect.ClientConnect();

//// 往websocket服务端发送数据
//  myClient.send("此为要发送的数据内容");
 }
}