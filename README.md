### SpringBoot-WebSocket

### required
* Scala 2.1+
* JDK 1.8+
* Gradle 3.+
* Mysql 5.1+

![功能设计](https://github.com/scalad/WebSocket/blob/master/doc/image/function.png)

### technology

* Gradle
* Scala
* SpringBoot
* SpringSecurity
* Mybatis
* Redis
* Alibaba Druid
* Shiro

### application.properties configuration
    
	# Application
	spring.application.name=LayIM
	
	# Mybayis
	mybatis.type-aliases-package=com.silence.entity
	mybatis.mapper-locations=classpath*:/mapper/*Mapper.xml
	mybatis.configuration.map-underscore-to-camel-case=true
	mybatis.configuration.use-generated-keys=true
	mybatis.configuration.default-fetch-size=100
	mybatis.configuration.default-statement-timeout=30
	
	# Datasource
	spring.datasource.type=com.alibaba.druid.pool.DruidDataSource
	spring.datasource.url = jdbc:mysql://localhost:3306/websocket
	spring.datasource.username = root
	spring.datasource.password = root
	spring.datasource.driver-class-name= com.mysql.jdbc.Driver
	
	# Spring MVC
	spring.mvc.view.prefix=/WEB-INF/view/
	spring.mvc.view.suffix=.jsp
	
	# Server
	server.port=80
	server.session.timeout=1800
	
	# Redis Configration
	# Redis database index, default is 0
	spring.redis.database=0 
	spring.redis.host=120.27.114.229
	spring.redis.password=redis
	spring.redis.port=6379
	spring.redis.pool.max-idle=8
	spring.redis.pool.min-idle=0
	spring.redis.pool.max-active=8
	spring.redis.pool.max-wait=-1
	spring.redis.timeout=100
	
### build

* git clone https://github.com/silence940109/WebSocket.git
* gradle bootRun
* http://localhost:8080/swagger-ui.html