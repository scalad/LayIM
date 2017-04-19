package com.silence.domain

import scala.beans.BeanProperty

/**
 * @description 分页结果集
 * @date 2017-04-16
 * @author silence
 */
class ResultPageSet[T] extends ResultSet[T]{
  
    @BeanProperty var pages: Int = _
  
    def this(data: T) = {
        this
        this.data = data      
    }
    
    def this(data: T, pages: Int) = {
        this
        this.data = data
        this.pages = pages
    }
}