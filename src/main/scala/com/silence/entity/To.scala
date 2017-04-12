package com.silence.entity

import scala.beans.BeanProperty

/**
 * @description 对方的信息
 * @date 2017-04-09
 * @author silence
 */
class To {
  
    //对方的id
    @BeanProperty var id: Integer = _
    
    //名字
    @BeanProperty var username: String = _
    
    //签名
    @BeanProperty var sign: String = _
      
    //头像
    @BeanProperty var avatar: String = _
      
    //状态
    @BeanProperty var status: String = _
    
    //聊天类型，一般分friend和group两种，group即群聊
    @BeanProperty var Type: String = _
    
    override def toString = "id = " + id + ", username = " + username + ", sign = " + sign + ", avatar = " + avatar + ", status = " + status + ", type = " + Type  
    
}