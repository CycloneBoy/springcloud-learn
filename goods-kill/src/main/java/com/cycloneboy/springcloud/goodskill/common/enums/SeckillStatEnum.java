package com.cycloneboy.springcloud.goodskill.common.enums;

import com.cycloneboy.springcloud.common.common.AbstractHttpException;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Create by  sl on 2019-12-11 12:30
 */
@Getter
@AllArgsConstructor
public enum SeckillStatEnum implements AbstractHttpException {

  MUCH(2, "哎呦喂，人也太多了，请稍后！"),
  SUCCESS(1, "秒杀成功"),
  END(0, "秒杀结束"),
  REPEAT_KILL(-1, "重复秒杀"),
  INNER_ERROR(-2, "系统异常"),
  DATE_REWRITE(-3, "数据篡改");

  private Integer state;

  private String info;

  public static SeckillStatEnum statOf(int index) {
    for (SeckillStatEnum state : SeckillStatEnum.values()) {
      if (state.getState() == index) {
        return state;
      }
    }
    return null;
  }

  @Override
  public String getCode() {
    return state.toString();
  }

  @Override
  public String getMessage() {
    return info;
  }
}
