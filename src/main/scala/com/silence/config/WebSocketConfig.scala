package com.silence.config

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Bean
import org.springframework.web.socket.server.standard.ServerEndpointExporter

/*
 * @description websocket服务器配置,使用springboot的话,那么容器是由springboot自动管理的
 * @date 2017-04-07
 * @author silence
 */
@Configuration
class WebSocketConfig {
    
    /**
     * @description 自动注册使用了@ServerEndpoint注解声明的Websocket endpoint。
 		 * 要注意，如果使用独立的servlet容器，而不是直接使用springboot的内置容器，就不要注入
 		 * ServerEndpointExporter，因为它将由容器自己提供和管理
     */
    @Bean
    def serverEndpointExporter() = new ServerEndpointExporter()
}