package com.silence.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
import org.springframework.core.Ordered

/**
 * @description SpringMVC配置
 * @date 2017-04-07
 * @author silence
 */
@Configuration
class SpringMvcConfig extends WebMvcConfigurerAdapter {
  
    /**
     * @description 重写addViewControllers方法配置默认主页
     */
    override def addViewControllers(registry: ViewControllerRegistry): Unit = {
        registry.addViewController( "/" ).setViewName( "forward:/index.html" )
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE )
        super.addViewControllers(registry )
    } 
    
}