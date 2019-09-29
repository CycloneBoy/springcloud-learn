package com.cycloneboy.springcloud.server;

import com.cycloneboy.springcloud.config.SecureChatServerInitializer;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import java.net.InetSocketAddress;
import java.security.cert.CertificateException;
import javax.net.ssl.SSLException;
import lombok.extern.slf4j.Slf4j;

/**
 * Create by  sl on 2019-09-19 19:26
 */
@Slf4j
public class SecurtyChatServer extends ChatServer {

  private final SslContext context;

  public SecurtyChatServer(SslContext context) {
    this.context = context;
  }

  @Override
  protected ChannelInitializer<Channel> createInitializer(ChannelGroup channelGroup) {
    return new SecureChatServerInitializer(channelGroup, context);
  }

  public static void main(String[] args)
      throws SSLException, CertificateException, InterruptedException {
    if (args.length != 1) {
      log.error("Usage: {} <port>", EchoServer.class.getSimpleName());
      System.exit(1);
    }

    int port = Integer.parseInt(args[0]);

    SelfSignedCertificate cert = new SelfSignedCertificate();
//    SslContext context = SslContextBuilder.forServer(cert.certificate(), cert.privateKey()).build();
    SslContext context = SslContext.newServerContext(cert.certificate(), cert.privateKey());

    final SecurtyChatServer endPoint = new SecurtyChatServer(context);
    ChannelFuture future = endPoint.start(
        new InetSocketAddress(port));

    Runtime.getRuntime().addShutdownHook(new Thread(endPoint::destroy));
    future.channel().closeFuture().syncUninterruptibly();
  }
}
