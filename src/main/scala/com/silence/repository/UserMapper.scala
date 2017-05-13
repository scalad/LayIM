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
import com.silence.entity.AddMessage
import com.silence.domain.AddInfo
import com.silence.entity.AddFriends
import com.silence.entity.AddFriends
import org.apache.ibatis.annotations.Options
import com.silence.entity.FriendGroup
import org.apache.ibatis.annotations.Delete
import com.silence.domain.GroupMember

/**
 * @description User Dao
 * @date 2017年1月11日
 * @author wang
 *
 */
trait UserMapper {

    /**
     * @description 退出群
     * @param groupMember
     */
    @Delete(Array("delete from t_group_members where gid=#{gid} and uid=#{uid}"))
    def leaveOutGroup(groupMember: GroupMember): Int
  
    /**
     * @description 添加群成员
     * @param gid 群编号
     * @param uid 用户编号
     */
    @Insert(Array("insert into t_group_members(gid,uid) values(#{gid},#{uid})"))
    def addGroupMember(groupMember: GroupMember): Int
  
    /**
     * @description 删除好友
     * @param friendId 好友Id
     * @param uId 个人Id
     * @return Int
     */
    @Delete(Array("delete from t_friend_group_friends where fgid in (select id from t_friend_group where uid in (#{friendId}, #{uId})) and uid in(#{friendId}, #{uId})"))
    def removeFriend(@Param("friendId") friendId: Integer, @Param("uId") uId: Integer): Int
  
    /**
     * @description 更新用户头像
     * @param userId
     * @param avatar
     * @return 
     */
    @Update(Array("update t_user set avatar=#{avatar} where id=#{userId}"))
    def updateAvatar(@Param("userId") userId: Integer, @Param("avatar") avatar: String): Int
  
    /**
     * @description 移动好友分组
     * @param groupId 新的分组id
     * @param uId 被移动的好友id
     * @param mId 我的id
     * @return
     */
    @Update(Array("update t_friend_group_friends set fgid = #{groupId} where id =(select t.id from ((select id from t_friend_group_friends where fgid in (select id from t_friend_group where uid = #{mId}) and uid = #{uId}) t))"))
    def changeGroup(@Param("groupId") groupId: Integer, @Param("uId") uId: Integer, @Param("mId") mId: Integer):Int
  
    /**
     * @description 添加好友操作
     * @param mgid 分组id
     * @param tid 对方用户id
     * @param mid 自己的id
     * @param tgid 对方分组id
     */
    @Insert(Array("insert into t_friend_group_friends(fgid,uid) values(#{mgid},#{tid}),(#{tgid},#{mid})"))
    def addFriend(addFriends: AddFriends): Int
  
    /**
     * @description 统计未处理的消息
     * @param uid
     */
    @Select(Array("<script> select count(*) from t_add_message where to_uid=#{uid} <if test='agree!=null'> and agree=#{agree} </if> </script>"))
    def countUnHandMessage(@Param("uid") uid: Integer, @Param("agree") agree: Integer): Integer
  
    /**
     * @description 查询添加好友、群组信息
     * @param uid
     * @return List[AddInfo]
     */
    @Select(Array("select * from t_add_message where to_uid = #{uid} order by time desc"))
    @Results(value = Array(new Result(property="from",column="from_uid"),
             new Result(property="uid",column="to_uid"),
             new Result(property="read",column="agree"),
             new Result(property="from_group",column="group_id"))
     )
    def findAddInfo(@Param("uid") uid: Integer): List[AddInfo]
  
    /**
     * @description 更新好友、群组信息请求
     * @param addMessage
     * @return
     */
    @Update(Array("update t_add_message set agree = #{agree} where id = #{id}"))
    def updateAddMessage(addMessage: AddMessage): Int
    
    /**
     * @description 添加好友、群组信息请求
     * @param addMessage
     * @return
     */  
    @Insert(Array("insert into t_add_message(from_uid,to_uid,group_id,remark,agree,type,time) values (#{fromUid},#{toUid},#{groupId},#{remark},#{agree},#{Type},#{time}) ON DUPLICATE KEY UPDATE remark=#{remark},time=#{time},agree=#{agree};"))
    def saveAddMessage(addMessage: AddMessage): Int
  
    /**
     * @description 根据群名模糊统计
     * @param groupName
     * @return
     */
    @Select(Array("<script> select count(*) from t_group where 1 = 1 <if test='groupName != null'> and group_name like '%${groupName}%'</if></script>"))
    def countGroup(@Param("groupName")  groupName: String): Int
  
    /**
     * @description 根据群名模糊查询群
     * @param groupName
     * @return 
     */
    @Select(Array("<script> select id,group_name,avatar,create_id from t_group where 1=1 <if test='groupName != null'> and group_name like '%${groupName}%'</if></script>"))
    def findGroup(@Param("groupName") groupName: String): List[GroupList]
    
    /**
     * @description 根据群id查询群信息
     * @param gid
     * @return
     */
    @Select(Array("select id,group_name,avatar,create_id from t_group where id = #{gid}"))
    def findGroupById(@Param("gid") gid: Integer): GroupList
    
    /**
     * @description 根据用户名和性别统计用户
     * @param username
     * @param sex
     */
    @Select(Array("<script> select count(*) from t_user where 1 = 1 <if test='username != null'> and username like '%${username}%'</if><if test='sex != null'> and sex=#{sex}</if></script>"))
    def countUser(@Param("username")  username: String, @Param("sex") sex: Integer): Int
  
    /**
     * @description 根据用户名和性别查询用户
     * @param username
     * @param sex
     */
    @Select(Array("<script> select id,username,status,sign,avatar,email from t_user where 1=1 <if test='username != null'> and username like '%${username}%'</if><if test='sex != null'> and sex=#{sex}</if></script>"))
    def findUsers(@Param("username")  username: String, @Param("sex") sex: Integer): List[User]
  
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
    def activeUser(@Param("activeCode") activeCode: String): Int
  
    /**
     * @description 根据群组ID查询群里用户的信息
     * @param gid
     * @return List[User]
     */
    @Select(Array("select id,username,status,sign,avatar,email from t_user where id in(select uid from t_group_members where gid = #{gid})"))
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
    @Results(value = Array(new Result(property="createId",column="create_id")))
    @Select(Array("select id,group_name,avatar,create_id from t_group where id in(select distinct gid from t_group_members where uid = #{uid})"))
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
    @Select(Array("select id,username,avatar,sign,status,email,sex from t_user where id in(select uid from t_friend_group_friends where fgid = #{fgid})"))
    def findUsersByFriendGroupIds(fgid: Int): List[User]
    
    /**
     * @description 保存用户信息
     * @param user
     * @return Int
     */
    @Insert(Array("insert into t_user(username,password,email,create_date,active) values(#{username},#{password},#{email},#{createDate},#{active})"))
    @Options(useGeneratedKeys = true, keyProperty = "id")
    def saveUser(user: User): Int = user.getId
    
    /**
     * @description 
     * @param user
     * @return User
     */
    @Select(Array("select id,username,email,avatar,sex,sign,password,status,active from t_user where email = #{email}"))
    def matchUser(email: String): User
 
    /**
     * @description 创建好友分组列表
     * @param uid
     * @param groupName
     */
    @Insert(Array("insert into t_friend_group(group_name,uid) values(#{groupName},#{uid})"))
    def createFriendGroup(friendGroup: FriendGroup): Int
    
}