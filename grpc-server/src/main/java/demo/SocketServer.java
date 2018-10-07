package demo;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@ServerEndpoint(value = "/websocket")
public class SocketServer {
    public static List<String> shops = new ArrayList<>();
    @OnOpen
    public void onOpen(Session session) {
        System.out.println("add new session");
        new Timer().scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run(){
                try {
                    for (String shop : shops) {
                        sendMessage("{\"shop\":\"" + shop +"\",\"content\":\"wat\",\"time\":123}"
                                , session);
                    }
                    sendMessage("{\"shop\":\"" + "你已经" +"\",\"content\":\"连接\",\"time\":上了}"
                            , session);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        },0,5000);
    }


//    @OnClose
//    public void onClose() {
//        webSocketSet.remove(this);
//    }


//    //接收到服务2的信息后，发送信息给每个对象
//    @OnMessage
//    public void onMessage(String message, Session session) {
//        for (SocketServer item : webSocketSet) {
//            try {
//                item.sendMessage(message);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    @OnError
//    public void onError(Session session, Throwable error) {
//        error.printStackTrace();
//    }

    //发送信息
    private void sendMessage(String message, Session session) throws IOException {
        session.getBasicRemote().sendText(message);
    }

}