package com.cycloneboy.springcloud.goodskill.service.impl;

import com.cycloneboy.springcloud.common.domain.BaseResponse;
import com.cycloneboy.springcloud.goodskill.common.Constants;
import com.cycloneboy.springcloud.goodskill.entity.Seckill;
import com.cycloneboy.springcloud.goodskill.repository.SeckillRepository;
import com.cycloneboy.springcloud.goodskill.service.CreateHtmlService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import io.netty.util.CharsetUtil;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Create by  sl on 2019-12-10 17:42
 */
@Slf4j
@Service
public class CreateHtmlServiceImpl implements CreateHtmlService {

  private static int corePoolSize = Runtime.getRuntime().availableProcessors();

  //多线程生成静态页面
  private static ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize,
      corePoolSize + 1, 10l,
      TimeUnit.SECONDS, new LinkedBlockingQueue<>(1000));

  @Autowired
  private Configuration configuration;

  @Autowired
  private SeckillRepository seckillRepository;

  @Value("${spring.freemarker.html.path}")
  private String path;

  @Override
  public BaseResponse createAllHtml() {
    List<Seckill> seckillList = seckillRepository.findAll();
    List<Future<String>> resultList = new ArrayList<>();

    for (Seckill seckill : seckillList) {
      resultList.add(executor.submit(new CreateHtml(seckill)));
    }

    for (Future<String> stringFuture : resultList) {
      //打印各个线任务执行的结果，调用future.get() 阻塞主线程，获取异步任务的返回结果
      try {
        log.info(stringFuture.get());
      } catch (InterruptedException e) {
        e.printStackTrace();
      } catch (ExecutionException e) {
        e.printStackTrace();
      }
    }

    return new BaseResponse();
  }

  class CreateHtml implements Callable<String> {

    public Seckill seckill;


    public CreateHtml(Seckill seckill) {
      this.seckill = seckill;
    }

    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public String call() throws Exception {
      // static/template/goods.flt
      Template template = configuration.getTemplate(Constants.GOODS_TEMPLATE_NAME);
      File file = new File(path + seckill.getSeckillId() + ".html");
      OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(file),
          CharsetUtil.UTF_8);
      template.process(seckill, outputStreamWriter);
      return Constants.SUCCESS;
    }
  }

}
