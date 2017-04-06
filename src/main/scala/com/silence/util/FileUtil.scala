package com.silence.util

import org.springframework.web.multipart.MultipartFile
import java.io.File
import org.apache.commons.io.FileUtils
import com.silence.common.SystemConstant

/**
 * @description 服务器文件工具
 * @date 2017-04-06
 * @author silence
 */
object FileUtil {
  
    /**
     * @description 服务器保存我呢见
     * @param path 文件绝对路径地址
     * @param file 二进制文件
     * @return 文件的相对路径地址
     */
    def upload(types: String, path: String, file: MultipartFile) : String = {
        val name = file.getOriginalFilename
    		//图片保存到服务器
        val paths = path + DateUtil.getDateString()
        FileUtils.copyInputStreamToFile(file.getInputStream, new File(paths, name))
        types + DateUtil.getDateString() + "/" + name
    }
  
}