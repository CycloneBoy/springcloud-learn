package com.cycloneboy.springcloud.server;

import com.cycloneboy.springcloud.handler.TimeServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import java.net.InetSocketAddress;

/**
 * Create by  sl on 2019-09-28 18:41
 */
public class NettyTimeServer {

  public static void main(String[] args) throws InterruptedException {
    int port = 8089;

    if (args != null && args.length > 0) {
      port = Integer.parseInt(args[0]);
    }

    new NettyTimeServer().bind(port);
  }

  public void bind(int port) throws InterruptedException {

    EventLoopGroup bossGroup = new NioEventLoopGroup();
    EventLoopGroup workerGroup = new NioEventLoopGroup();

    try {
      ServerBootstrap bootstrap = new ServerBootstrap();
      bootstrap.group(bossGroup, workerGroup)
          .channel(NioServerSocketChannel.class)
          .option(ChannelOption.SO_BACKLOG, 1024)
          .localAddress(new InetSocketAddress(port))
          .childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
              // 绑定业务逻辑
              ch.pipeline().addLast(new TimeServerHandler());
            }
          });

      ChannelFuture future = bootstrap.bind().sync();
      future.channel().closeFuture().sync();
    } finally {
      bossGroup.shutdownGracefully().sync();
      workerGroup.shutdownGracefully().sync();
    }

  }
}
