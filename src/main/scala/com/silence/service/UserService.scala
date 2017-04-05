package com.silence.service

import com.silence.enties.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import com.silence.repository.UserMapper
import org.springframework.transaction.annotation.Transactional
import org.springframework.cache.annotation.Cacheable

@Service
class UserService @Autowired()(private var userMapper: UserMapper) {

    def findUserByUsername(username: String): User = {
        userMapper.findUser(username)
    }
  
    @Transactional
    def saveUser(user: User): Int = {
        userMapper.saveUser(user)
    }
    
    @Cacheable(value = Array("reportcache"), keyGenerator = "wiselyKeyGenerator")
    def findAll(): java.util.List[User] = {
        userMapper.findAll()
    }
}