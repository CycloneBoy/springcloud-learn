package com.cycloneboy.springcloud.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler.ServerHandshakeStateEvent;

/**
 * Create by  sl on 2019-09-18 23:22
 */
public class TextWebsocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

  private ChannelGroup group;

  public TextWebsocketFrameHandler(ChannelGroup group) {
    this.group = group;
  }

  @Override
  public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
    if (evt == ServerHandshakeStateEvent.HANDSHAKE_COMPLETE) {
      ctx.pipeline().remove(HttpRequestHandler.class);
      group.writeAndFlush(new TextWebSocketFrame("Client " + ctx.channel() + " joined"));
      group.add(ctx.channel());
    }
  }

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
    group.writeAndFlush(msg.retain());
  }
}
