package com.cycloneboy.springcloud.config;

import com.cycloneboy.springcloud.domain.LogEvent;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.CharsetUtil;
import java.util.List;

/**
 * Create by  sl on 2019-09-19 20:37
 */
public class LogEventDecoder extends MessageToMessageDecoder<DatagramPacket> {


  @Override
  protected void decode(ChannelHandlerContext ctx, DatagramPacket msg, List<Object> out)
      throws Exception {

    ByteBuf buf = msg.content();
    int index = buf.indexOf(0, buf.readableBytes(), LogEvent.SEPARATOR);

    String fileName = buf.slice(0, index).toString(CharsetUtil.UTF_8);

    String logMsg = buf.slice(index + 1, buf.readableBytes()).toString(CharsetUtil.UTF_8);

    LogEvent logEvent = new LogEvent(msg.sender(), fileName, logMsg, System.currentTimeMillis());
    out.add(logEvent);

  }
}
