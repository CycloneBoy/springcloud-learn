package com.cycloneboy.springcloud.slmall.common.exception;

import com.cycloneboy.springcloud.slmall.common.utils.ResultUtil;
import com.cycloneboy.springcloud.slmall.common.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Create by  sl on 2020-01-11 15:24
 */
@Slf4j
//@RestControllerAdvice
public class RestCtrlExceptionHandler {


  @ExceptionHandler(SlMallException.class)
  @ResponseStatus(value = HttpStatus.OK)
  public Result<Object> handleXCloudException(SlMallException e) {
    String errorMsg = "SlMallException exception";
    if (e != null) {
      errorMsg = e.getMessage();
      log.warn(e.toString());
    }
    return new ResultUtil<>().setErrorMsg(500, errorMsg);
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(value = HttpStatus.OK)
  public Result<Object> handleException(Exception e) {
    String errorMsg = "Exception";
    if (e != null) {
      errorMsg = e.getMessage();
      log.warn(e.toString());
    }
    return new ResultUtil<>().setErrorMsg(500, errorMsg);
  }
}
