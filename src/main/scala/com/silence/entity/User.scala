package com.silence.enties

import java.util.Date
import scala.beans.BeanProperty
import javax.persistence.Id
import javax.persistence.GeneratedValue
import org.hibernate.validator.constraints.NotEmpty
import javax.persistence.Entity
import javax.persistence.Table
import javax.persistence.Temporal
import javax.persistence.TemporalType
import org.springframework.format.annotation.DateTimeFormat
import javax.validation.constraints.NotNull
import org.hibernate.validator.constraints.NotBlank
import javax.persistence.Column

/**
 * @description 用户属性
 * @date 2017年1月11日
 * @author wang
 *
 */
@Table(name = "t_user")
@Entity
class User {

    @Id
    @GeneratedValue
    @BeanProperty
    var id: Long = _
    
    //用户名
    @BeanProperty
    @NotBlank
    var username: String = _
  
    //密码
    @BeanProperty
    @NotEmpty
    var password: String = _
    
    //签名
    @BeanProperty
    @NotEmpty
    var sign: String = _
    
    //头像
    @BeanProperty
    var avatar: String = _
    
    //电话
    @BeanProperty
    @NotEmpty
    var telephone: String = _
    
    //创建时间
    @BeanProperty
    @NotNull
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Column(name = "create_date")
    var createDate: Date = _
    
    //性别
    @BeanProperty
    var sex: Int = _
  
    override def toString = id + ", " + username + ", " + password + ", " + sign + ", " + avatar + ", " + telephone + ", " + createDate
    
}