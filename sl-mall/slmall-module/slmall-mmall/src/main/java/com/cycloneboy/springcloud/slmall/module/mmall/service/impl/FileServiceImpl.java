package com.cycloneboy.springcloud.slmall.module.mmall.service.impl;

import com.cycloneboy.springcloud.slmall.module.mmall.service.IFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Create by  sl on 2020-01-12 17:51
 */
@Slf4j
@Service
public class FileServiceImpl implements IFileService {

  @Override
  public String upload(MultipartFile file, String path) {
    return null;
  }
}
