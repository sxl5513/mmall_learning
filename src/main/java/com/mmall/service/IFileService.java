package com.mmall.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by sxl on 2017/10/17.
 */
public interface IFileService {

    String upload(MultipartFile file, String path);
}
