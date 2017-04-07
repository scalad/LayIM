package com.silence.service

import com.silence.enties.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import com.silence.repository.UserMapper
import org.springframework.transaction.annotation.Transactional
import org.springframework.cache.annotation.Cacheable
import org.springframework.cache.annotation.CacheEvict

import java.util.List
import com.silence.util.UUIDUtil
import com.silence.util.SecurityUtil
import com.silence.domain.GroupList
import com.silence.domain.FriendList
import com.silence.domain.FriendList
import scala.collection.JavaConversions
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * @description 用户信息相关操作
 * @date 2017-04-06
 */
@Service
class UserService @Autowired()(private var userMapper: UserMapper) {

    private final val LOGGER: Logger = LoggerFactory.getLogger(classOf[UserService])
    
    /**
     * @description 用户邮件和密码是否匹配
     * @param user
     * @return User
     */
    def matchUser(user: User): User = {
        if (user == null || user.getEmail == null) {
            return null
        }
        val u: User = userMapper.matchUser(user.getEmail)
        //密码不匹配
        if(u == null || !SecurityUtil.matchs(user.getPassword, u.getPassword)){
            return null      
        }
        u
    }
    
    /**
     * @description 根据群组ID查询群里用户的信息
     * @param gid
     * @return List[User]
     */
    def findUserByGroupId(gid: Int): List[User] = userMapper.findUserByGroupId(gid)
    
    /**
     * @description 根据ID查询用户好友分组列表信息
     * @param uid 用户ID
     * @return List[FriendList]
     */
    @Transactional
    @Cacheable(value = Array("findFriendGroupsById"), keyGenerator = "wiselyKeyGenerator")
    def findFriendGroupsById(uid: Int): List[FriendList] = {
        var friends = userMapper.findFriendGroupsById(uid)
        //封装分组列表下的好友信息
        JavaConversions.collectionAsScalaIterable(friends).foreach { 
            friend:FriendList => {
                friend.list = userMapper.findUsersByFriendGroupId(friend.id)
            }
        }
        friends
    }
  
    /**
     * @description 根据ID查询用户信息
     * @param id
     * @return User
     */
    def findUserById(id: Int): User = {
        userMapper.findUserById(id)
    }
    
    /**
     * @description 根据ID查询用户群组信息
     * @param id
     * @return List[Group]
     */
    def findGroupsById(id: Int): List[GroupList] = {
        userMapper.findGroupsById(id)
    }
    
    //清除缓存
    @CacheEvict(value = Array("findFriendGroupsById"), allEntries = true)  
    def saveUser(user: User): Int = {
        user.setActive(UUIDUtil.getUUID64String)
        user.setPassword(SecurityUtil.encrypt(user.getPassword))
        userMapper.saveUser(user)
    }
        
}