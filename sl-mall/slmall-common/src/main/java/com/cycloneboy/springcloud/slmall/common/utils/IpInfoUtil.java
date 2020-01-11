package com.cycloneboy.springcloud.slmall.common.utils;


import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.cycloneboy.springcloud.slmall.common.vo.IpLocate;
import com.google.gson.Gson;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;


/**
 * @author sl
 */
@Slf4j
public class IpInfoUtil {

  /**
   * Mob api
   */
  private static final String APPKEY = "你的appkey";

  private static final String LOCAL_HOST = "0:0:0:0:0:0:0:1";

  /**
   * 获取客户端IP地址
   *
   * @param request 请求
   * @return
   */
  public static String getIp(HttpServletRequest request) {
    String ip = request.getHeader("x-forwarded-for");
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("WL-Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getRemoteAddr();
      if (ip.equals("127.0.0.1")) {
        //根据网卡取本机配置的IP
        InetAddress inet = null;
        try {
          inet = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
          e.printStackTrace();
        }
        ip = inet.getHostAddress();
      }
    }
    // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
    if (ip != null && ip.length() > 15) {
      if (ip.indexOf(",") > 0) {
        ip = ip.substring(0, ip.indexOf(","));
      }
    }
    if (LOCAL_HOST.equals(ip)) {
      ip = "127.0.0.1";
    }
    return ip;
  }

  /**
   * 获取IP返回地理天气信息
   *
   * @param ip ip地址
   * @return
   */
  public String getIpWeatherInfo(String ip) {

    String GET_IP_WEATHER = "http://apicloud.mob.com/v1/weather/ip?key=" + APPKEY + "&ip=";
    if (StrUtil.isNotBlank(ip)) {
      String url = GET_IP_WEATHER + ip;
      String result = HttpUtil.get(url);
      return result;
    }
    return null;
  }

  /**
   * 获取IP返回地理信息
   *
   * @param ip ip地址
   * @return
   */
  public String getIpCity(String ip) {

    String GET_IP_LOCATE = "http://apicloud.mob.com/ip/query?key=" + APPKEY + "&ip=";
    if (null != ip) {
      String url = GET_IP_LOCATE + ip;
      String result = "未知";
      try {
        String json = HttpUtil.get(url, 3000);
        IpLocate locate = new Gson().fromJson(json, IpLocate.class);
        if (("200").equals(locate.getRetCode())) {
          if (StrUtil.isNotBlank(locate.getResult().getProvince())) {
            result =
                locate.getResult().getProvince() + " " + locate.getResult().getCity();
          } else {
            result = locate.getResult().getCountry();
          }
        }
      } catch (Exception e) {
        log.info("获取IP信息失败");
      }
      return result;
    }
    return null;
  }
}
