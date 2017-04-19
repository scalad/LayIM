package com.silence.entity

import scala.beans.BeanProperty

class AddFriends {
 
    //自己的id
    @BeanProperty var mid: Int = _
    //分组id
    @BeanProperty var mgid: Int = _
    //对方用户id
    @BeanProperty var tid: Int = _
    //对方分组id   
    @BeanProperty var tgid: Int = _
    
    def this(mid: Int, mgid: Int, tid: Int, tgid: Int) {
        this
        this.mid = mid
        this.mgid = mgid
        this.tid = tid
        this.tgid = tgid
    }
  
}