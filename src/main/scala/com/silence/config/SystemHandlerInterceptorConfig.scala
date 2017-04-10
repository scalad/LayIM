package com.silence.config

import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.springframework.web.servlet.ModelAndView
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * @description 系统拦截器配置
 * @date 2017-04-10
 * @author silence
 */
class SystemHandlerInterceptorConfig extends HandlerInterceptor {
    
    private final val LOGGER: Logger = LoggerFactory.getLogger(classOf[SystemHandlerInterceptorConfig])
  
    /**
     * @description 前置处理器，在请求处理之前调用
     * @param request
     * @param response
     * @param handler
     * @return Boolean
     */
    override def preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Object): Boolean = {
        LOGGER.info("前置处理器，在请求处理之前调用")
        if(request.getSession.getAttribute("user") == null) {
            response.sendRedirect("/")
            return false
        } else {
            return true
        }
    }
    
    /**
     * @description 请求处理之后进行调用，但是在视图被渲染之前(Controller方法调用之后)
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     */
    override def postHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Object,
            modelAndView: ModelAndView): Unit = {
        
    }

    /**
     * @description 后置处理器，渲染视图完成
     * @param request
     * @param response
     * @param handler
     * @param ex
     */
    override def afterCompletion(request: HttpServletRequest, response: HttpServletResponse, handler: Object,
            ex: Exception) = {
        
    }
}