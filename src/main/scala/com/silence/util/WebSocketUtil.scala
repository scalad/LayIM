package com.silence.util

import scala.collection.mutable.HashMap
import scala.beans.BeanProperty
import javax.websocket.Session

object WebSocketUtil {
  
    @BeanProperty final var sessions: HashMap[Integer, Session] = new HashMap[Integer, Session]()
  
    def sendMessage(message: String, session: Session): Unit = {
        session.getBasicRemote().sendText(message)
    }
}