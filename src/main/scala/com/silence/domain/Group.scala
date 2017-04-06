package com.silence.domain

import java.lang.String
import scala.beans.BeanProperty

/**
 * @description 组信息
 * @param id 群组ID
 * @param groupname 群组名
 * 
 */
class Group(@BeanProperty var id: Integer, @BeanProperty var groupname: String)