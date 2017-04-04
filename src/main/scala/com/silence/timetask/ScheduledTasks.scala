package com.silence.timetask

import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.Configurable
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.slf4j.LoggerFactory
import java.text.SimpleDateFormat
import java.util.Date
import com.silence.util.DateUtil
import org.slf4j.Logger

/**
 * @description ScheduledTasks定时任务处理
 * @date 2017年3月27日
 * @author wang
 */
@Component
@Configurable
//启动定时任务
@EnableScheduling 
class ScheduledTasks {
  
    private final val LOGGER:Logger = LoggerFactory.getLogger(classOf[ScheduledTasks])
    /**
     * 每1分钟执行一次
     */
    @Scheduled(cron = "0 */1 *  * * * ")
    def redisTask = {
        LOGGER.info("Scheduling Tasks Examples By Cron: The time is now " + DateUtil.getDate)
    }
    
    @Scheduled(fixedRate = 1000 * 30)
    def dataBaseTask = {
        LOGGER.info("Scheduling Tasks Examples: The time is now " + DateUtil.getDate)
    }

}