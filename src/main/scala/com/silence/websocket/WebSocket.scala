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
import com.silence.util.DateUtil
import com.silence.entity.Message
import com.silence.entity.Receive
import com.silence.service.RedisService
import com.silence.common.SystemConstant
import com.silence.util.WebUtil
import com.silence.util.WebSocketUtil
import com.silence.service.UserService
import com.silence.enties.User
import java.util.List
import java.util.HashMap
import scala.collection.JavaConversions

/**
 * @description websocket服务器处理消息
 * @date 2017-04-09
 * @author silence
 */
@ServerEndpoint(value = "/websocket/{uid}")
@Component
class WebSocket {
  
    private final val LOGGER: Logger = LoggerFactory.getLogger(classOf[WebSocket])
    
    private lazy val redisService: RedisService = WebUtil.getBean(classOf[RedisService])
    
    private lazy val userService: UserService = WebUtil.getBean(classOf[UserService])
  
    private final val gson: Gson = new Gson    
    
    private var uid: Integer = _
    
    /**
     * @description 发送消息
     * @message Message
     */
    def sendMessage(message: Message): Unit = {
        //封装返回消息格式
    		var gid = message.getTo.getId
        val receive = getReceiveType(message)
        val key: Integer = message.getTo.getId
        //聊天类型，可能来自朋友或群组
        if("friend".equals(message.getTo.getType)) {            
          	//是否在线
          	if(WebSocketUtil.getSessions.containsKey(key)) {
          		  val session: Session = WebSocketUtil.getSessions.get(key)
          			WebSocketUtil.sendMessage(gson.toJson(receive).replaceAll("Type", "type"), session)
          	} else {
          	    //保存为离线消息
          		  userService.saveMessage(receive)
          	}
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
                  				WebSocketUtil.sendMessage(gson.toJson(receive).replaceAll("Type", "type"), session)
                  	  } else {
                  	      //保存为离线消息
                  	      receive.setToid(user.getId)
                  	      receive.setId(key)
          		            userService.saveMessage(receive)               		  
                  	  }
            }}
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
                LOGGER.info("发送好友消息和群消息!");
                sendMessage(mess)    
            };
            case "checkOnline" => {
                LOGGER.info("监测在线状态" + mess.getTo.toString)
                val uids = redisService.getSets(SystemConstant.ONLINE_USER)
                var result = new HashMap[String, String]
                result.put("type", "checkOnline")
                if (uids.contains(mess.getTo.getId.toString)) {
                    result.put("status", "在线")
                    WebSocketUtil.sendMessage(gson.toJson(result), session)
                } else {
                    result.put("status", "离线")
                  	WebSocketUtil.sendMessage(gson.toJson(result), session)
                }
            }
        }
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
}