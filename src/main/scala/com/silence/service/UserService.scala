package com.silence.service

import com.silence.enties.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import com.silence.repository.UserMapper
import org.springframework.transaction.annotation.Transactional
import org.springframework.cache.annotation.Cacheable
import org.springframework.cache.annotation.CacheEvict

import java.util.List

@Service
class UserService @Autowired()(private var userMapper: UserMapper) {

    def findUserByUsername(username: String): User = {
        userMapper.findUser(username)
    }
  
    //清除缓存
    @CacheEvict(value = Array("findUsers" ), allEntries = true)  
    def saveUser(user: User): Int = {
        userMapper.saveUser(user)
    }
    
    @Transactional
    @Cacheable(value = Array("findUsers"), keyGenerator = "wiselyKeyGenerator")
    def findUsers(): List[User] = {
        userMapper.findUsers
    }
    
}