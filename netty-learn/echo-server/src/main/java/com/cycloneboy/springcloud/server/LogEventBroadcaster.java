package com.cycloneboy.springcloud.server;

import com.cycloneboy.springcloud.config.LogEventEncoder;
import com.cycloneboy.springcloud.domain.LogEvent;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import lombok.extern.slf4j.Slf4j;

/**
 * Create by  sl on 2019-09-19 20:15
 */
@Slf4j
public class LogEventBroadcaster {

  private final EventLoopGroup group;
  private final Bootstrap bootstrap;
  private File file;

  public LogEventBroadcaster(InetSocketAddress address, File file) {
    group = new NioEventLoopGroup();
    bootstrap = new Bootstrap();
    bootstrap.group(group)
        .channel(NioDatagramChannel.class)
        .option(ChannelOption.SO_BROADCAST, true)
        .handler(new LogEventEncoder(address));
    this.file = file;
  }

  public void run() throws IOException, InterruptedException {
    Channel channel = bootstrap.bind(0).sync().channel();
    long pointer = 0;
    for (; ; ) {
      long len = file.length();
      log.info("file length:{} ", len);
      if (len < pointer) {
        pointer = len;
      } else {
        RandomAccessFile raf = new RandomAccessFile(file, "r");
        raf.seek(pointer);
        String line;
        while ((line = raf.readLine()) != null) {
          channel.writeAndFlush(new LogEvent(null, file.getAbsolutePath(), line, -1));
        }

        pointer = raf.getFilePointer();
        raf.close();
      }

      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        Thread.interrupted();
        break;
      }
    }
  }

  public void stop() {
    group.shutdownGracefully();
  }

  public static void main(String[] args) {
    if (args.length != 2) {
      log.error("Usage <port> <file>");
    }
    LogEventBroadcaster broadcaster = new LogEventBroadcaster(
        new InetSocketAddress("255.255.255.255", Integer.parseInt(args[0])), new File(args[1]));

    try {
      broadcaster.run();
    } catch (InterruptedException | IOException e) {
      e.printStackTrace();
    } finally {
      broadcaster.stop();
    }


  }
}
