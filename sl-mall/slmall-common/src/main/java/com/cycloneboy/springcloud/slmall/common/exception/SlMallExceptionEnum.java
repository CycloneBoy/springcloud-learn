package com.cycloneboy.springcloud.slmall.common.exception;

import com.cycloneboy.springcloud.common.common.AbstractHttpException;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Create by  sl on 2019-12-11 12:30
 */
@Getter
@AllArgsConstructor
public enum SlMallExceptionEnum implements AbstractHttpException {

  SUCCESS("0", "Operation success."),

  FAILED("1", "Operation failed."),

  PARAMETER_ERROR("4000", "Parameter error.");


  private String code;

  private String message;

  public static SlMallExceptionEnum statOf(String code) {
    for (SlMallExceptionEnum ex : SlMallExceptionEnum.values()) {
      if (ex.getCode().equals(code)) {
        return ex;
      }
    }
    throw new SlMallException(PARAMETER_ERROR);
  }

  @Override
  public String getCode() {
    return code;
  }

  @Override
  public String getMessage() {
    return message;
  }
}
