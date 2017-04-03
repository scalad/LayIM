package com.silence.repository

import com.silence.enties.User
import java.lang.Long
import org.apache.ibatis.annotations.Select

/**
 * @description User Dao
 * @date 2017年1月11日
 * @author wang
 *
 */
trait UserMapper {
  
    @Select(value = Array("select * from t_user where username = #{username}"))
    def findUser(username: String): User
    
}