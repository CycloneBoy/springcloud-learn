package com.cycloneboy.springcloud.config;

import com.cycloneboy.springcloud.domain.LogEvent;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.CharsetUtil;
import java.net.InetSocketAddress;
import java.util.List;

/**
 * Create by  sl on 2019-09-19 20:10
 */
public class LogEventEncoder extends MessageToMessageEncoder<LogEvent> {

  public final InetSocketAddress remoteAddress;

  public LogEventEncoder(InetSocketAddress remoteAddress) {
    this.remoteAddress = remoteAddress;
  }

  @Override
  protected void encode(ChannelHandlerContext ctx, LogEvent msg, List<Object> out)
      throws Exception {
    byte[] file = msg.getLogfile().getBytes(CharsetUtil.UTF_8);
    byte[] msgs = msg.getMsg().getBytes(CharsetUtil.UTF_8);
    ByteBuf buf = ctx.alloc().buffer(file.length + msgs.length + 1);
    buf.writeBytes(file);
    buf.writeByte(LogEvent.SEPARATOR);
    buf.writeBytes(msgs);

    out.add(new DatagramPacket(buf, remoteAddress));
  }
}
