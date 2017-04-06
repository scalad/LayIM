package com.silence.config

import org.springframework.context.annotation.Configuration
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import com.github.pagehelper.PageHelper
import java.util.Properties

/**
 * @description Mybatis的分页插件
 * @date 2017-04-05
 * @author silence
 */
@Configuration
class MybatisConfig {
  
    private final val LOGGER: Logger = LoggerFactory.getLogger(classOf[MybatisConfig])
    
    @Bean
    def pageHelper(): PageHelper = {
        LOGGER.info("注册MyBatis分页插件PageHelper");
        val pageHelper = new PageHelper()
        val properties = new Properties()
        properties.setProperty("offsetAsPageNum", "true")
        properties.setProperty("rowBoundsWithCount", "true")
        properties.setProperty("reasonable", "true")
        pageHelper.setProperties(properties)
        pageHelper
    }
    
}