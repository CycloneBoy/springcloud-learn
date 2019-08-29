package com.cycloneboy.springcloud.travelnote.web;

import java.io.File;
import java.util.concurrent.TimeUnit;
import org.junit.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Create by  sl on 2019-08-03 14:58
 */

public class PhantomJsTest {

    @Test
    public void test01() {
        System.setProperty("phantomjs.binary.path", "/usr/local/bin/phantomjs");

        WebDriver driver = new PhantomJSDriver();
        driver.get("http://www.baidu.com");
        System.out.println(driver.getCurrentUrl());
    }

    @Test
    public void verifyFacebookTitle() {
        DesiredCapabilities caps = new DesiredCapabilities();
        ((DesiredCapabilities) caps).setJavascriptEnabled(true);
        ((DesiredCapabilities) caps).setCapability("takesScreenshot", true);
        ((DesiredCapabilities) caps).setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, "/usr/local/bin/phantomjs");
        caps.setJavascriptEnabled(true);
        String[] phantomJsArgs = {"--web-security=no", "--ignore-ssl-errors=yes"};
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, phantomJsArgs);
        WebDriver driver = new PhantomJSDriver(caps);
        driver.get("https://www.facebook.com/");
        System.out.println(driver.getTitle());
        driver.quit();
    }

    @Test
    public void test03() throws InterruptedException {
        //设置必要参数
        DesiredCapabilities dcaps = new DesiredCapabilities();
        //ssl证书支持
        dcaps.setCapability("acceptSslCerts", true);
        //截屏支持
        dcaps.setCapability("takesScreenshot", true);
        //css搜索支持
        dcaps.setCapability("cssSelectorsEnabled", true);
        //js支持
        dcaps.setJavascriptEnabled(true);
        //驱动支持（第二参数表明的是你的phantomjs引擎所在的路径）
        dcaps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
                "/user/local/bin/phantomjs");
        //创建无界面浏览器对象
        PhantomJSDriver driver = new PhantomJSDriver(dcaps);

        //设置隐性等待（作用于全局）
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        long start = System.currentTimeMillis();
        //打开页面
        driver.get("https://juejin.im/post/5bb24bafe51d450e4437fd96");
        Thread.sleep(30 * 1000);
        JavascriptExecutor js = driver;
        for (int i = 0; i < 33; i++) {
            js.executeScript("window.scrollBy(0,1000)");
            //睡眠10s等js加载完成
            Thread.sleep(5 * 1000);
        }
        //指定了OutputType.FILE做为参数传递给getScreenshotAs()方法，其含义是将截取的屏幕以文件形式返回。
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        Thread.sleep(3000);
        //利用FileUtils工具类的copyFile()方法保存getScreenshotAs()返回的文件对象
//        FileUtils.copyFile(srcFile, new File("/Users/hetiantian/Desktop/juejin-01.png"));
        System.out.println("耗时：" + (System.currentTimeMillis() - start) + " 毫秒");
    }


}
