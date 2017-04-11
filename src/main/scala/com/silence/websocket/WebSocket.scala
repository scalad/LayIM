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
        val receive = getReceiveType(message)
        val key: Integer = message.getTo.getId
        //聊天类型，可能来自朋友或群组
        if("friend".equals(message.getTo.getType)) {            
          	//是否在线
          	if(WebSocketUtil.getSessions.containsKey(key)) {
          		  val session: Session = WebSocketUtil.getSessions.get(key)
          			WebSocketUtil.sendMessage(gson.toJson(receive).replaceAll("Type", "type"), session)
          	} else {
          		  //处理离线消息
          	}
        } else {
            receive.setId(message.getTo.getId)
            var gid = message.getTo.getId
        		//找到群组id里面的所有用户
            val users:List[User] = userService.findUserByGroupId(gid)
            //过滤掉本身的uid
            JavaConversions.collectionAsScalaIterable(users).filter(_.id != message.getMine.getId)
              .foreach { user => {
                  	  //是否在线
                  	  if(WebSocketUtil.getSessions.containsKey(user.getId)) {
                  		  val session: Session = WebSocketUtil.getSessions.get(user.getId)
                  				  WebSocketUtil.sendMessage(gson.toJson(receive).replaceAll("Type", "type"), session)
                  	  } else {
                  		  
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
        redisService.setMap(SystemConstant.ONLINE_USER_MAP, uid+ "", session.getId)
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
        sendMessage(mess)
    }
    
    /**
     * @description 链接关闭调用
     * @param session
     */
    @OnClose
    def onClose(session: Session) = {
        LOGGER.info("userId = " + uid + ",sessionId = " + session.getId + "断开连接!")
        WebSocketUtil.getSessions().remove(uid)
        
    }
    
    /**
     * @description 服务器发送错误调用
     * @param session
     * @param error
     */
    @OnError
    def onError(session: Session, error: Throwable) = {
    		LOGGER.info(session.getId + " 发生错误" + error.printStackTrace)
    		WebSocketUtil.getSessions().remove(uid)
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
        receive.setUsername(mine.getUsername)
        receive.setType(to.getType)
        receive.setAvatar(mine.getAvatar)
        receive.setContent(mine.getContent)
        receive.setMine(false)
        receive.setTimestamp(DateUtil.getLongDateTime)
        receive
    }
}