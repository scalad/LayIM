package com.silence.entity

import scala.beans.BeanProperty

/**
 * @description 好友分组对象
 * @date 2017-04-30
 * @author silence
 */
class FriendGroup {
    
    //用户id
    @BeanProperty var uid: Integer = _

    //群组名称
    @BeanProperty var groupName: String = _
    
    def this(uid: Integer, groupName: String) = {
        this
        this.uid = uid
        this.groupName = groupName
    }
    
    override def toString = "uid = " + uid + ",groupName = " + groupName
    
}