package com.cycloneboy.springcloud.server;

import static com.cycloneboy.springcloud.common.Constants.DELIMITER_$;

import com.cycloneboy.springcloud.handler.EchoServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import java.net.InetSocketAddress;
import lombok.extern.slf4j.Slf4j;

/**
 * Create by  sl on 2019-09-14 21:29
 */
@Slf4j
public class EchoServer {

  private final int port;

  public EchoServer(int port) {
    this.port = port;
  }

  public static void main(String[] args) throws InterruptedException {
    int port = 8090;
    if (args.length != 1) {
      log.error("Usage: {} <port>", EchoServer.class.getSimpleName());
    }

//    int port = Integer.parseInt(args[0]);

    new EchoServer(port).start();

  }

  public void start() throws InterruptedException {
    final EchoServerHandler echoServerHandler = new EchoServerHandler();
    EventLoopGroup group = new NioEventLoopGroup();

    try {
      ServerBootstrap bootstrap = new ServerBootstrap();
      bootstrap.group(group)
          .channel(NioServerSocketChannel.class)
          .option(ChannelOption.SO_BACKLOG, 100)
          .handler(new LoggingHandler(LogLevel.INFO))
          .localAddress(new InetSocketAddress(port))
          .childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
              // 绑定业务逻辑
              ByteBuf delimiter = Unpooled.copiedBuffer(DELIMITER_$.getBytes());
              ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, delimiter));
//              ch.pipeline().addLast(new FixedLengthFrameDecoder(20));
              ch.pipeline().addLast(new StringDecoder());
              ch.pipeline().addLast(echoServerHandler);
            }
          });

      ChannelFuture future = bootstrap.bind().sync();
      future.channel().closeFuture().sync();
    } finally {
      group.shutdownGracefully().sync();
    }

  }
}
