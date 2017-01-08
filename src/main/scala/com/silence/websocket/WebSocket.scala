package com.silence.websocket

import javax.websocket.server.ServerEndpoint
import org.springframework.stereotype.Component
import java.util.concurrent.CopyOnWriteArraySet
import javax.websocket.Session
import javax.websocket.OnOpen
import javax.websocket.OnError
import javax.websocket.OnMessage
import javax.websocket.OnClose

@ServerEndpoint(value = "/websocket")
@Component
class WebSocket {
        
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private var session: Session = null
    
    //发送文本消息
    def sendMessage(message: String, sess: Session): Unit =  sess.getBasicRemote().sendText(message)
    
    //群发自定义消息
    def sendInfo(message: String): Unit = {
        println("群发消息")
        var itor = WebSocketConfig.webSocketSet.iterator()
        while(itor.hasNext){
          var webSocket: WebSocket = itor.next()
          sendMessage(message, webSocket.session)
        }
    }
    
    //连接建立成功调用的方法 
    @OnOpen
    def onOpen(sess: Session): Unit =  {
        session = sess
        //加入set中
     		println(WebSocketConfig.webSocketSet.size())
        WebSocketConfig.webSocketSet.add(WebSocket.this)
        println(WebSocketConfig.webSocketSet.size())
        //在线数加1
        WebSocketConfig.addOnlineCount()
        println("有新连接加入！,sessionId=" + session.getId + ",当前在线人数 = " + WebSocketConfig.getOnlineCount())
        sendMessage("hello world", sess)
    }
    
    //发生错误时调用
    @OnError
    def onError(session: Session, error: Throwable): Unit =  {
    		println(session.getId)
        println("发生错误" + error.printStackTrace())
        onClose(session);
    }
    
    //收到客户端消息后调用的方法
    @OnMessage
    def onMessage(message: String, session: Session) {
        println("来自客户端的消息:" + message)
        sendInfo(message)
    }
    
    //连接关闭调用的方法
    @OnClose
    def onClose(session: Session) = {
        //从set中删除
        WebSocketConfig.webSocketSet.remove(this)
        //在线数减1
        WebSocketConfig.subOnlineCount()
        println("有一连接关闭！当前在线人数为" + WebSocketConfig.getOnlineCount())
    }
        
}

object WebSocketConfig {
      
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private var onlineCount: Int = 0
    
    //返回当前在线数量
    def getOnlineCount(): Int = synchronized(onlineCount)
    
    //线程安全，在线自增
    def addOnlineCount() = synchronized(onlineCount += 1)
    
    //线程安全，在线自减
    def subOnlineCount(): Unit = synchronized(onlineCount -= 1)
    
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    var webSocketSet: CopyOnWriteArraySet[WebSocket] = new CopyOnWriteArraySet[WebSocket]()

}