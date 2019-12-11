package com.cycloneboy.springcloud.goodskill.common.dynamicquery;

import com.cycloneboy.springcloud.goodskill.common.Constants;
import com.cycloneboy.springcloud.goodskill.entity.SuccessKilled;
import java.sql.Timestamp;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Create by  sl on 2019-12-11 13:13
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class DynamicQueryImplTest {

  @Autowired
  private DynamicQuery dynamicQuery;

  @Test
  public void save() {

    //创建订单
    SuccessKilled successKilled = new SuccessKilled();
    successKilled.setSeckillId(1000);
    successKilled.setUserId(33);
    successKilled.setState(Constants.SUCCESS_KILLED_STAT_SUCCESS);
    Timestamp createTime = new Timestamp(new Date().getTime());
    successKilled.setCreateTime(createTime);
    dynamicQuery.save(successKilled);

  }
}