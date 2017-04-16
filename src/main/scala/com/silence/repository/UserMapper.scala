package com.silence.repository

import com.silence.enties.User
import org.apache.ibatis.annotations.Select
import org.apache.ibatis.annotations.Insert
import java.util.List
import com.silence.domain.GroupList
import com.silence.domain.FriendList
import org.apache.ibatis.annotations.Update
import org.apache.ibatis.annotations.Param
import com.silence.entity.Receive
import org.apache.ibatis.annotations.Results
import org.apache.ibatis.annotations.Result

/**
 * @description User Dao
 * @date 2017年1月11日
 * @author wang
 *
 */
trait UserMapper {

    @Select(Array("<script> select count(*) from t_user <if test='email != null'> and email like %#{email}%</if></script>"))
    def countUser(@Param("email")  email: String): Int
  
    @Select(Array("<script> select id,username,status,sign,avatar,email from t_user <if test='email != null'> and email like %#{email}%</if></script>"))
    def findUsers(@Param("email") email: String): List[User]
  
    /**
     * @description 统计查询消息
     * @param uid 消息所属用户
     * @param mid 来自哪个用户
     * @param Type 消息类型，可能来自friend或者group
     */
    @Select(Array("<script> select count(*) from t_message where type = #{Type} and " +
      "<choose><when test='uid!=null and mid !=null'>(toid = #{uid} and mid = #{mid}) or (toid = #{mid} and mid = #{uid}) </when><when test='mid != null'> mid = #{mid} </when></choose> order by timestamp </script>"))
    def countHistoryMessage(@Param("uid") uid: Integer, @Param("mid") mid: Integer, @Param("Type") Type: String): Int
    
    /**
     * @description 查询消息
     * @param uid 消息所属用户
     * @param mid 来自哪个用户
     * @param Type 消息类型，可能来自friend或者group
     */
    @Results(value = Array(new Result(property="id",column="mid")))
    @Select(Array("<script> select toid,fromid,mid,content,type,timestamp,status from t_message where type = #{Type} and " +
      "<choose><when test='uid!=null and mid !=null'>(toid = #{uid} and mid = #{mid}) or (toid = #{mid} and mid = #{uid}) </when><when test='mid != null'> mid = #{mid} </when></choose> order by timestamp </script>"))
    def findHistoryMessage(@Param("uid") uid: Integer, @Param("mid") mid: Integer, @Param("Type") Type: String): List[Receive]
 
    /**
     * @description 查询消息
     * @param uid
     * @param status 历史消息还是离线消息 0代表离线 1表示已读
     */
    @Results(value = Array(new Result(property="id",column="mid")))
    @Select(Array("select toid,fromid,mid,content,type,timestamp,status from t_message where toid = #{uid} and status = #{status}"))
    def findOffLineMessage(@Param("uid") uid: Integer, @Param("status") status: Integer): List[Receive]
  
    /**
     * @description 保存用户聊天记录
     * @param receive 聊天记录信息
     * @return Int
     */
    @Insert(Array("insert into t_message(mid,toid,fromid,content,type,timestamp,status) values(#{id},#{toid},#{fromid},#{content},#{type},#{timestamp},#{status})"))
    def saveMessage(receive: Receive): Int
    
    /**
     * @description 更新签名
     */
    @Update(Array("update t_user set sign = #{sign} where id = #{uid}"))
    def updateSign(@Param("sign") sign: String, @Param("uid") uid: Integer): Int
  
    /**
     * @description 激活用户账号
     * @param activeCode
     * @return List[User]
     */
    @Update(Array("update t_user set status = 'offline' where active = #{activeCode}"))
    def activeUser(activeCode: String): Int
  
    /**
     * @description 根据群组ID查询群里用户的信息
     * @param gid
     * @return List[User]
     */
    @Select(Array("select id,username,status,sign,avatar from t_user where id in(select uid from t_group_members where gid = #{gid})"))
    def findUserByGroupId(gid: Int): List[User]
  
    /**
     * @description 根据ID查询用户信息
     * @param id
     * @return User
     */
    @Select(Array("select id,username,status,sign,avatar,email,sex,create_date from t_user where id = #{id}"))
    def findUserById(id: Int): User
    
    /**
     * @description 根据ID查询用户群组列表,不管是自己创建的还是别人创建的
     * @param uid 用户ID
     * @return List[Group]
     */
    @Select(Array("select id,group_name,avatar from t_group where id in(select distinct gid from t_group_members where uid = #{uid})"))
    def findGroupsById(uid: Int): List[GroupList]
    
    /**
     * @description 根据ID查询用户好友分组列表
     * @param uid 用户ID
     * @return List[FriendList]
     */
    @Select(Array("select id, group_name from t_friend_group where uid = #{uid}"))
    def findFriendGroupsById(uid: Int): List[FriendList]
    
    /**
     * @description 根据好友列表ID查询用户信息
     * @param fgid
     * @return List[User]
     */
    @Select(Array("select id,username,avatar,sign,status from t_user where id in(select uid from t_friend_group_friends where fgid = #{fgid})"))
    def findUsersByFriendGroupIds(fgid: Int): List[User]
    
    /**
     * @description 保存用户信息
     * @param user
     * @return Int
     */
    @Insert(Array("insert into t_user(username,password,email,create_date,active) values(#{username},#{password},#{email},#{createDate},#{active})"))
    def saveUser(user: User): Int
    
    /**
     * @description 
     * @param user
     * @return User
     */
    @Select(Array("select id,username,email,avatar,sex,sign,password,status,active from t_user where email = #{email}"))
    def matchUser(email: String): User
        
}