package com.cycloneboy.springcloud.goodskill.queue.disruptor;

import java.io.Serializable;
import lombok.Data;

/**
 * 事件对象（秒杀事件）
 * <p>
 * Create by  sl on 2019-12-12 14:36
 */
@Data
public class SeckillEvent implements Serializable {

  private static final long serialVersionUID = 1600570876008162293L;

  private long seckillId;

  private long userId;

}
