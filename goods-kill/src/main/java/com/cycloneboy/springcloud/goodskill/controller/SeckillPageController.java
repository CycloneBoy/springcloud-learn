package com.cycloneboy.springcloud.goodskill.controller;

import com.alibaba.fastjson.JSONObject;
import com.cycloneboy.springcloud.common.domain.BaseResponse;
import com.cycloneboy.springcloud.goodskill.common.utils.HttpClientUtil;
import com.cycloneboy.springcloud.goodskill.common.utils.IPUtils;
import com.cycloneboy.springcloud.goodskill.entity.Seckill;
import com.cycloneboy.springcloud.goodskill.service.SeckillService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Create by  sl on 2019-12-10 22:05
 */
@Slf4j
@Api(tags = "秒杀商品")
@RestController
@RequestMapping("/seckillPage")
public class SeckillPageController {

  @Autowired
  private SeckillService seckillService;

  @Autowired
  private HttpClientUtil httpClient;

  @Value("${qq.captcha.url}")
  private String url;

  @Value("${qq.captcha.aid}")
  private String aid;

  @Value("${qq.captcha.AppSecretKey}")
  private String appSecretKey;

  @ApiOperation(value = "秒杀商品列表", nickname = "cycloneboy")
  @PostMapping("/list")
  public BaseResponse list() {
    List<Seckill> seckillList = seckillService.getSeckillList();
    return new BaseResponse(seckillList);
  }

  /**
   * 秒杀场景一 第一次秒杀
   *
   * @param ticket
   * @param randstr
   * @param request
   * @return
   */
  @PostMapping("/startSeckill")
  public BaseResponse starttSeckill(String ticket, String randstr, HttpServletRequest request) {
    HttpMethod method = HttpMethod.GET;
    LinkedMultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
    params.add("Action", "DescribeCaptchaResult");
    params.add("Version", "2019-07-22");
    params.add("CaptchaType", 9);
    params.add("CaptchaAppId", aid);
    params.add("AppSecretKey", appSecretKey);
    //验证码返回给用户的票据
    params.add("Ticket", ticket);
    //验证票据需要的随机字符串
    params.add("Randstr", randstr);
    params.add("UserIP", IPUtils.getIpAddr());

    String msg = httpClient.client(url, method, params);

    /**
     * response: 1:验证成功，0:验证失败，100:AppSecretKey参数校验错误[required]
     * evil_level:[0,100]，恶意等级[optional]
     * err_msg:验证错误信息[optional]
     */
    //{"response":"1","evil_level":"0","err_msg":"OK"}
    JSONObject json = JSONObject.parseObject(msg);
    log.info("json:{}", json.toJSONString());
//    String response = (String) json.get("Response");
//    log.info("response:{}", response);
    if (!json.isEmpty()) {
      //进入队列、假数据而已
//      Destination destination = new ActiveMQQueue("seckill.queue");
//      activeMQSender.sendChannelMess(destination, 1000 + ";" + 1);
      return BaseResponse.ok();
    } else {
      return BaseResponse.failed("验证失败");
    }
  }
}
