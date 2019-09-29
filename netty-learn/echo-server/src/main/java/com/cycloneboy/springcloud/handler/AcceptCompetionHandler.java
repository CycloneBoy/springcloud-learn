package com.cycloneboy.springcloud.handler;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * Create by  sl on 2019-09-28 10:52
 */
public class AcceptCompetionHandler implements
    CompletionHandler<AsynchronousSocketChannel, AsyncTimeServerHandler> {

  @Override
  public void completed(AsynchronousSocketChannel result,
      AsyncTimeServerHandler attachment) {
    attachment.asynchronousServerSocketChannel.accept(attachment, this);

    ByteBuffer buffer = ByteBuffer.allocate(1024);
    result
        .read(buffer, buffer, new ReadCompletionHandler(result));
  }

  @Override
  public void failed(Throwable throwable, AsyncTimeServerHandler attachment) {
    throwable.printStackTrace();
    attachment.latch.countDown();
  }
}
