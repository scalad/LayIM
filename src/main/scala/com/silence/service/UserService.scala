package com.silence.service

import com.silence.enties.User
import org.springframework.beans.factory.annotation.Autowired
import com.silence.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService extends BaseService[User] {
  
  @Autowired val userRepository: UserRepository = null
  
}