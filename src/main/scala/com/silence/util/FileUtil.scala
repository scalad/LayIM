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
     * @description 文件保存服务器
     * @param types 文件类型/upload/image 或  /upload/file
     * @param path 文件绝对路径地址
     * @param file 二进制文件
     * @return 文件的相对路径地址
     */
    def upload(types: String, path: String, file: MultipartFile) : String = {
        var name = file.getOriginalFilename
        var paths = path + types + DateUtil.getDateString + "/"
        var result = types + DateUtil.getDateString + "/"
        //如果是图片，则使用uuid重命名图片
        if (SystemConstant.IMAGE_PATH.equals(types)) {
            name = UUIDUtil.getUUID32String() + name.substring(name.indexOf(".")) 
        } else if (SystemConstant.FILE_PATH.equals(types)) {
            //如果是文件，则区分目录
            val p = UUIDUtil.getUUID32String
            paths = paths + p
            result += p + "/"
        }
        FileUtils.copyInputStreamToFile(file.getInputStream, new File(paths, name))
        result + name
    }
    
    /**
     * @description 头像
     * @param realpath 服务器绝对路径地址
     * @param file 文件
     * @return 相对路径 
     */
    def upload(realpath: String, file: MultipartFile): String = {
        var name = file.getOriginalFilename
        name = UUIDUtil.getUUID32String + name.substring(name.indexOf("."))
        FileUtils.copyInputStreamToFile(file.getInputStream, new File(realpath, name))
        SystemConstant.AVATAR_PATH + name
    }
  
}