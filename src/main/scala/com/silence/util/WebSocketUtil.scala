package com.silence.util

import javax.websocket.Session
import java.util.concurrent.CopyOnWriteArraySet
import com.silence.websocket.WebSocket

object WebSocketUtil {
         
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
    
    //发送文本消息
    def sendMessage(message: String, sess: Session): Unit =  sess.getBasicRemote().sendText(message)
    
}