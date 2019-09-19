package com.cycloneboy.springcloud.domain;

import java.net.InetSocketAddress;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Create by  sl on 2019-09-19 20:05
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LogEvent {

  public static final byte SEPARATOR = (byte) ':';

  private InetSocketAddress source;
  private String logfile;
  private String msg;
  private long received;

  public LogEvent(String logfile, String msg) {
    this(null, logfile, msg, -1);
  }


}
