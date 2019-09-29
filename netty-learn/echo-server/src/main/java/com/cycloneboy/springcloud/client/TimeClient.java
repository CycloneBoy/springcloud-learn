package com.cycloneboy.springcloud.client;

import com.cycloneboy.springcloud.handler.AsyncTimeClientHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * Create by  sl on 2019-09-28 11:21
 */
@Slf4j
public class TimeClient {

  public static void main(String[] args) {
    int port = 8088;

    if (args != null && args.length > 0) {
      port = Integer.parseInt(args[0]);
    }

    new Thread(new AsyncTimeClientHandler("127.0.0.1", port), "AIO-AsyncTimeClientHandler-001")
        .start();

  }
}
