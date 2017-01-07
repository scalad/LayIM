package com.silence.config

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
    
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private var onlineCount: Int = 0
    
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private val webSocketSet: CopyOnWriteArraySet[WebSocket] = new CopyOnWriteArraySet[WebSocket]()
    
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private var session: Session = null
    
    //返回当前在线数量
    def getOnlineCount(): Int = synchronized(onlineCount)
    
    //线程安全，在线自增
    def addOnlineCount() = synchronized(onlineCount += 1)
    
    //线程安全，在线自减
    def subOnlineCount(): Unit = synchronized(onlineCount -= 1)
    
    //发送文本消息
    def sendMessage(message: String): Unit =  session.getBasicRemote().sendText(message)
    
    //群发自定义消息
    def sendInfo(message: String): Unit = {
        var itor = webSocketSet.iterator()
        while(itor.hasNext){
          itor.next().sendMessage(message)
        }
    }
    
    //连接建立成功调用的方法 
    @OnOpen
    def onOpen(session: Session): Unit =  {
         this.session = session
        //加入set中
        webSocketSet.add(this)     
        //在线数加1
        addOnlineCount()      
        println("有新连接加入！当前在线人数为" + getOnlineCount())
        sendMessage("hello world")
    }
    
    //发生错误时调用
    @OnError
    def onError(session: Session, error: Throwable): Unit =  {
        println("发生错误")
        onClose(session);
    }
    
    //收到客户端消息后调用的方法
    @OnMessage
    def onMessage(message: String, session: Session) {
        println("来自客户端的消息:" + message)
    }
    
    //连接关闭调用的方法
    @OnClose
    def onClose(session: Session) = {
        //从set中删除
        webSocketSet.remove(this)
        //在线数减1
        subOnlineCount();           
        println("有一连接关闭！当前在线人数为" + getOnlineCount());
    }
        
}