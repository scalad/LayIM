package com.silence.service

import org.springframework.stereotype.Service
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.beans.factory.annotation.Autowired
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.core.io.FileSystemResource
import java.io.File
import javax.mail.MessagingException

/**
 * @description 邮件发送相关服务 
 * @date 2017-04-04
 * @author silence
 */
@Service
class MailService {
  
    private final val LOGGER: Logger = LoggerFactory.getLogger(classOf[MailService])
    
    @Autowired private var sender: JavaMailSender = _
    
    @Value("${spring.mail.username}") private var username: String = _     
    
    /** 
     * @description 发送纯文本的简单邮件 
     * @param to 邮件接收者
     * @param subject 主题
     * @param content 内容
     */ 
    def sendSimpleMail(to: String, subject: String, content: String) = {
        val message = new SimpleMailMessage
        message.setFrom(username)
        message.setTo(to)
        message.setSubject(subject)
        message.setText(content)
        try {
            sender.send(message)
            LOGGER.info("发送给  " + to + "邮件发送成功")
        } catch {
            case ex: Exception => {
                LOGGER.info("发送给 " + to + "邮件发送失败！" + ex.getMessage)
            }
        }
    }
    
    /** 
     * @description 发送html格式的邮件 
     * @param to 邮件接收者
     * @param subject 主题
     * @param content 内容
     */ 
    def sendHtmlMail(to: String, subject: String,content: String) = {
        val message = sender.createMimeMessage()
        val helper = new MimeMessageHelper(message, true)
        helper.setFrom(username)
        helper.setTo(to)
        helper.setSubject(subject)
        helper.setText(content, true)
        try {
            sender.send(message)
            LOGGER.info("发送给  " + to + "html格式的邮件发送成功")
        } catch {
            case ex: MessagingException => {
                LOGGER.info("发送给  " + to + "html格式的邮件发送失败！" + ex.getMessage)
            }
        }       
    }
    
    /** 
     * @description 发送带附件的邮件 
     * @param to 邮件接收者
     * @param subject 主题
     * @param content 内容
     * @param filePath 附件路径
     */
    def sendAttachmentsMail(to: String,subject: String, content: String, filePath: String) = {
        val message = sender.createMimeMessage()
        val helper = new MimeMessageHelper(message, true)
        helper.setFrom(username)
        helper.setTo(to)
        helper.setSubject(subject)
        helper.setText(content, true)
        val file = new FileSystemResource(new File(filePath))
        val fileName = filePath.substring(filePath.lastIndexOf(File.separator))
        helper.addAttachment(fileName, file)
        try {
            sender.send(message)
            LOGGER.info("发送给  " + to + "带附件邮件发送成功")
        } catch {
            case ex: MessagingException => {
                LOGGER.info("发送给   " + to + "带附件邮件发送失败！" + ex.getMessage)
            }
        }              
    }
    
    /** 
     * @description 发送嵌入静态资源（一般是图片）的邮件 
     * @param to 邮件接收者
     * @param subject 主题
     * @param content 邮件内容，需要包括一个静态资源的id，比如：<img src=\"cid:rscId01\" > 
     * @param rscPath 静态资源路径和文件名 
     * @param rscId 静态资源id 
     */  
    def sendInlineResourceMail(to: String, subject: String, content: String, rscPath: String, rscId: String) = {
        val message = sender.createMimeMessage()
        val helper = new MimeMessageHelper(message, true)
        helper.setFrom(username)
        helper.setTo(to)
        helper.setSubject(subject)
        helper.setText(content, true)
        val res = new FileSystemResource(new File(rscPath))
        helper.addInline(rscId, res)
        try {
            sender.send(message)
            LOGGER.info("发送给  " + to + "嵌入静态资源的邮件发送成功")
        } catch {
            case ex: MessagingException => {
                LOGGER.info("发送给  " + to + "嵌入静态资源的邮件发送失败！" + ex.getMessage)
            }
        }       
    }
}