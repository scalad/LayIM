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

/**
 * @description 用户属性
 * @date 2017年1月11日
 * @author wang
 *
 */
@Table(name = "user")
@Entity
class User {

  @Id
  @GeneratedValue
  @BeanProperty
  var id: Long = _
  
  @BeanProperty
  @NotBlank
  var name: String = _
  
  @BeanProperty
  @NotNull
  @DateTimeFormat(pattern="yyyy-MM-dd")
  var birthday: Date = _
  
  @BeanProperty
  @NotEmpty
  var telephone: String = _
  
  override def toString = "id = " + id + ",name = " + name + ",birthday = " + birthday + ",telephone = " + telephone
  
}