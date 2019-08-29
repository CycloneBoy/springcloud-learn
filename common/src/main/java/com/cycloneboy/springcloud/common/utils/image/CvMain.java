package com.cycloneboy.springcloud.common.utils.image;

import static com.cycloneboy.springcloud.common.common.Constants.FILE_IMAGE_DESTINATION_DIR_2;
import static com.cycloneboy.springcloud.common.common.Constants.IMAGE_URL_1;

import lombok.extern.slf4j.Slf4j;

/**
 * Create by  sl on 2019-08-27 23:50
 */
@Slf4j
public class CvMain {

  public static void main(String[] args) {
    test1();
    test2();
  }

  public static void test1() {
    String filename = IMAGE_URL_1.substring(IMAGE_URL_1.lastIndexOf("/"));
    log.info(filename);
  }

  public static void test2() {
    String filename =
        FILE_IMAGE_DESTINATION_DIR_2 + IMAGE_URL_1.substring(IMAGE_URL_1.lastIndexOf("/"));
    Smoother.smooth(filename);
  }
}
