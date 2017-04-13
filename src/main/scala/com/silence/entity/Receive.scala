package com.silence.entity

import scala.beans.BeanProperty
import java.lang.Long

/**
 * @description 客户端接收消息类型
 * @date 2017-04--09
 * @author silence
 */
class Receive {
  
    //发送给哪个用户
    @BeanProperty var toid: Integer = _
  
    //消息的来源ID（如果是私聊，则是用户id，如果是群聊，则是群组id）
    @BeanProperty var id: Integer = _
    
    //消息来源用户名
    @BeanProperty var username: String = _
    
    //消息来源用户头像
    @BeanProperty var avatar: String = _
    
    //聊天窗口来源类型，从发送消息传递的to里面获取
    @BeanProperty var Type: String = _
    
    //消息内容
    @BeanProperty var content: String = _  
    
    //消息id，可不传。除非你要对消息进行一些操作（如撤回）
    @BeanProperty var cid: Int = _ 
    
    //是否我发送的消息，如果为true，则会显示在右方
    @BeanProperty var mine: Boolean = _
    
    //消息的发送者id（比如群组中的某个消息发送者），可用于自动解决浏览器多窗口时的一些问题
    @BeanProperty var fromid: Integer = _
    
    //服务端动态时间戳
    @BeanProperty var timestamp: Long = _
    
    //消息的状态
    @BeanProperty var status: Int = _
           
    override def toString = "id = " + id + ", username = " + username + 
      ", avatar = " + avatar +",Type = " + Type + ", content = " + content + ", cid = " + cid + ", mine = " + mine +
      ", fromid = " + fromid + ", timestamp = " + timestamp + ", status =" + status
    
}