package com.cycloneboy.springcloud.handler;

import com.cycloneboy.springcloud.common.Constants;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * Create by  sl on 2019-09-29 19:59
 */
@Slf4j
public class TimeClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

  private int counter;

  private byte[] req;

  public TimeClientHandler() {
    req = (Constants.QUERY_TIME_ORDER + System.getProperty("line.separator")).getBytes();
  }

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
    byte[] req = new byte[msg.readableBytes()];
    msg.readBytes(req);

    String body = new String(req, CharsetUtil.UTF_8);

    log.info("Now is :{} - the counter is : {}", body, ++counter);
  }

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    ByteBuf message = null;
    for (int i = 0; i < 100; i++) {
      message = Unpooled.buffer(req.length);
      message.writeBytes(req);
      ctx.writeAndFlush(message);
    }

  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    super.exceptionCaught(ctx, cause);
    ctx.close();
  }
}
