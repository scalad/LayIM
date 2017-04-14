package com.silence.entity

import scala.beans.BeanProperty

/**
 * @description 服务器用于接收消息
 * @date 2017-04-09
 * @author silence
 */
class Message {
  
    //随便定义，用于在服务端区分消息类型
    @BeanProperty var Type: String = _

    //我的信息
    @BeanProperty var mine: Mine = _
    
    //对方信息
    @BeanProperty var to: To = _
    
    //额外的信息
    @BeanProperty var msg: String = _

    override def toString = "type = " + Type + ", mine = " + mine + ",to = " + to + ", msg = " + msg
    
}
