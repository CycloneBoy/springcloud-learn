package com.cycloneboy.springcloud.travelnote.basic;

import com.cycloneboy.springcloud.travelnote.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * Create by  sl on 2019-09-02 21:39
 */
@Slf4j
public class BasicTest {


  @Test
  public void testAuthorUrl() {
    log.info(CommonUtils.buildTravelAuthorUrl("http://www.mafengwo.cn/u/74369556.html"));
    log.info(CommonUtils.buildTravelAuthorUrl("http://www.mafengwo.cn/u/74369556/note.html"));
  }

}
