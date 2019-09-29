package com.cycloneboy.springcloud.handler;

import com.cycloneboy.springcloud.common.Constants;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;
import lombok.extern.slf4j.Slf4j;

/**
 * Create by sl on 2019-09-28 11:25
 */
@Slf4j
public class AsyncTimeClientHandler implements CompletionHandler<Void, AsyncTimeClientHandler>,
    Runnable {

  private AsynchronousSocketChannel client;
  private String host;
  private int port;
  private CountDownLatch latch;

  public AsyncTimeClientHandler(String host, int port) {
    this.host = host;
    this.port = port;

    try {
      client = AsynchronousSocketChannel.open();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void run() {
    latch = new CountDownLatch(1);

    client.connect(new InetSocketAddress(host, port), this, this);

    try {
      latch.await();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    try {
      client.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void completed(Void result, AsyncTimeClientHandler attachment) {
    byte[] req = Constants.QUERY_TIME_ORDER.getBytes();
    ByteBuffer writeBuffer = ByteBuffer.allocate(req.length);
    writeBuffer.put(req);
    writeBuffer.flip();

    client.write(writeBuffer, writeBuffer, new CompletionHandler<Integer, ByteBuffer>() {
      @Override
      public void completed(Integer result, ByteBuffer buffer) {
        // 如果没有发送完成,继续发送
        if (buffer.hasRemaining()) {
          client.write(buffer, buffer, this);
        } else {
          ByteBuffer readBuffer = ByteBuffer.allocate(1024);
          client.read(readBuffer, readBuffer, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer byteBuffer) {
              byteBuffer.flip();
              byte[] bytes = new byte[byteBuffer.remaining()];
              byteBuffer.get(bytes);
              String body;
              try {
                body = new String(bytes, "UTF-8");
                log.info("Now is : {}", body);
                latch.countDown();
              } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
              }

            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
              try {
                client.close();
                latch.countDown();
              } catch (IOException e) {
                e.printStackTrace();
              }

            }
          });
        }
      }

      @Override
      public void failed(Throwable exc, ByteBuffer attachment) {
        try {
          client.close();
          latch.countDown();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    });

  }

  @Override
  public void failed(Throwable exc, AsyncTimeClientHandler attachment) {
    try {
      client.close();
      latch.countDown();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
