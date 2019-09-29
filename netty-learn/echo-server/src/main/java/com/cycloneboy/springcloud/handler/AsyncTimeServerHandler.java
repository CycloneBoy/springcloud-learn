package com.cycloneboy.springcloud.handler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.CountDownLatch;
import lombok.extern.slf4j.Slf4j;

/**
 * Create by  sl on 2019-09-28 10:47
 */
@Slf4j
public class AsyncTimeServerHandler implements Runnable {

  private int port;
  CountDownLatch latch;

  AsynchronousServerSocketChannel asynchronousServerSocketChannel;

  public AsyncTimeServerHandler(int port) {
    this.port = port;

    try {
      asynchronousServerSocketChannel = AsynchronousServerSocketChannel.open();
      asynchronousServerSocketChannel.bind(new InetSocketAddress(port));
      log.info("The time server is start in port : {}", port);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void run() {

    latch = new CountDownLatch(1);
    doAccept();

    try {
      latch.await();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private void doAccept() {
    asynchronousServerSocketChannel.accept(this, new AcceptCompetionHandler());
  }
}
