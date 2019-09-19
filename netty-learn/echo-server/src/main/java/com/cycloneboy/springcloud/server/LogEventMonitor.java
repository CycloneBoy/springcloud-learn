package com.cycloneboy.springcloud.server;

import com.cycloneboy.springcloud.config.LogEventDecoder;
import com.cycloneboy.springcloud.handler.LogEventHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import java.net.InetSocketAddress;
import lombok.extern.slf4j.Slf4j;

/**
 * Create by  sl on 2019-09-19 20:46
 */
@Slf4j
public class LogEventMonitor {

  private final EventLoopGroup group;
  private final Bootstrap bootstrap;

  public LogEventMonitor(InetSocketAddress address) {
    group = new NioEventLoopGroup();
    bootstrap = new Bootstrap();
    bootstrap.group(group)
        .channel(NioDatagramChannel.class)
        .option(ChannelOption.SO_BROADCAST, true)
        .handler(new ChannelInitializer<Channel>() {
          @Override
          protected void initChannel(Channel ch) throws Exception {
            ChannelPipeline pipeline = ch.pipeline();
            pipeline.addLast(new LogEventDecoder())
                .addLast(new LogEventHandler());
          }
        }).localAddress(address);

  }


  public Channel bind() {
    return bootstrap.bind().syncUninterruptibly().channel();
  }

  public void stop() {
    group.shutdownGracefully();
  }

  public static void main(String[] args) {
    if (args.length != 1) {
      log.error("Usage LogEventMonitor : <port>");
      return;
    }

    LogEventMonitor logEventMonitor = new LogEventMonitor(
        new InetSocketAddress(Integer.parseInt(args[0])));
    try {
      Channel channel = logEventMonitor.bind();

      log.info("LogEventMonitor is running");

      channel.closeFuture().sync();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      logEventMonitor.stop();
    }
  }
}
