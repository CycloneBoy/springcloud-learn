package com.cycloneboy.springcloud.client;

import static com.cycloneboy.springcloud.common.Constants.DELIMITER_$;

import com.cycloneboy.springcloud.handler.EchoClientHandler;
import com.cycloneboy.springcloud.server.EchoServer;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import java.net.InetSocketAddress;
import lombok.extern.slf4j.Slf4j;

/**
 * Create by  sl on 2019-09-14 21:52
 */
@Slf4j
public class EchoClient {

  public final String host;

  public final int port;

  public EchoClient(String host, int port) {
    this.host = host;
    this.port = port;
  }

  public static void main(String[] args) throws InterruptedException {
    String host = "127.0.0.1";
    int port = 8090;

    if (args.length != 2) {
      log.error("Usage: {} <host> <port>", EchoServer.class.getSimpleName());
    }

//    String host = args[0];
//    int port = Integer.parseInt(args[1]);
    new EchoClient(host, port).start();
  }

  public void start() throws InterruptedException {
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

              ByteBuf delimiter = Unpooled.copiedBuffer(DELIMITER_$.getBytes());
              ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, delimiter));
              ch.pipeline().addLast(new StringDecoder());
              ch.pipeline().addLast(new EchoClientHandler());
            }
          });

      ChannelFuture future = bootstrap.connect().sync();
      future.channel().closeFuture().sync();
    } finally {
      group.shutdownGracefully().sync();
    }
  }
}
