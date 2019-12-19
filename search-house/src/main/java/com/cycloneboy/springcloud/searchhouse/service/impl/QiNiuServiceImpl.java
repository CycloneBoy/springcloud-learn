package com.cycloneboy.springcloud.searchhouse.service.impl;

import com.cycloneboy.springcloud.searchhouse.service.IQiNiuService;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import java.io.File;
import java.io.InputStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Create by  sl on 2019-12-19 15:38
 */
@Slf4j
@Service
public class QiNiuServiceImpl implements IQiNiuService {

  @Override
  public Response uploadFile(File file) throws QiniuException {
    return null;
  }

  @Override
  public Response uploadFile(InputStream inputStream) throws QiniuException {
    return null;
  }

  @Override
  public Response delete(String key) throws QiniuException {
    return null;
  }
}
