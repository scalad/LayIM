package com.silence

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import springfox.documentation.swagger2.annotations.EnableSwagger2
import org.springframework.boot.web.support.SpringBootServletInitializer
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.mybatis.spring.annotation.MapperScan
import org.springframework.context.ApplicationContext
import scala.beans.BeanProperty

@SpringBootApplication
//启动swagger注解
@EnableSwagger2
@EntityScan(Array("com.silence.entity"))
@MapperScan(Array("com.silence.repository"))
class Config extends App

object Application extends SpringBootServletInitializer {

    @BeanProperty var applicationContext: ApplicationContext = null
    
    def main(args: Array[String]): Unit = {
      
    		applicationContext = SpringApplication.run(classOf[Config])
    		
    }
        
    override protected def configure(builder: SpringApplicationBuilder): SpringApplicationBuilder = {
  		  builder.sources(Application)
  	}
}
