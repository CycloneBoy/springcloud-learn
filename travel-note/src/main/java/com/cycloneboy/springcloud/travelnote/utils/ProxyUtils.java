package com.cycloneboy.springcloud.travelnote.utils;

import com.cycloneboy.springcloud.travelnote.entity.Proxy;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * Create by  sl on 2019-08-03 12:12
 */
@Slf4j
public class ProxyUtils {

    public static final String HOST_IP_URL = "https://tool.lu/ip/";

    /**
     * 批量代理IP有效检测
     *
     * @param proxyInfo
     * @param reqUrl
     */
    public static boolean checkProxyIp(Proxy proxyInfo, String reqUrl) {
        CloseableHttpClient httpClient = null;
        HttpPost httpPost = null;
        String result = "";
        try {

            //设置代理IP、端口、协议（请分别替换）
            HttpHost proxy = new HttpHost(proxyInfo.getIp(), proxyInfo.getPort(), proxyInfo.getProtocol());

            //把代理设置到请求配置
            RequestConfig defaultRequestConfig = RequestConfig.custom()
                    .setConnectTimeout(3000)
                    .setSocketTimeout(3000)
                    .setProxy(proxy).build();


            CookieStore cookieStore = new BasicCookieStore();
            httpClient = HttpClients.custom()
                    .setMaxConnTotal(5)
                    .setDefaultRequestConfig(defaultRequestConfig)
                    .setDefaultCookieStore(cookieStore).build();

            HttpUriRequest httpUriRequest = new HttpGet(reqUrl);


            httpUriRequest.setHeader("User-Agent", RandomUserAgentUtils.getUserAgent());
//            httpUriRequest.setHeader("Host", "www.mafengwo.cn");
//            httpUriRequest.setHeader("Proxy-Connection", "keep-alive");
//            httpUriRequest.setHeader("Pragma", "no-cache");
//            httpUriRequest.setHeader("Cache-Control", "no-cache");
//            httpUriRequest.setHeader("Accept", "application/json, text/javascript}, */*; q=0.01");
//            httpUriRequest.setHeader("Origin", "http,//www.mafengwo.cn");
//            httpUriRequest.setHeader("X-Requested-With", "XMLHttpRequest");
//            httpUriRequest.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
//            httpUriRequest.setHeader("Accept-Encoding", "gzip, deflate");
//            httpUriRequest.setHeader("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8");


            HttpResponse response = httpClient.execute(httpUriRequest);
            int statusCode = response.getStatusLine().getStatusCode();
            log.info("访问成功：" + statusCode);
            if (statusCode == HttpStatus.SC_OK) {
                log.info("访问成功：");
            }

            InputStream content = response.getEntity().getContent();

            BufferedReader br = new BufferedReader(new InputStreamReader(content));
            String line = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            br.close();

            log.info(proxyInfo.getIp() + " - is OK");


        } catch (Exception ex) {
            log.info(ex.getMessage());
            return false;
        }

        return true;

    }

    public static void main(String[] args) {
        Proxy proxy = new Proxy();
        proxy.setIp("58.253.157.172");
        proxy.setPort(9999);
        proxy.setProtocol("HTTPS");
        ProxyUtils.checkProxyIp(proxy, "http://www.baidu.com");


    }
}

