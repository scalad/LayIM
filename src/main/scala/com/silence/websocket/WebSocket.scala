package com.silence.websocket

import javax.websocket.server.ServerEndpoint
import javax.websocket.Session
import javax.websocket.OnOpen
import javax.websocket.OnError
import javax.websocket.OnMessage
import javax.websocket.OnClose
import javax.websocket.server.PathParam
import org.springframework.stereotype.Component
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import com.google.gson.Gson

@ServerEndpoint("/websocket/{uid}")
@Component
class WebSocket {
        
    private final val LOGGER: Logger = LoggerFactory.getLogger(classOf[WebSocket])
    
    //Google对JSON支持
    private final val gson: Gson = new Gson
    
    private var session: Session = null
    
    //群发自定义消息
    def sendInfo(message: String): Unit = {
        sendMessage(message, session)
    }
     
    @OnOpen
    def onOpen(sess: Session, @PathParam("uid") uid: Integer): Unit =  {
        session = sess
        LOGGER.info("userId = " + uid + ",有新连接加入！,sessionId=" + session.getId)
    }

    @OnMessage
    def onMessage(message: String, session: Session) {
        val mess = gson.fromJson(message.replaceAll("type", "Type"), classOf[Message])
        LOGGER.info("来自客户端的消息: " + mess)
    }
    
    @OnClose
    def onClose(session: Session) = {
     
    }
     
    @OnError
    def onError(session: Session, error: Throwable): Unit =  {
    		LOGGER.info(session.getId + " 发生错误" + error.printStackTrace())
        onClose(session);
    }
    
    def sendMessage(message: String, sess: Session): Unit =  sess.getBasicRemote().sendText(message)
}