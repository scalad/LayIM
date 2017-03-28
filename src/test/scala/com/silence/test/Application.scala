package com.silence.test

import org.junit.runner.RunWith
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.junit.Assert
import org.springframework.boot.test.context.SpringBootContextLoader
import org.springframework.test.context.web.WebAppConfiguration
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.StringRedisTemplate

@SpringBootContextLoader
@RunWith(value = classOf[SpringJUnit4ClassRunner])
@WebAppConfiguration
class RedisTest {
    
  
    @Test
    def test(): Unit = {
         
    }

}