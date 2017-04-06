package com.silence.domain

import com.silence.enties.User
import java.util.List
import scala.beans.BeanProperty

class FriendAndGroupInfo {
  
    //我的信息
    @BeanProperty
    var mine: User = _
    
    //好友列表
    @BeanProperty
    var friend: List[FriendList] = _
    
    //群组分组
    @BeanProperty
    var group: List[GroupList] = _
    
}