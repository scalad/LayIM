package com.silence.service

import com.silence.enties.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import com.silence.repository.UserMapper

@Service
class UserService @Autowired()(private var userMapper: UserMapper) {

    def findUserByUsername(username: String): User = {
        userMapper.findUser(username)
    }
  
}