package com.cycloneboy.springcloud.travelnote.web;

import com.cycloneboy.springcloud.travelnote.utils.CrawelUtils;
import java.io.IOException;
import java.util.List;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Create by  sl on 2019-08-03 17:58
 */
public class ChromeTest {

    @Test
    public void test01() throws IOException {

        WebDriver driver = CrawelUtils.getChromeDriver();
        //获取一个导航窗口
        WebDriver.Navigation navigation = driver.navigate();

        //指定登陆页面
        String path = "http://angularjs.cn/";
        //加载到指定url
        navigation.to(path);

        try {
            /*//获取渲染页面源代码字符串
            String pageSource = driver.getPageSource();
            System.out.println(pageSource.toString());*/

            /**
             * 下面通过元素选择器对获取到的页面进行字段抽取，遍历打印出需要的数据。
             */
            List<WebElement> elements = driver.findElements(By.xpath("//div[@class='media-header']"));
            for (WebElement element : elements) {
                System.out.println(element.getText());
            }
            driver.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
