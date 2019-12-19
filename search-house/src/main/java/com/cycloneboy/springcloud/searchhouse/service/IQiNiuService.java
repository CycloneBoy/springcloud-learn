package com.cycloneboy.springcloud.searchhouse.service;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import java.io.File;
import java.io.InputStream;

/**
 * 七牛云服务
 * <p>
 * Create by  sl on 2019-12-19 15:37
 */
public interface IQiNiuService {

  Response uploadFile(File file) throws QiniuException;

  Response uploadFile(InputStream inputStream) throws QiniuException;

  Response delete(String key) throws QiniuException;

}
