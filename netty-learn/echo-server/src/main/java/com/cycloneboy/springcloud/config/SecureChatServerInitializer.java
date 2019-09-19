package com.cycloneboy.springcloud.config;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;
import javax.net.ssl.SSLEngine;

/**
 * Create by  sl on 2019-09-19 19:22
 */
public class SecureChatServerInitializer extends ChatServerInitializer {

  private final SslContext context;

  public SecureChatServerInitializer(ChannelGroup group, SslContext context) {
    super(group);
    this.context = context;
  }

  @Override
  protected void initChannel(Channel ch) throws Exception {
    super.initChannel(ch);

    SSLEngine sslEngine = context.newEngine(ch.alloc());
    sslEngine.setUseClientMode(false);

    ch.pipeline().addLast(new SslHandler(sslEngine));
  }
}
