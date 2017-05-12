package com.silence.websocket.domain

import scala.beans.BeanProperty

/**
 * @description 服务器端WebSocket领域对象
 * @date 2017-05-11
 * @author silence
 */
object Domain {
  
    /**
     * @description 添加群信息
     */
    class Group {
      	@BeanProperty var groupId: Integer = _
  			@BeanProperty var remark: String = _
    }
    
    /**
     * @description 同意添加好友
     */
    class AgreeAddGroup {
        @BeanProperty var toUid: Integer = _
        @BeanProperty var groupId: Integer = _
        @BeanProperty var messageBoxId: Integer = _
        override def toString = "toUid=" + toUid + ",groupId=" + groupId + ",messageBoxId=" + messageBoxId
    }
    
}