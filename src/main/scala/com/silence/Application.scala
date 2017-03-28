package com.silence

import org.springframework.context.annotation.Configuration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableAutoConfiguration
@ComponentScan
@SpringBootApplication
//启动swagger注解
@EnableSwagger2
class Config

object Application extends App {

    SpringApplication.run(classOf[Config])
    
}
