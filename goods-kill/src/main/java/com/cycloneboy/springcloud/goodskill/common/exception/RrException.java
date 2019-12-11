package com.cycloneboy.springcloud.goodskill.common.exception;

import com.cycloneboy.springcloud.common.common.AbstractHttpException;
import lombok.Getter;
import lombok.Setter;

/**
 * Create by  sl on 2019-12-11 16:08
 */
@Getter
@Setter
public class RrException extends RuntimeException implements AbstractHttpException {

  private static final long serialVersionUID = 8501940893673633608L;

  private String message;

  private String code = "500";

  public RrException(String msg) {
    super(msg);
    this.message = msg;
  }

  public RrException(String msg, Throwable e) {
    super(msg, e);
    this.message = msg;
  }

  public RrException(String msg, String code) {
    super(msg);
    this.message = msg;
    this.code = code;
  }

  public RrException(String msg, String code, Throwable e) {
    super(msg, e);
    this.message = msg;
    this.code = code;
  }

}
