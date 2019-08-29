package com.cycloneboy.springcloud.travelnote.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Create by  sl on 2019-08-02 21:56
 */
@Slf4j
public class MafengwoLoginUtils {

    public static final String MAFENGWO_LOGIN_URL = "https://passport.mafengwo.cn/login/";
    public static final String MAFENGWO_AUTHOR_HOME_URL = "http://www.mafengwo.cn/home/ajax_index.php?act=getNoteMapPointer&uid=83338921";
    public static final String MAFENGWO_HOEM_URL = "http://www.mafengwo.cn/u/83338921.html";

    private static void loginedPager() throws IOException,
            ClientProtocolException {

        HttpUriRequest httpUriRequest = new HttpPost(MAFENGWO_LOGIN_URL);


        httpUriRequest.setHeader("Referer", "http,//www.mafengwo.cn/travel-scenic-spot/mafengwo/10460.html");
        httpUriRequest.setHeader("User-Agent", RandomUserAgentUtils.getUserAgent());
        httpUriRequest.setHeader("Host", "www.mafengwo.cn");
        httpUriRequest.setHeader("Proxy-Connection", "keep-alive");
        httpUriRequest.setHeader("Pragma", "no-cache");
        httpUriRequest.setHeader("Cache-Control", "no-cache");
        httpUriRequest.setHeader("Accept", "application/json, text/javascript}, */*; q=0.01");
        httpUriRequest.setHeader("Origin", "http,//www.mafengwo.cn");
        httpUriRequest.setHeader("X-Requested-With", "XMLHttpRequest");
        httpUriRequest.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        httpUriRequest.setHeader("Accept-Encoding", "gzip, deflate");
        httpUriRequest.setHeader("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8");


    }


    public static String login(Map<String, String> map, String charset) {
        CloseableHttpClient httpClient = null;
        HttpPost httpPost = null;
        String result = null;
        try {
            CookieStore cookieStore = new BasicCookieStore();
            httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();

            HttpUriRequest httpUriRequest = new HttpPost(MAFENGWO_LOGIN_URL);


            httpUriRequest.setHeader("Referer", "http,//www.mafengwo.cn/travel-scenic-spot/mafengwo/10460.html");
            httpUriRequest.setHeader("User-Agent", RandomUserAgentUtils.getUserAgent());
            httpUriRequest.setHeader("Host", "www.mafengwo.cn");
            httpUriRequest.setHeader("Proxy-Connection", "keep-alive");
            httpUriRequest.setHeader("Pragma", "no-cache");
            httpUriRequest.setHeader("Cache-Control", "no-cache");
            httpUriRequest.setHeader("Accept", "application/json, text/javascript}, */*; q=0.01");
            httpUriRequest.setHeader("Origin", "http,//www.mafengwo.cn");
            httpUriRequest.setHeader("X-Requested-With", "XMLHttpRequest");
            httpUriRequest.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            httpUriRequest.setHeader("Accept-Encoding", "gzip, deflate");
            httpUriRequest.setHeader("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8");


            httpPost = new HttpPost(MAFENGWO_LOGIN_URL);


            List<NameValuePair> list = new ArrayList<NameValuePair>();
            Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> elem = (Map.Entry<String, String>) iterator.next();
                list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
            }
            if (list.size() > 0) {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
                httpPost.setEntity(entity);
            }
            HttpResponse response = httpClient.execute(httpUriRequest);
            log.info("code:" + response.getStatusLine().getStatusCode());
            log.info(response.getEntity().getContent().toString());
            String JSESSIONID = null;
            String cookie_user = null;
            List<Cookie> cookies = cookieStore.getCookies();
            for (int i = 0; i < cookies.size(); i++) {
                System.out.println(cookies.get(i).getName() + "- " + cookies.get(i).getValue());

                if (cookies.get(i).getName().equals("JSESSIONID")) {
                    JSESSIONID = cookies.get(i).getValue();
                }
                if (cookies.get(i).getName().equals("cookie_user")) {
                    cookie_user = cookies.get(i).getValue();
                }
            }
            if (cookie_user != null) {
                result = JSESSIONID;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }


    public static String geturl() {
        CloseableHttpClient httpClient = null;
        HttpPost httpPost = null;
        String result = "";
        try {

            //设置代理IP、端口、协议（请分别替换）
            HttpHost proxy = new HttpHost("117.91.133.111", 9999, "http");

            //把代理设置到请求配置
            RequestConfig defaultRequestConfig = RequestConfig.custom().setProxy(proxy).build();


            CookieStore cookieStore = new BasicCookieStore();
            httpClient = HttpClients.custom()
                    .setDefaultRequestConfig(defaultRequestConfig)
                    .setDefaultCookieStore(cookieStore).build();

            HttpUriRequest httpUriRequest = new HttpGet(MAFENGWO_AUTHOR_HOME_URL);


            httpUriRequest.setHeader("Referer", "http,//www.mafengwo.cn/travel-scenic-spot/mafengwo/10460.html");
            httpUriRequest.setHeader("User-Agent", RandomUserAgentUtils.getUserAgent());
            httpUriRequest.setHeader("Host", "www.mafengwo.cn");
            httpUriRequest.setHeader("Proxy-Connection", "keep-alive");
            httpUriRequest.setHeader("Pragma", "no-cache");
            httpUriRequest.setHeader("Cache-Control", "no-cache");
            httpUriRequest.setHeader("Accept", "application/json, text/javascript}, */*; q=0.01");
            httpUriRequest.setHeader("Origin", "http,//www.mafengwo.cn");
            httpUriRequest.setHeader("X-Requested-With", "XMLHttpRequest");
            httpUriRequest.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            httpUriRequest.setHeader("Accept-Encoding", "gzip, deflate");
            httpUriRequest.setHeader("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8");


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

            System.out.println(result);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    // 登陆方法
    public static void chromeGet(String url) throws IOException {


        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(url);
        log.info(driver.getTitle());
        log.info(driver.getPageSource());
//        // 定义验证码变量
//        String verify = null;
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        driver.findElement(By.xpath("//div[@class='layui-input-block']/input")).sendKeys("账号");
//        driver.findElement(By.id("userPwd")).sendKeys("密码");
//        //获取点击按钮
//        WebElement element = driver.findElement(By.xpath("//button[@class='layui-btn layui-btn-fluid']"));
//        //验证码获取破解....之后奉上
//
//        //模拟点击
//        element.click();
//        //很重要的一步获取登陆后的cookies
////        cookies = driver.manage().getCookies();
        driver.close();
    }


    public static void main(String[] args) throws IOException {
        //
        Map<String, String> body = new HashMap<>();
        body.put("passport", "534634799@qq.com");
        body.put("password", "shenglei1992");
        MafengwoLoginUtils.login(body, null);
//        log.info(MafengwoLoginUtils.geturl());
//        MafengwoLoginUtils.chromeGet();

        MafengwoLoginUtils.chromeGet(MAFENGWO_HOEM_URL);
    }
}

