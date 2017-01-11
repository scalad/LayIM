###SpringBoot-WebSocket

###required
* Scala 2.1*
* JDK 1.8*
* Gradle 3.*
* Mysql 5.1*

###configuration
`application.properties`
    
	#spring.datasource.url: jdbc:hsqldb:mem:scratchdb
	#logging.file: /tmp/logs/app.log
	#datasource
	spring.datasource.url = jdbc:mysql://localhost:3306/websocket
	spring.datasource.username = root
	spring.datasource.password = root
	spring.datasource.driverClassName = com.mysql.jdbc.Driver
	# Specify the DBMS
	spring.jpa.database = MYSQL
	# Show or not log for each sql query
	spring.jpa.show-sql = true
	# Hibernate ddl auto (create, create-drop, update)
	spring.jpa.hibernate.ddl-auto = update
	# stripped before adding them to the entity manager)
	spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQL5Dialect
	# Server port
	server.port=8080

`/doc/user.sql`

###build

* git clone https://github.com/silence940109/WebSocket.git
* gradle bootRun