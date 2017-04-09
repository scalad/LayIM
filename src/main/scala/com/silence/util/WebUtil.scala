package com.silence.util

import javax.servlet.http.HttpServletRequest
import org.springframework.context.ApplicationContext

/**
 * @description web工具
 * @date 2017-04-08
 * @author silence
 */
object WebUtil {
  
    //Spring Boot 应用上下文
    var applicationContext:ApplicationContext = _
    
	  def setApplicationContext(context: ApplicationContext) = {
	      applicationContext = context
	  }
	  
    /**
     * @description 获取SpringBoot Bean实例
     * @param clazz
     * @return T
     */
	  def getBean[T](clazz: Class[T]): T = {
	      return applicationContext.getBean(clazz)
	  }
  
    /**
     * @description：获取服务器IP
     * @param request
     * @return
     */
    def getServerIpAdder(request: HttpServletRequest): String = {
        var addr: String = request.getScheme + "://" + request.getServerName
        if(request.getServerPort == 80) {
            return addr
        } else {
            return addr + ":" + request.getServerPort
        }
    }
  
    /**
     * @description：获取客户端真实IP
     * @param request
     * @return
     */
    def getClientIpAddr(request: HttpServletRequest): String = {
      if (request == null) {
            return ""
        }
        var ip: String = request.getHeader("x-forwarded-for")

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP")
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP")
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr()
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("http_client_ip")
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR")
        }
        // 如果是多级代理，那么取第一个ip为客户ip
        if (ip != null && ip.indexOf(",") != -1) {
            ip = ip.substring(ip.lastIndexOf(",") + 1, ip.length()).trim()
        }
        ip
    }  
 
}