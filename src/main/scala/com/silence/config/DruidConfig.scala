package com.silence.config

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Bean
import org.springframework.boot.web.servlet.ServletRegistrationBean
import com.alibaba.druid.support.http.StatViewServlet
import scala.collection.immutable.Map
import scala.collection.JavaConversions
import scala.collection.immutable.List
import org.springframework.boot.web.servlet.FilterRegistrationBean
import com.alibaba.druid.support.http.WebStatFilter

/**
 * @description Alibaba Druid数据源配置
 * @date 2017-04003
 * @author silence
 */
@Configuration
class DruidConfig {
  
    /**
     * @description druid配置访问路径和用户名密码
     * @return ServletRegistrationBean
     */
    @Bean
  	def statViewServlet(): ServletRegistrationBean = {
  		  var druid = new ServletRegistrationBean()
  		  druid.setServlet(new StatViewServlet())  		
  		  druid.setUrlMappings(JavaConversions.asJavaCollection(List("/druid/*")))
  		  var params = Map("loginUsername" -> "admin", "loginPassword" -> "admin", "allo" -> "", "resetEnable" -> "false")
  		  druid.setInitParameters(JavaConversions.mapAsJavaMap(params))
  		  druid
  	}
  
    /**
     * @description 拦截器配置 
     * @return
     */
    @Bean
  	def webStatFilter():FilterRegistrationBean = {
  		  var fitler = new FilterRegistrationBean()
  		  fitler.setFilter(new WebStatFilter())
  		  fitler.setUrlPatterns(JavaConversions.asJavaCollection(List("/*")))
  		  fitler.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*")
  		  fitler
  	}
    
}