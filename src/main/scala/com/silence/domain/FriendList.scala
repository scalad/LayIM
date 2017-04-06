package com.silence.domain

import java.util.List
import com.silence.enties.User
import java.lang.String
import scala.beans.BeanProperty

/**
 * @description 好友列表
 * @param id 好友列表分组
 * @param groupname 列表名称
 * @param list 列表中的好友信息
 */
class FriendList(id: Integer, groupname: String) extends Group(id, groupname) {
    
    @BeanProperty
    var list: List[User] = _
    
    def this(id: Integer, groupname: String, list: List[User] ) = {
        this(id, groupname)
        this.list = list
    }
    
    def this(list: List[User]) = {
        this(null, null, list)
    }
    
    def this() = this(null)
    
}