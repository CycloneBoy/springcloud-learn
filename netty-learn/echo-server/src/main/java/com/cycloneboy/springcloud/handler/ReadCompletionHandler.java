package com.cycloneboy.springcloud.handler;

import static com.cycloneboy.springcloud.common.Constants.QUERY_TIME_ORDER;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;

/**
 * Create by sl on 2019-09-28 10:57
 */
@Slf4j
public class ReadCompletionHandler implements CompletionHandler<Integer, ByteBuffer> {


  private AsynchronousSocketChannel channel;

  public ReadCompletionHandler(AsynchronousSocketChannel channel) {
    this.channel = channel;
  }

  @Override
  public void completed(Integer result, ByteBuffer attachment) {
    attachment.flip();
    byte[] body = new byte[attachment.remaining()];
    attachment.get(body);

    String req = null;
    try {
      req = new String(body, "UTF-8");
      log.info("the time server receive order： {}", req);
      String currentTime =
          QUERY_TIME_ORDER.equalsIgnoreCase(req) ? new Date(System.currentTimeMillis()).toString()
              : "BAD ORDER";
      doWrite(currentTime);
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }

  }

  private void doWrite(String currentTime) {
    if (currentTime != null && currentTime.trim().length() > 0) {
      byte[] bytes = currentTime.getBytes();
      ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
      writeBuffer.put(bytes);
      writeBuffer.flip();

      channel.write(writeBuffer, writeBuffer, new CompletionHandler<Integer, ByteBuffer>() {
        @Override
        public void completed(Integer result, ByteBuffer buffer) {
          // 如果没有发送完成,继续发送
          if (buffer.hasRemaining()) {
            channel.write(buffer, buffer, this);
          }
        }

        @Override
        public void failed(Throwable exc, ByteBuffer buffer) {
          try {
            channel.close();
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      });
    }
  }

  @Override
  public void failed(Throwable throwable, ByteBuffer attachment) {
    try {
      this.channel.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
