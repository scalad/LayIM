package com.silence.domain

import java.lang.String
import scala.beans.BeanProperty

/**
 * @description 好友分组列表
 * @param id 分组id
 * @param groupname 分组名称
 * @param avatar 群组头像地址
 */
class GroupList(id: Integer, groupname: String) extends Group(id, groupname) {
  
    @BeanProperty
    var avatar: String = _
    
    def this(id: Integer, groupname: String, avatar: String) = {
        this(id, groupname)
        this.avatar = avatar
    }
    
    def this() = this(null, null)
  
}