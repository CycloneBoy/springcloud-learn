package com.cycloneboy.springcloud.domain;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * Create by  sl on 2019-09-29 22:06
 */
@Slf4j
public class UserInfoTest {

  @Test
  public void test01() throws IOException {
    UserInfo userInfo = new UserInfo("welcome to netty", 100);
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
    objectOutputStream.writeObject(userInfo);
    objectOutputStream.flush();
    objectOutputStream.close();

    byte[] bytes = outputStream.toByteArray();
    log.info("The jdk serializable length {}", bytes.length);
    log.info("The byte array serializable length {}", userInfo.codeC().length);


  }

  @Test
  public void test02() throws IOException {
    UserInfo userInfo = new UserInfo("welcome to netty", 100);
    int loop = 1000000;

    ByteArrayOutputStream outputStream = null;
    ObjectOutputStream objectOutputStream = null;
    long start = System.currentTimeMillis();

    for (int i = 0; i < loop; i++) {
      outputStream = new ByteArrayOutputStream();
      objectOutputStream = new ObjectOutputStream(outputStream);
      objectOutputStream.writeObject(userInfo);
      objectOutputStream.flush();
      objectOutputStream.close();
      byte[] bytes = outputStream.toByteArray();

      outputStream.close();
    }

    long end = System.currentTimeMillis();
    log.info("The jdk serializable time : {} ms", (end - start));

    ByteBuffer buffer = ByteBuffer.allocate(1024);
    start = System.currentTimeMillis();

    for (int i = 0; i < loop; i++) {
      byte[] codeC = userInfo.codeC(buffer);
    }

    end = System.currentTimeMillis();

    log.info("The byte array serializable time : {}ms", (end - start));
  }
}
