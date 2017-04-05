package com.silence.test

import org.junit.runner.RunWith
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.junit.Test
import com.silence.enties.User
import java.util.Date
import com.silence.util.SecurityUtil
import org.springframework.boot.test.context.SpringBootTest
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.SimpleMailMessage
import org.springframework.beans.factory.annotation.Value
import com.silence.service.MailService

@SpringBootTest
@RunWith(value = classOf[SpringJUnit4ClassRunner])
class ApplicationBootTest {
  
    private final val LOGGER: Logger = LoggerFactory.getLogger(classOf[ApplicationBootTest])
      
    @Autowired private var mailService: MailService = _
    
    @Value("${spring.mail.username}")
    private var username: String = _
    
    @Test
    def test(): Unit = {
        LOGGER.info("send mail..")
        mailService.sendSimpleMail("695412269@qq.com", "这是测试邮件", "这是测试邮件")
        LOGGER.info("send mail finish")
    }

}