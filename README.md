### SpringBoot-WebSocket

一个基于[WebSocket](http://websocket.org/index.html)和[LayIM](https://www.layui.com/)的即时通讯系统，后台使用了[Gradle](https://gradle.org/)集成了[Spring Boot](http://projects.spring.io/spring-boot/)、[Scala](http://www.scala-lang.org/)、[Mybatis](http://blog.mybatis.org/)以及[Redis](https://redis.io/)等

### 需要
* Scala 2.1+
* JDK 1.8+
* Gradle 3.+
* Mysql 5.0+

![功能设计](https://github.com/scalad/WebSocket/blob/master/doc/image/function.png)

### 技术

* [Gradle](https://gradle.org/install)
* Scala
* SpringBoot
* SpringSecurity
* Mybatis And PageHelper
* Redis
* Alibaba Druid
* Java Mail

### application.properties配置文件
    
	# Application
	spring.application.name=LayIM
	
	# Mybayis
	mybatis.mapper-locations=classpath*:/mapper/*Mapper.xml
	mybatis.configuration.map-underscore-to-camel-case=true
	mybatis.configuration.use-generated-keys=true
	mybatis.configuration.default-fetch-size=100
	mybatis.configuration.default-statement-timeout=30
	spring.jpa.show-sql=true
	
	# Datasource
	spring.datasource.type=com.alibaba.druid.pool.DruidDataSource
	spring.datasource.url = jdbc:mysql://localhost:3306/websocket
	spring.datasource.username = root
	spring.datasource.password = root
	spring.datasource.driver-class-name= com.mysql.jdbc.Driver
	
	# Spring MVC
	spring.mvc.view.prefix=/WEB-INF/view/
	spring.mvc.view.suffix=.jsp
	spring.http.multipart.max-file-size=50MB
	spring.http.multipart.max-request-size=50MB
	
	# Server
	server.port=80
	server.session.timeout=1800
	
	# Email
	spring.mail.host=smtp.qq.com
	spring.mail.username=764341083@qq.com
	spring.mail.password=ugmgoaidcsaobbaf
	spring.mail.port=587
	spring.mail.properties.mail.smtp.auth=true  
	spring.mail.properties.mail.smtp.starttls.enable=true  
	spring.mail.properties.mail.smtp.starttls.required=true  
	
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
	spring.redis.timeout=500
	
	# Logging
	logging.level.*=debug
	logging.level.com.silence.repository=debug
	logging.file=./log/spring.log
	
### 构建

* git clone https://github.com/scalad/WebSocket.git
* gradle bootRun
* swagger ui reference => http://localhost:8080/swagger-ui.html
* druid manage reference => http://localhost/druid/index.html
	* username:admin password:admin
* inded page reference => http://localhost/