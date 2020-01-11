package com.cycloneboy.springcloud.slmall.common.advice;


import com.cycloneboy.springcloud.common.domain.BaseResponse;
import com.cycloneboy.springcloud.slmall.common.exception.SlMallException;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Qinyi.
 */
@Slf4j
//@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler(value = SlMallException.class)
    public BaseResponse handlerAdException(HttpServletRequest req,
        SlMallException ex) {
        BaseResponse response = BaseResponse.failed();
        response.setData(ex.getMessage());
        return response;
    }

    @ExceptionHandler(SlMallException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public BaseResponse handleXCloudException(SlMallException e) {
        String errorMsg = "SlMallException exception";
        if (e != null) {
            errorMsg = e.getMessage();
            log.warn(e.toString());
        }
        return BaseResponse.failed(errorMsg);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.OK)
    public BaseResponse handleException(Exception e) {
        String errorMsg = "Exception";
        if (e != null) {
            errorMsg = e.getMessage();
            log.warn(e.toString());
        }
        return BaseResponse.failed(errorMsg);
    }
}
