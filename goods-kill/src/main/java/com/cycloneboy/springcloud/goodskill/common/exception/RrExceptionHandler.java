package com.cycloneboy.springcloud.goodskill.common.exception;

import com.cycloneboy.springcloud.common.domain.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常处理器
 * <p>
 * Create by  sl on 2019-12-11 16:10
 */
@Slf4j
@RestControllerAdvice
public class RrExceptionHandler {

  @ExceptionHandler(RrException.class)
  public BaseResponse handleRRException(RrException e) {
    return new BaseResponse(e);
  }

  @ExceptionHandler(DuplicateKeyException.class)
  public BaseResponse handleDuplicateKeyException(DuplicateKeyException e) {
    log.error(e.getMessage(), e);
    return BaseResponse.failed("数据库中已存在该记录");
  }

  @ExceptionHandler(Exception.class)
  public BaseResponse handleException(Exception e) {
    log.error(e.getMessage(), e);
    return BaseResponse.failed(e.getMessage());
  }
}
