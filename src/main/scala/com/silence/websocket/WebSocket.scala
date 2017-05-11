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
import com.silence.entity.Message
import com.silence.service.RedisService
import com.silence.common.SystemConstant
import com.silence.util.WebUtil
import com.silence.util.WebSocketUtil
import com.silence.Application

/**
 * @description websocket服务器处理消息
 * @date 2017-04-09
 * @author silence
 */
@ServerEndpoint(value = "/websocket/{uid}")
@Component
class WebSocket {
  
    private final val LOGGER: Logger = LoggerFactory.getLogger(classOf[WebSocket])
    
    private lazy val redisService: RedisService = Application.getApplicationContext.getBean(classOf[RedisService])
    
    private final val gson: Gson = new Gson    
    
    private var uid: Integer = _
    
    /**
     * @description 服务器接收到消息调用
     * @param message 消息体
     * @param session 
     */
    @OnMessage
    def onMessage(message: String, session: Session) {
        val mess = gson.fromJson(message.replaceAll("type", "Type"), classOf[Message])
        LOGGER.info("来自客户端的消息: " + mess)
        mess.getType match {
            case "message" => {
                WebSocketUtil.sendMessage(mess)    
            }
            case "checkOnline" => {
                val result = WebSocketUtil.checkOnline(mess, session)
                WebSocketUtil.sendMessage(gson.toJson(result), session)
            }
            case "addGroup" => {
                WebSocketUtil.addGroup(uid, mess)
            }
            case "changOnline" => {
                WebSocketUtil.changeOnline(uid, mess.getMsg)
            }
            case "addFriend" => {
                WebSocketUtil.addFriend(uid, mess)
            }
            case "agreeAddFriend" => {
                if (WebSocketUtil.getSessions.get(mess.getTo.getId) != null) {                  
                  	WebSocketUtil.sendMessage(message, WebSocketUtil.getSessions.get(mess.getTo.getId))
                }
            }
            case "unHandMessage" => {
                val result = WebSocketUtil.countUnHandMessage(uid)
                WebSocketUtil.sendMessage(gson.toJson(result), session)
            }
            case "delFriend" => {
                WebSocketUtil.removeFriend(uid, mess.getTo.getId)
            }
            case _ => {
                LOGGER.info("No Mapping Message!")
            }
        }
    }
    
    /**
     * @description 首次创建链接
     * @param session
     * @param uid
     */
    @OnOpen
    def onOpen(session: Session, @PathParam("uid") uid: Integer): Unit =  {
    		this.uid = uid
        WebSocketUtil.sessions.put(uid, session)
        LOGGER.info("userId = " + uid + ",sessionId = " + session.getId + ",新连接加入!")
        redisService.setSet(SystemConstant.ONLINE_USER, uid + "")
    }
    
    /**
     * @description 链接关闭调用
     * @param session
     */
    @OnClose
    def onClose(session: Session) = {
        LOGGER.info("userId = " + uid + ",sessionId = " + session.getId + "断开连接!")
        WebSocketUtil.getSessions().remove(uid)
        redisService.removeSetValue(SystemConstant.ONLINE_USER, uid + "")
    }
    
    /**
     * @description 服务器发送错误调用
     * @param session
     * @param error
     */
    @OnError
    def onError(session: Session, error: Throwable) = {
    		LOGGER.info(session.getId + " 发生错误" + error.printStackTrace)
        onClose(session);
    }
 
}