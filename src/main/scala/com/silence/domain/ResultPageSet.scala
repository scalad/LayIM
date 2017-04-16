package com.silence.domain

import scala.beans.BeanProperty

class ResultPageSet[T] extends ResultSet[T]{
  
    @BeanProperty var pages: Int = _
  
    def this(data: T) = {
        this
        this.data = data      
    }
}