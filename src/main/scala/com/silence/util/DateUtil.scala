package com.silence.util

import java.text.SimpleDateFormat
import java.util.Date

/**
 * @description 时间工具
 * @date 2017年3月27日
 * @author wang
 */
object DateUtil {
 
    private final val parttern = "yyyy-mm-dd: HH:MM:ss"
    
    /**
     * @description 获取格式化后的当前时间
     * @return  
     */
    def getDate(): String = new SimpleDateFormat(parttern).format(new Date)
    
    /**
     * @description 获取特定格式的当前时间
     * @return  
     */
    def getDate(parttern: String): String = new SimpleDateFormat(parttern).format(new Date)
  
}