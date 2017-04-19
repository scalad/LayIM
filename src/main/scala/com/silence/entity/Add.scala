package com.silence.entity

import scala.beans.BeanProperty

/**
 * @description 添加好友、群组信息
 * @date 2017-04-16
 * @author silence
 */
class Add {
    
    //好友列表id或群组id
    @BeanProperty var groupId: Integer = _
    //附言
    @BeanProperty var remark: String = _
    //类型，好友或群组
    @BeanProperty var Type: Int = _
    
    override def toString = "groupId=" + groupId + ", remark=" + remark + ", Type=" + Type
}