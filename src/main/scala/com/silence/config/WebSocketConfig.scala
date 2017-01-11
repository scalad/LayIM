package com.silence.config

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Bean
import org.springframework.web.socket.server.standard.ServerEndpointExporter

@Configuration
class WebSocketConfig {
  
    @Bean
    def serverEndpointExporter: ServerEndpointExporter =  {
        new ServerEndpointExporter()
    }
    
}