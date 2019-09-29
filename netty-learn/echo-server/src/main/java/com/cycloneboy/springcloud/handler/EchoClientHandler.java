package com.cycloneboy.springcloud.handler;

import com.cycloneboy.springcloud.common.Constants;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * Create by  sl on 2019-09-14 21:44
 */
@Slf4j
@Sharable
public class EchoClientHandler extends ChannelInboundHandlerAdapter {

  int counter = 0;

  static final String ECHO_REQ =
      "Hi, Cycloneboy. Welcome to Netty." + Constants.DELIMITER_$;

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    for (int i = 0; i < 10; i++) {
      ctx.writeAndFlush(Unpooled.copiedBuffer(ECHO_REQ.getBytes()));
    }

  }

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    log.info("This is {} Client receive : [{}] ", ++counter, msg);
  }

  @Override
  public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
    ctx.flush();
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    cause.printStackTrace();
    ctx.close();
  }
}
