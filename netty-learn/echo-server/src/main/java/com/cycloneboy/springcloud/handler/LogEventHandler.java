package com.cycloneboy.springcloud.handler;

import com.cycloneboy.springcloud.domain.LogEvent;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * Create by  sl on 2019-09-19 20:42
 */
@Slf4j
public class LogEventHandler extends SimpleChannelInboundHandler<LogEvent> {

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    super.exceptionCaught(ctx, cause);
    ctx.close();
  }

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, LogEvent msg) throws Exception {
    StringBuilder builder = new StringBuilder();
    builder.append(msg.getReceived());
    builder.append(" [");
    builder.append(msg.getSource().toString());
    builder.append("] [");
    builder.append(msg.getLogfile());
    builder.append("] : [");
    builder.append(msg.getMsg());

    log.info(builder.toString());

  }
}
