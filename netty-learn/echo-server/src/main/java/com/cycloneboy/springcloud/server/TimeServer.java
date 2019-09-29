package com.cycloneboy.springcloud.server;

import com.cycloneboy.springcloud.handler.AsyncTimeServerHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * Create by  sl on 2019-09-28 10:45
 */
@Slf4j
public class TimeServer {

  public static void main(String[] args) {
    int port = 8088;

    if (args != null && args.length > 0) {
      port = Integer.parseInt(args[0]);
    }

    AsyncTimeServerHandler timeServerHandler = new AsyncTimeServerHandler(port);
    new Thread(timeServerHandler, "AIO-AysncTimeServerHandler-001").start();
  }
}
