package com.silence.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.beans.factory.annotation.Autowired
import com.silence.repository.UserRepository
import org.springframework.web.servlet.ModelAndView
import com.silence.enties.User
import java.util.List
import org.springframework.web.bind.annotation.PathVariable
import javax.validation.Valid
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.data.domain.Page
import com.silence.service.UserService
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParams
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiResponses
import io.swagger.annotations.ApiResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@ComponentScan
@Controller
@ResponseBody
@Api(value = "用户相关操作")
class UserController @Autowired()(private val userService : UserService){
    
    private final val LOGGER:Logger = LoggerFactory.getLogger(classOf[UserController])
    
    @ApiOperation("获取用户信息")
    @ApiImplicitParams(
        Array(new ApiImplicitParam(paramType="header",name="username",dataType="String",required=true,value="用户的姓名",defaultValue="zhaojigang")
    ))
  	@RequestMapping(value = Array("/list"), method = Array(RequestMethod.GET))
    def list() : List[User] = {
        userService.findAll
    }
    
    @RequestMapping(value = Array("save"), method = Array(RequestMethod.POST))
    def save(@Valid user : User) : User = {
        userService.save(user)
    }
    
    @ApiOperation("查询用户信息")
    @ApiImplicitParams(Array(new ApiImplicitParam(paramType="header",name="id",dataType="Integer",required=true,value="用户的编号",defaultValue="1")))
    @ApiResponses(Array(new ApiResponse(code=400,message="请求参数没填好"),new ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")))
  	@RequestMapping(value = Array("/find/{id}"), method = Array(RequestMethod.GET))
    def find(@PathVariable(value = "id") id: Long) : User = {
        userService.find(id)
    }
    
    @RequestMapping(value = Array("delete/{id}"), method = Array(RequestMethod.POST))
    def delete(@PathVariable(value = "id") id: Long) : Unit = {
        userService.delete(id)
    }
    
    @RequestMapping(value = Array("update"), method = Array(RequestMethod.POST))
    def update(@Valid user : User, bindingResult : BindingResult) : User = {
        userService.update(user)
    }
    
    @RequestMapping(value = Array("page"), method = Array(RequestMethod.GET))
    def page(@RequestParam("page") page : Int, @RequestParam("pageSize") pageSize : Int) : Page[User] = {
        userService.page(page, pageSize)
    }
    
}