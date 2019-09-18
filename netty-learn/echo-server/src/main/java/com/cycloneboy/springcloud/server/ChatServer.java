package com.cycloneboy.springcloud.server;

import com.cycloneboy.springcloud.EchoServer;
import com.cycloneboy.springcloud.config.ChatServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.ImmediateEventExecutor;
import java.net.InetSocketAddress;
import lombok.extern.slf4j.Slf4j;

/**
 * Create by  sl on 2019-09-18 23:36
 */
@Slf4j
public class ChatServer {

  private final ChannelGroup channelGroup = new DefaultChannelGroup(
      ImmediateEventExecutor.INSTANCE);
  private final static EventLoopGroup group = new NioEventLoopGroup();
  private Channel channel;

  public ChannelFuture start(InetSocketAddress address) throws InterruptedException {

    ServerBootstrap bootstrap = new ServerBootstrap();
    bootstrap.group(group)
        .channel(NioServerSocketChannel.class)
        .childHandler(createInitializer(channelGroup));

    ChannelFuture future = bootstrap.bind(address);
    future.syncUninterruptibly();
    channel = future.channel();
    return future;


  }

  private ChannelInitializer<Channel> createInitializer(ChannelGroup channelGroup) {
    return new ChatServerInitializer(channelGroup);
  }

  public void destroy() {
    if (channel != null) {
      channel.close();
    }

    channelGroup.close();
    group.shutdownGracefully();
  }

  public static void main(String[] args) throws InterruptedException {
    if (args.length != 1) {
      log.error("Usage: {} <port>", EchoServer.class.getSimpleName());
      System.exit(1);
    }

    int port = Integer.parseInt(args[0]);
    final ChatServer endPoint = new ChatServer();
    ChannelFuture future = endPoint.start(
        new InetSocketAddress(port));

    Runtime.getRuntime().addShutdownHook(new Thread(endPoint::destroy));
    future.channel().closeFuture().syncUninterruptibly();


  }
}
