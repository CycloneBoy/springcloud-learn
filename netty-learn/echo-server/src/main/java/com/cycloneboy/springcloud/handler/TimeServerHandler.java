package com.cycloneboy.springcloud.handler;

import com.cycloneboy.springcloud.common.Constants;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;

/**
 * Create by  sl on 2019-09-29 19:08
 */
@Slf4j
public class TimeServerHandler extends ChannelInboundHandlerAdapter {

  private int counter;

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    ByteBuf buf = (ByteBuf) msg;
    byte[] req = new byte[buf.readableBytes()];
    buf.readBytes(req);

    String body = new String(req, CharsetUtil.UTF_8)
        .substring(0, req.length - System.getProperty("line.separator").length());

    log.info("The time server receive  order :{} - the counter is : {}", body, ++counter);
    String currentTime =
        Constants.QUERY_TIME_ORDER.equalsIgnoreCase(body) ? new Date(System.currentTimeMillis())
            .toString()
            : "BAD ORDER";
    currentTime = currentTime + System.getProperty("line.separator");

    ByteBuf writeBuf = Unpooled.copiedBuffer(currentTime.getBytes());
    ctx.writeAndFlush(writeBuf);
  }

  @Override
  public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
    ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    cause.printStackTrace();
    ctx.close();
  }
}
