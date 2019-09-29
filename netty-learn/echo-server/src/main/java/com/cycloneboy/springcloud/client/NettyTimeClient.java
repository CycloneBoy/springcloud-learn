package com.cycloneboy.springcloud.client;

import com.cycloneboy.springcloud.handler.TimeClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.net.InetSocketAddress;
import lombok.extern.slf4j.Slf4j;

/**
 * Create by  sl on 2019-09-29 19:56
 */
@Slf4j
public class NettyTimeClient {

  public static void main(String[] args) throws InterruptedException {
    String host = "127.0.0.1";
    int port = 8089;

    if (args.length == 2) {
      host = args[0];
      port = Integer.parseInt(args[1]);
    } else {
      log.error("Usage: {} default <host> <port>", NettyTimeClient.class.getSimpleName());

    }

    new NettyTimeClient().connect(host, port);
  }

  public void connect(String host, int port) throws InterruptedException {
    EventLoopGroup group = new NioEventLoopGroup();

    try {
      Bootstrap bootstrap = new Bootstrap();
      bootstrap.group(group)
          .channel(NioSocketChannel.class)
          .option(ChannelOption.TCP_NODELAY, true)
          .remoteAddress(new InetSocketAddress(host, port))
          .handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
              ch.pipeline().addLast(new TimeClientHandler());
            }
          });

      ChannelFuture future = bootstrap.connect().sync();
      future.channel().closeFuture().sync();
    } finally {
      group.shutdownGracefully().sync();
    }
  }

}
