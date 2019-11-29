package com.cycloneboy.springcloud.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Create by  sl on 2019-09-29 23:24
 */
public class MsgpackEncoder extends MessageToByteEncoder<Object> {

  @Override
  protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf)
      throws Exception {

  }
}
