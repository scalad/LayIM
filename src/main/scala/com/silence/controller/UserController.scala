package com.silence.controller

import io.swagger.annotations.Api
import org.springframework.beans.factory.annotation.Autowired
import com.silence.service.UserService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestBody
import com.silence.enties.User
import com.silence.util.DateUtil

import java.util.List
import com.silence.domain.FriendAndGroupInfo
import com.silence.domain.ResultSet
import com.google.gson.Gson
import io.swagger.annotations.ApiOperation
import com.silence.domain.FriendList
import org.springframework.web.multipart.MultipartFile
import javax.servlet.http.HttpServletRequest
import org.apache.commons.io.FileUtils
import java.io.File
import java.util.HashMap
import com.silence.common.SystemConstant
import com.silence.util.FileUtil
import com.silence.util.FileUtil

@Controller
@Api(value = "用户相关操作")
@RequestMapping(value = Array("/user"))
class UserController @Autowired()(private val userService : UserService){
    
    private final val LOGGER:Logger = LoggerFactory.getLogger(classOf[UserController])
    
    private final val gson: Gson = new Gson
    
    @ResponseBody
    @RequestMapping(value = Array("/saveUser"), method = Array(RequestMethod.POST))
    def saveUser(@RequestBody user: User) = {
        user.setCreateDate(DateUtil.getDate())
        userService.saveUser(user)
    }
    
    @ResponseBody
    @ApiOperation("初始化聊天界面数据，分组列表好友信息、群列表")
    @RequestMapping(value = Array("/init/{userId}"), method = Array(RequestMethod.POST))
    def init(@PathVariable("userId") userId: Int): String = {
    		var data = new FriendAndGroupInfo
    		//用户信息
        data.mine = userService.findUserById(userId)
        //用户群组列表
        data.group = userService.findGroupsById(userId)
        //用户好友列表
        data.friend = userService.findFriendGroupsById(userId)
        gson.toJson(new ResultSet[FriendAndGroupInfo](data))
    }
    
    @ResponseBody
    @RequestMapping(value = Array("/getMembers"), method = Array(RequestMethod.GET))
    def getMembers(@RequestParam("id") id: Int): String = {
        val users = userService.findUserByGroupId(id)
        val friends = new FriendList(users)
        gson.toJson(new ResultSet[FriendList](friends))   
    }
    
    @ResponseBody
    @RequestMapping(value = Array("/upload/image"), method = Array(RequestMethod.POST))
    def uploadImage(@RequestParam("file") file:MultipartFile,request: HttpServletRequest): String = {
        if (file.isEmpty()) {
            return gson.toJson(new ResultSet(SystemConstant.ERROR, SystemConstant.UPLOAD_FAIL))
        }
        val path = request.getServletContext.getRealPath(SystemConstant.IMAGE_PATH)
        val src = FileUtil.upload(SystemConstant.IMAGE_PATH, path, file)
        var result = new HashMap[String, String]
        //图片的相对路径地址
        result.put("src", src)
        LOGGER.info("图片" + file.getOriginalFilename + "上传成功") 
        gson.toJson(new ResultSet[HashMap[String, String]](result))
    }

    @ResponseBody
    @RequestMapping(value = Array("/upload/file"), method = Array(RequestMethod.POST))
    def uploadFile(@RequestParam("file") file:MultipartFile,request: HttpServletRequest): String = {
        if (file.isEmpty()) {
            return gson.toJson(new ResultSet(SystemConstant.ERROR, SystemConstant.UPLOAD_FAIL))
        }
        val path = request.getServletContext.getRealPath(SystemConstant.FILE_PATH)
        val src = FileUtil.upload(SystemConstant.FILE_PATH, path, file)
        var result = new HashMap[String, String]
        //文件的相对路径地址
        result.put("src", src)
        result.put("name", file.getOriginalFilename)
        LOGGER.info("文件" + file.getOriginalFilename + "上传成功") 
        gson.toJson(new ResultSet[HashMap[String, String]](result))
    }
        
    @RequestMapping(value = Array("/index"), method = Array(RequestMethod.GET))
    def index(model: Model): String = {
        LOGGER.info("index action")
        "index"
    }
}