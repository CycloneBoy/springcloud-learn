package com.cycloneboy.springcloud.mafengwo;

import org.junit.Test;

/**
 * Create by  sl on 2019-08-25 23:31
 */
public class CommonTest {

  @Test
  public void test01() {
    String fileName = "http://p1-q.mafengwo.net/s14/M00/99/AA/wKgE2l0hheeAT9ExAA0LHjR-qx4525.jpg";
    System.out.println(fileName.substring(fileName.lastIndexOf(".")));

  }

}
