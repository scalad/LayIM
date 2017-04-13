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
import com.silence.entity.Receive
import com.silence.enties.User
import scala.collection.JavaConversions
import com.silence.service.UserService
import java.util.List
import com.silence.Application

object WebSocketUtil {
  
    private final val LOGGER: Logger = LoggerFactory.getLogger(WebSocketUtil.getClass)
  
    @BeanProperty final var sessions: HashMap[Integer, Session] = new HashMap[Integer, Session]()
  
    private lazy val redisService: RedisService = Application.getApplicationContext.getBean(classOf[RedisService])
    
    private lazy val userService: UserService = Application.getApplicationContext.getBean(classOf[UserService])
    
    private final val gson: Gson = new Gson     
    
    /**
     * @description 发送消息
     * @message Message
     */
    def sendMessage(message: Message): Unit = synchronized {
        LOGGER.info("发送好友消息和群消息!");
        //封装返回消息格式
    		var gid = message.getTo.getId
        val receive = WebSocketUtil.getReceiveType(message)
        val key: Integer = message.getTo.getId
        //聊天类型，可能来自朋友或群组
        if("friend".equals(message.getTo.getType)) {            
          	//是否在线
          	if(WebSocketUtil.getSessions.containsKey(key)) {
          		  val session: Session = WebSocketUtil.getSessions.get(key)
          		  receive.setStatus(1)
          			WebSocketUtil.sendMessage(gson.toJson(receive).replaceAll("Type", "type"), session)
          	}
            //保存为离线消息,默认是为离线消息
            userService.saveMessage(receive)
        } else {
            receive.setId(gid)
        		//找到群组id里面的所有用户
            val users:List[User] = userService.findUserByGroupId(gid)
            //过滤掉本身的uid
            JavaConversions.collectionAsScalaIterable(users).filter(_.id != message.getMine.getId)
              .foreach { user => {
                  	  //是否在线
                  	  if(WebSocketUtil.getSessions.containsKey(user.getId)) {
                  		    val session: Session = WebSocketUtil.getSessions.get(user.getId)
                  		    receive.setId(gid)
                  		    receive.setStatus(1)
                  				WebSocketUtil.sendMessage(gson.toJson(receive).replaceAll("Type", "type"), session)
                  	  } else {
                  	      //保存为离线消息
                  	      receive.setToid(user.getId)
                  	      receive.setId(key)
                  	  }
                  	  userService.saveMessage(receive)               		  
            }}
        }
    }
    
    /**
     * @description 监测某个用户的离线或者在线
     * @param message
     * @param session
     */
    def checkOnline(message: Message, session: Session): Unit = synchronized {
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
     * @description 封装返回消息格式
     * @param Message
     * @return Receive
     */
    def getReceiveType(message: Message): Receive = {
        val mine = message.getMine
        val to = message.getTo
        var receive = new Receive
        receive.setId(mine.getId)
        receive.setFromid(mine.getId)
        receive.setToid(to.getId)
        receive.setUsername(mine.getUsername)
        receive.setType(to.getType)
        receive.setAvatar(mine.getAvatar)
        receive.setContent(mine.getContent)
        receive.setTimestamp(DateUtil.getLongDateTime)
        receive
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