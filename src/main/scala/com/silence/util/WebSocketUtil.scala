package com.silence.util

import scala.beans.BeanProperty
import javax.websocket.Session
import java.util.HashMap
import com.silence.service.RedisService
import com.silence.common.SystemConstant
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import com.silence.entity.Message
import com.google.gson.Gson

object WebSocketUtil {
  
    private final val LOGGER: Logger = LoggerFactory.getLogger(WebSocketUtil.getClass)
  
    @BeanProperty final var sessions: HashMap[Integer, Session] = new HashMap[Integer, Session]()
  
    private lazy val redisService: RedisService = WebUtil.getBean(classOf[RedisService])
    
    private final val gson: Gson = new Gson     
    
    /**
     * @description 监测某个用户的离线或者在线
     * @param message
     * @param session
     */
    def checkOnline(message: Message, session: Session): Unit = {
          LOGGER.info("监测在线状态" + message.getTo.toString)
          val uids = redisService.getSets(SystemConstant.ONLINE_USER)
          var result = new HashMap[String, String]
          result.put("type", "checkOnline")
          if (uids.contains(message.getTo.getId.toString)) {
              result.put("status", "在线")
              WebSocketUtil.sendMessage(gson.toJson(result), session)
          } else {
              result.put("status", "离线")
            	WebSocketUtil.sendMessage(gson.toJson(result), session)
          }
    }
    
    
    def sendMessage(message: String, session: Session): Unit = synchronized {
        session.getBasicRemote().sendText(message)
    }
    
    /**
     * @description 用户在线切换状态
     * @param uid
     * @param redisService
     */
    def changeOnline(uid: Integer, rediserService: RedisService) = synchronized {
        
        rediserService.removeSetValue(SystemConstant.ONLINE_USER, uid + "")
        
    }
   
}