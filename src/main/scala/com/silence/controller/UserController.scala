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
import org.springframework.ui.Model
import com.silence.entity.Receive
import scala.collection.JavaConversions
import com.github.pagehelper.PageHelper
import java.util.ArrayList
import com.silence.entity.ChatHistory
import com.silence.entity.ChatHistory
import com.silence.entity.ChatHistory
import org.springframework.core.annotation.AnnotationUtils.DefaultValueHolder
import com.silence.domain.ResultPageSet

@Controller
@Api(value = "用户相关操作")
@RequestMapping(value = Array("/user"))
class UserController @Autowired()(private val userService : UserService){
    
    private final val LOGGER:Logger = LoggerFactory.getLogger(classOf[UserController])
    
    private final val gson: Gson = new Gson
    
    /**
     * @description 移动好友分组
     * @param groupId 新的分组id
     * @param userId 被移动的好友id
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = Array("/changeGroup"), method = Array(RequestMethod.POST))
    def changeGroup(@RequestParam("groupId") groupId: Integer, @RequestParam("userId") userId: Integer
        ,request: HttpServletRequest): String = {
        val user = request.getSession.getAttribute("user").asInstanceOf[User]
        val result = userService.changeGroup(groupId, userId, user.getId)
        if (result)
            return gson.toJson(new ResultSet(result))
        else
            gson.toJson(new ResultSet(SystemConstant.ERROR, SystemConstant.ERROR_MESSAGE))
    }
    
    /**
     * @description 拒绝添加好友
     * @param request
     * @param messageBoxId 消息盒子的消息id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = Array("/refuseFriend"), method = Array(RequestMethod.POST))
    def refuseFriend(@RequestParam("messageBoxId") messageBoxId: Integer,request: HttpServletRequest): String = {
        val result = userService.refuseFriend(messageBoxId)
        gson.toJson(new ResultSet(result))
    }
    
    /**
     * @description 添加好友
     * @param uid 对方用户ID
     * @param fromGroup 对方设定的好友分组
     * @param group 我设定的好友分组
     * @param messageBoxId 消息盒子的消息id
     * @return String
     */
    @ResponseBody
    @RequestMapping(value = Array("/agreeFriend"), method = Array(RequestMethod.POST))
    def agreeFriend(@RequestParam("uid") uid: Integer,@RequestParam("from_group") fromGroup: Integer,
        @RequestParam("group") group: Integer, @RequestParam("messageBoxId") messageBoxId: Integer,
        request: HttpServletRequest): String = {
        val user = request.getSession.getAttribute("user").asInstanceOf[User]
        val result = userService.addFriend(user.getId, group, uid, fromGroup, messageBoxId)
        gson.toJson(new ResultSet(result))
    }
    
    /**
     * @description 查询消息盒子信息
     * @param uid
     * @param page
     */
    @ResponseBody
    @RequestMapping(value = Array("/findAddInfo"), method = Array(RequestMethod.GET))
    def findAddInfo(@RequestParam("uid") uid: Integer, @RequestParam("page") page: Int): String = {
        PageHelper.startPage(page, SystemConstant.ADD_MESSAGE_PAGE)
        val list = userService.findAddInfo(uid)
        val count = userService.countUnHandMessage(uid, null).toInt
        val pages = if (count < SystemConstant.ADD_MESSAGE_PAGE) 1 else count / SystemConstant.ADD_MESSAGE_PAGE + 1
        gson.toJson(new ResultPageSet(list, pages)).replaceAll("Type", "type")
    }
    
    /**
     * @description 查找好友、群
     * @param page
     * @param key
     * @param Type
     */
    @ResponseBody
    @RequestMapping(value = Array("/findUsers"), method = Array(RequestMethod.GET))
    def findUsers(@RequestParam(value = "page",defaultValue = "1") page: Int,
                  @RequestParam(value = "key", required = false) key: String,
                  @RequestParam("Type") Type: String): String = {
    		val count = userService.countUsers(key, Type)
				val pages = if (count < SystemConstant.USER_PAGE) 1 else (count / SystemConstant.USER_PAGE + 1)
    		PageHelper.startPage(page, SystemConstant.USER_PAGE)
    		val users = userService.findUsers(key, Type)
    		var result = new ResultPageSet(users)
    		result.setPages(pages)
        gson.toJson(result)
    }
    
    /**
     * @description 获取聊天记录
     * @param id 与谁的聊天记录id
     * @param Type 类型，可能是friend或者是group
     */
    @ResponseBody
    @RequestMapping(value = Array("/chatLog"), method = Array(RequestMethod.POST))
    def chatLog(@RequestParam("id") id: Integer, @RequestParam("Type") Type: String, 
        @RequestParam("page") page: Int, request: HttpServletRequest, model: Model): String = {
        val user = request.getSession.getAttribute("user").asInstanceOf[User]
        PageHelper.startPage(page, SystemConstant.SYSTEM_PAGE)
        //查找聊天记录
        val historys:List[ChatHistory] = userService.findHistoryMessage(user, id, Type)
        gson.toJson(new ResultSet(historys))
    }
    
    /**
     * @description 弹出聊天记录页面
     * @param id 与谁的聊天记录id
     * @param Type 类型，可能是friend或者是group
     */
    @RequestMapping(value = Array("/chatLogIndex"), method = Array(RequestMethod.GET))
    def chatLogIndex(@RequestParam("id") id: Integer, @RequestParam("Type") Type: String, 
        model: Model, request: HttpServletRequest): String = {
        model.addAttribute("id", id)
        model.addAttribute("Type", Type)
        val user = request.getSession.getAttribute("user").asInstanceOf[User]
        var pages: Int = userService.countHistoryMessage(user.getId, id, Type)
        pages = if (pages < SystemConstant.SYSTEM_PAGE) pages else (pages / SystemConstant.SYSTEM_PAGE + 1)
        model.addAttribute("pages", pages)
        "chatLog"
    }
    
    /**
     * @description 获取离线消息
     */
    @ResponseBody
    @RequestMapping(value = Array("/getOffLineMessage"), method = Array(RequestMethod.POST))
    def getOffLineMessage(request: HttpServletRequest): String = {
        val user = request.getSession.getAttribute("user").asInstanceOf[User]
        		LOGGER.info("查询 uid = " + user.getId + " 的离线消息")
        val receives: List[Receive] = userService.findOffLineMessage(user.getId, 0)
        JavaConversions.collectionAsScalaIterable(receives).foreach { receive => {
            val user = userService.findUserById(receive.getId)
            receive.setUsername(user.getUsername)
            receive.setAvatar(user.getAvatar)
        } }
        gson.toJson(new ResultSet(receives)).replaceAll("Type", "type")
    }
        
    
    /**
     * @description 更新签名   
     * @param sign
     * 
     */
    @ResponseBody
    @RequestMapping(value = Array("/updateSign"), method = Array(RequestMethod.POST))
    def updateSign(request: HttpServletRequest, @RequestParam("sign") sign: String): String = {
        val user:User = request.getSession.getAttribute("user").asInstanceOf[User]
        user.setSign(sign)
        if(userService.updateSing(user)) {
            gson.toJson(new ResultSet)
        } else {
            gson.toJson(new ResultSet(SystemConstant.ERROR, SystemConstant.ERROR_MESSAGE))
        }
    }
    
    /**
     * @description 激活   
     * @param activeCode
     * 
     */
    @RequestMapping(value = Array("/active/{activeCode}"), method = Array(RequestMethod.GET))
    def activeUser(@PathVariable("activeCode") activeCode: String): String = {
        if(userService.activeUser(activeCode) == 1) {
            return "redirect:/#tologin?status=1"
        }
        "redirect:/#toregister?status=0"
    }
    
    /**
     * @description 注册   
     * @param user
     * 
     */
    @ResponseBody
    @RequestMapping(value = Array("/register"), method = Array(RequestMethod.POST))
    def register(@RequestBody user: User, request: HttpServletRequest): String = {
        if(userService.saveUser(user, request) == 1) {
            gson.toJson(new ResultSet[String](SystemConstant.SUCCESS, SystemConstant.REGISTER_SUCCESS))   
        } else {
          	gson.toJson(new ResultSet[String](SystemConstant.ERROR, SystemConstant.REGISTER_FAIL))   
        }
    }
    
    /**
     * @description 登陆
     * @param user
     * 
     */
    @ResponseBody
    @RequestMapping(value = Array("/login"), method = Array(RequestMethod.POST))
    def login(@RequestBody user: User, request: HttpServletRequest): String = {
        val u: User = userService.matchUser(user)
        //未激活
        if (u != null && "nonactivated".equals(u.getStatus)) {
            return gson.toJson(new ResultSet[User](SystemConstant.ERROR, SystemConstant.NONACTIVED))
        } else if(u != null && !"nonactivated".equals(u.getStatus)) {
            LOGGER.info(user + "成功登陆服务器")
            request.getSession.setAttribute("user", u);
            return gson.toJson(new ResultSet[User](u))
        } else {        
            var result = new ResultSet[User](SystemConstant.ERROR, SystemConstant.LOGGIN_FAIL)
            gson.toJson(result)
        }
    }

    /**
     * @description  初始化主界面数据
     * @param userId
     * 
     */
    @ResponseBody
    @ApiOperation("初始化聊天界面数据，分组列表好友信息、群列表")
    @RequestMapping(value = Array("/init/{userId}"), method = Array(RequestMethod.POST))
    def init(@PathVariable("userId") userId: Int): String = {
    		var data = new FriendAndGroupInfo
    		//用户信息
    		var user = userService.findUserById(userId)
    		user.setStatus("online")
        data.mine = user
        //用户群组列表
        data.group = userService.findGroupsById(userId)
        //用户好友列表
        data.friend = userService.findFriendGroupsById(userId)
        gson.toJson(new ResultSet[FriendAndGroupInfo](data))
    }
    
    /**
     * @description 获取群成员
     * @param id
     * 
     */
    @ResponseBody
    @RequestMapping(value = Array("/getMembers"), method = Array(RequestMethod.GET))
    def getMembers(@RequestParam("id") id: Int): String = {
        val users = userService.findUserByGroupId(id)
        val friends = new FriendList(users)
        gson.toJson(new ResultSet[FriendList](friends))   
    }
    
    /**
     * @description 客户端上传图片
     * @param file
     * @param request
     * @return 
     */
    @ResponseBody
    @RequestMapping(value = Array("/upload/image"), method = Array(RequestMethod.POST))
    def uploadImage(@RequestParam("file") file:MultipartFile,request: HttpServletRequest): String = {
        if (file.isEmpty()) {
            return gson.toJson(new ResultSet(SystemConstant.ERROR, SystemConstant.UPLOAD_FAIL))
        }
        val path = request.getServletContext.getRealPath("/")
        val src = FileUtil.upload(SystemConstant.IMAGE_PATH, path, file)
        var result = new HashMap[String, String]
        //图片的相对路径地址
        result.put("src", src)
        LOGGER.info("图片" + file.getOriginalFilename + "上传成功") 
        gson.toJson(new ResultSet[HashMap[String, String]](result))
    }

    /**
     * @description 客户端上传文件
     * @param file
     * @param request
     * @return 
     */
    @ResponseBody
    @RequestMapping(value = Array("/upload/file"), method = Array(RequestMethod.POST))
    def uploadFile(@RequestParam("file") file:MultipartFile,request: HttpServletRequest): String = {
        if (file.isEmpty()) {
            return gson.toJson(new ResultSet(SystemConstant.ERROR, SystemConstant.UPLOAD_FAIL))
        }
        val path = request.getServletContext.getRealPath("/")
        val src = FileUtil.upload(SystemConstant.FILE_PATH, path, file)
        var result = new HashMap[String, String]
        //文件的相对路径地址
        result.put("src", src)
        result.put("name", file.getOriginalFilename)
        LOGGER.info("文件" + file.getOriginalFilename + "上传成功") 
        gson.toJson(new ResultSet[HashMap[String, String]](result))
    }
    
    /**
     * @description用户更新头像
     * @param file
     */
    @ResponseBody
    @RequestMapping(value = Array("/updateAvatar"), method = Array(RequestMethod.POST))
    def updateAvatar(@RequestParam("avatar") avatar: MultipartFile, request: HttpServletRequest): String = {
        val user = request.getSession.getAttribute("user").asInstanceOf[User]
        val path = request.getServletContext.getRealPath(SystemConstant.AVATAR_PATH)
        val src = FileUtil.upload(path, avatar)
        userService.updateAvatar(user.getId, src)
        var result = new HashMap[String, String]
        result.put("src", src)
        gson.toJson(new ResultSet(result))
    }  
    
    /**
     * @description 跳转主页
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = Array("/index"), method = Array(RequestMethod.GET))
    def index(model: Model, request: HttpServletRequest): String = {
        val user = request.getSession.getAttribute("user")
        model.addAttribute("user", user)
        LOGGER.info("用户" + user + "登陆服务器")
        "index"
    }
    
    /**
     * @description 根据id查找用户信息
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = Array("/findUser"), method = Array(RequestMethod.POST, RequestMethod.GET))
    def findUserById(@RequestParam("id") id: Integer): String = {
        gson.toJson(new ResultSet(userService.findUserById(id)))
    }
    
}