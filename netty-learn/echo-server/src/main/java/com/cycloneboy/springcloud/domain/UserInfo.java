package com.cycloneboy.springcloud.domain;

import java.io.Serializable;
import java.nio.ByteBuffer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Create by  sl on 2019-09-29 21:51
 */
@AllArgsConstructor
@Slf4j
@NoArgsConstructor
@Data
public class UserInfo implements Serializable {

  private static final long serialVersionUID = 4702755304691173330L;

  private String userName;

  private int userId;

  public byte[] codeC() {
    ByteBuffer buffer = ByteBuffer.allocate(1024);
    byte[] userNameBytes = this.userName.getBytes();
    buffer.putInt(userNameBytes.length);
    buffer.put(userNameBytes);
    buffer.putInt(userId);
    buffer.flip();

    userNameBytes = null;
    byte[] result = new byte[buffer.remaining()];
    buffer.get(result);
    return result;

  }

  public byte[] codeC(ByteBuffer buffer) {
    buffer.clear();
    byte[] userNameBytes = this.userName.getBytes();
    buffer.putInt(userNameBytes.length);
    buffer.put(userNameBytes);
    buffer.putInt(userId);
    buffer.flip();

    userNameBytes = null;
    byte[] result = new byte[buffer.remaining()];
    buffer.get(result);
    return result;

  }
}
