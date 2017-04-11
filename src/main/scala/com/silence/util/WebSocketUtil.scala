package com.silence.util

import scala.beans.BeanProperty
import javax.websocket.Session
import java.util.HashMap

object WebSocketUtil {
  
    @BeanProperty final var sessions: HashMap[Integer, Session] = new HashMap[Integer, Session]()
  
    def sendMessage(message: String, session: Session): Unit = {
        session.getBasicRemote().sendText(message)
    }
}