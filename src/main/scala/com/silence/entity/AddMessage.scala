package com.silence.entity

import scala.beans.BeanProperty
import java.util.Date

/**
 * @description 添加好友、群组信息
 * @date 2017-04-16
 * @author silence
 */
class AddMessage {
  
    @BeanProperty var id: Integer = _
    //谁发起的请求
    @BeanProperty var fromUid: Integer = _
    //发送给谁的申请,可能是群，那么就是创建该群组的用户
    @BeanProperty var toUid: Integer = _
    //如果是添加好友则为from_id的分组id，如果为群组则为群组id
    @BeanProperty var groupId: Integer = _
    //附言
    @BeanProperty var remark: String = _
    //0未处理，1同意，2拒绝
    @BeanProperty var agree: Int = _
    //类型，可能是添加好友或群组
    @BeanProperty var Type: Int = _
    //申请时间
    @BeanProperty var time: Date = _
  
    override def toString = "id=" + id + ", fromUid=" + fromUid + ", toUid=" + toUid + ", groupId=" + groupId +", remark=" + remark + ", agree=" + agree + ", Type=" + Type + ", time=" + time
    
}