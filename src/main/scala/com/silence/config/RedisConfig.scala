package com.silence.config

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.PropertyAccessor
import com.fasterxml.jackson.databind.ObjectMapper
import redis.clients.jedis.JedisPoolConfig

/**
 * @description redis连接配置
 * @date 2017-03-29
 * @author silence
 */
@Configuration
@ComponentScan
class RedisConfig {
  
    private final val LOGGER: Logger = LoggerFactory.getLogger(classOf[RedisConfig])
    //主机地址    
    @Value("${spring.redis.host}")
    private var host: String = _
    //端口
    @Value("${spring.redis.port}")
    private var port: Int = _
    //允许超时
    @Value("${spring.redis.timeout}")
    private var timeout: Int = _
    //认证
    @Value("${spring.redis.password}")
    private var password: String = _
    //数据库索引数量
    @Value("${spring.redis.database}")
    private var database: Int = _
    
    @Value("${spring.redis.pool.max-idle}")
    private var maxIdle: Int = _
    
    @Value("${spring.redis.pool.min-idle}")
    private var minIdle: Int = _
    
    @Value("${spring.redis.pool.max-active}")
    private var maxActive: Int = _
    
    @Value("${spring.redis.pool.max-wait}")
    private var maxWait: Int = _
    
    /**
     * @description Jedis数据源配置
     * @return JedisPoolConfig
     */
    @Bean
    def jedisPoolConfig(): JedisPoolConfig = {
        var jedisPoolConfig = new JedisPoolConfig
        jedisPoolConfig.setMaxIdle(maxIdle)
        jedisPoolConfig.setMinIdle(minIdle)
        jedisPoolConfig.setMaxWaitMillis(maxWait)
        LOGGER.info("Init the RedisPoolConfig Finished")
        jedisPoolConfig
    }
    
    /**
     * @description Jedis数据连接工场
     * @return JedisConnectionFactory
     */
    @Bean
    def redisConnectionFactory(poolConfig: JedisPoolConfig): JedisConnectionFactory = {
        var factory: JedisConnectionFactory = new JedisConnectionFactory
        factory.setHostName(host)
        factory.setPort(port)
        factory.setTimeout(timeout)
        factory.setPassword(password)
        factory.setDatabase(database)
        factory.setPoolConfig(poolConfig)
        LOGGER.info("Init the Redis instance Finished")
        factory
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