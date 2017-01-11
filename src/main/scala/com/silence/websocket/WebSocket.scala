package com.silence.websocket

import javax.websocket.server.ServerEndpoint
import org.springframework.stereotype.Component
import java.util.concurrent.CopyOnWriteArraySet
import javax.websocket.Session
import javax.websocket.OnOpen
import javax.websocket.OnError
import javax.websocket.OnMessage
import javax.websocket.OnClose
import com.silence.util.WebSocketUtil
import java.io.File
import java.io.FileOutputStream
import java.io.BufferedOutputStream
import org.apache.commons.io.FileUtils

@ServerEndpoint(value = "/websocket")
@Component
class WebSocket {
        
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private var session: Session = null
    
    //群发自定义消息
    def sendInfo(message: String): Unit = {
        println("群发消息")
        var itor = WebSocketUtil.webSocketSet.iterator()
        while(itor.hasNext){
            var webSocket: WebSocket = itor.next()
            WebSocketUtil.sendMessage(message, webSocket.session)
        }
    }
    
    //连接建立成功调用的方法 
    @OnOpen
    def onOpen(sess: Session): Unit =  {
        session = sess
        //加入set中
     		println(WebSocketUtil.webSocketSet.size())
        WebSocketUtil.webSocketSet.add(WebSocket.this)
        println(WebSocketUtil.webSocketSet.size())
        //在线数加1
        WebSocketUtil.addOnlineCount()
        println("有新连接加入！,sessionId=" + session.getId + ",当前在线人数 = " + WebSocketUtil.getOnlineCount())
        WebSocketUtil.sendMessage("hello world", sess)
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
    
    @OnMessage
    def onFileUpload(byte: Array[Byte], last: Boolean, session: Session){
        println("Binary Data")
        println(byte.length)
        FileUtils.writeByteArrayToFile(new File("e:\\test.txt"), byte)
    }
    
    //连接关闭调用的方法
    @OnClose
    def onClose(session: Session) = {
        //从set中删除
        WebSocketUtil.webSocketSet.remove(this)
        //在线数减1
        WebSocketUtil.subOnlineCount()
        println("有一连接关闭！当前在线人数为" + WebSocketUtil.getOnlineCount())
    }
        
}