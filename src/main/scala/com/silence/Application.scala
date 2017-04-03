package com.silence

import org.springframework.context.annotation.Configuration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import springfox.documentation.swagger2.annotations.EnableSwagger2
import org.springframework.boot.web.support.SpringBootServletInitializer
import org.springframework.boot.builder.SpringApplicationBuilder

@Configuration
@EnableAutoConfiguration
@ComponentScan
@SpringBootApplication
//启动swagger注解
@EnableSwagger2
class Config extends App

object Application extends SpringBootServletInitializer {

    def main(args: Array[String]): Unit = {
      
    		SpringApplication.run(classOf[Config])

    }
        
    override protected def configure(builder: SpringApplicationBuilder): SpringApplicationBuilder = {
  		  builder.sources(Application);
  	}
}
