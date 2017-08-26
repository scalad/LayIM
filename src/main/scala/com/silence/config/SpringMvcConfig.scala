package com.silence.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
import org.springframework.core.Ordered
import org.springframework.web.servlet.config.annotation.InterceptorRegistry

/**
 * @description SpringMVC配置
 * @date 2017-04-07
 * @author silence
 */
@Configuration
class SpringMvcConfig extends WebMvcConfigurerAdapter {
  
    /**
     * @description 重写addViewControllers方法配置默认主页
     * @param registry
     */
    override def addViewControllers(registry: ViewControllerRegistry): Unit = {
        registry.addViewController("/").setViewName("forward:/index.html")
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE)
        super.addViewControllers(registry)
    } 
    
    /**
     * @description 注册拦截器
     * @param registry
     */
    override def addInterceptors(registry: InterceptorRegistry) = {
        // addPathPatterns 用于添加拦截规则,excludePathPatterns 用户排除拦截
        registry.addInterceptor(new SystemHandlerInterceptorConfig())
            .addPathPatterns("/**").excludePathPatterns("/").excludePathPatterns("/*.html")
            .excludePathPatterns("/user/login").excludePathPatterns("/user/register")
            .excludePathPatterns("/user/existEmail")
        super.addInterceptors(registry);
    }
}