package com.mmall.service.impl;

import com.google.common.collect.Lists;
import com.mmall.service.IFileService;
import com.mmall.util.FTPUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by sxl on 2017/10/17.
 */
@Service("iFileService")
@Slf4j
public class FileServiceImpl implements IFileService {


    public String upload(MultipartFile file,String path){
        String fileName = file.getOriginalFilename();

        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".")+1);
        String uploadFileName = UUID.randomUUID().toString()+"."+fileExtensionName;
        log.info("开始上传文件,上传文件的文件名:{},上传的路径:{},新的文件名:{}",fileName,path,uploadFileName);

        File fileDir = new File(path);
        if(!fileDir.exists()){
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }
        File targetFile = new File(path,uploadFileName);

        try {
            file.transferTo(targetFile);
            //上传文件完成
            
            FTPUtil.uploadFile(Lists.<File>newArrayList(targetFile));
            //已经上传到ftp服务器上

            targetFile.delete();

        } catch (IOException e) {
            log.info("上传文件出错",e);
            return null;
        }
        return targetFile.getName();
    }
}
