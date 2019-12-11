package com.cycloneboy.springcloud.common.domain;


import com.cycloneboy.springcloud.common.common.AbstractHttpException;
import com.cycloneboy.springcloud.common.common.HttpExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Data;


/**
 * create by CycloneBoy on 2019-05-26 14:11
 *
 * @author CycloneBoy
 */
@Data
@AllArgsConstructor
public class BaseResponse {

    private String code;

    private String message;

    private Object data;

    public static BaseResponse ok(){
        return new BaseResponse();
    }

    public static BaseResponse ok(String message) {
        return new BaseResponse(HttpExceptionEnum.SUCCESS, message);
    }

    public static BaseResponse failed(){
        return new BaseResponse(HttpExceptionEnum.FAILED);
    }

    public static BaseResponse failed(String message) {
        return new BaseResponse(HttpExceptionEnum.FAILED, message);
    }

    public BaseResponse() {
        this.code = HttpExceptionEnum.SUCCESS.getCode();
        this.message = HttpExceptionEnum.SUCCESS.getMessage();
        this.data = "";
    }

    public BaseResponse(Object data) {
        this.code = HttpExceptionEnum.SUCCESS.getCode();
        this.message = HttpExceptionEnum.SUCCESS.getMessage();
        this.data = data;
    }

    public BaseResponse(AbstractHttpException exception) {
        this.code = exception.getCode();
        this.message = exception.getMessage();
        this.data = "";
    }

    public BaseResponse(AbstractHttpException exception, String message) {
        this.code = exception.getCode();
        this.message = message;
        this.data = "";
    }

    public BaseResponse(AbstractHttpException exception, Object data) {
        this.code = exception.getCode();
        this.message = exception.getMessage();
        this.data = data;
    }
}
