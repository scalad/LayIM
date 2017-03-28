package com.silence.config

import org.springframework.context.annotation.Configuration
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.annotation.PropertyAccessor
import com.fasterxml.jackson.annotation.JsonAutoDetect
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Configuration
@ComponentScan
class RedisConfig {
  
    private final val LOGGER: Logger = LoggerFactory.getLogger(classOf[RedisConfig])
    
    @Value("${spring.redis.host}")
    private var host: String = _
    
    @Value("${spring.redis.port}")
    private var port: Int = _
    
    @Value("${spring.redis.timeout}")
    private var timeout: Int = _
    
    @Bean
    def redisConnectionFactory(): JedisConnectionFactory = {
        var factory: JedisConnectionFactory = new JedisConnectionFactory
        factory.setHostName(host)
        factory.setPort(port)
        factory.setTimeout(timeout)
        LOGGER.info("Init the Redis instance Finished")
        return factory;
    }
    
    @Bean
    def redisTemplate(factory: RedisConnectionFactory): RedisTemplate[String, String] = {
        var template = new StringRedisTemplate(factory)
        setSerializer(template)
        template.afterPropertiesSet()
        return template
    }
    
    def setSerializer(template: StringRedisTemplate): Unit = {
        var jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(classOf[Object])
        var om = new ObjectMapper
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY)
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL)
        jackson2JsonRedisSerializer.setObjectMapper(om)
        template.setValueSerializer(jackson2JsonRedisSerializer)
    }
    
}