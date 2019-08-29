package com.cycloneboy.springcloud.travelnote;


import com.cycloneboy.springcloud.travelnote.utils.CrawelUtils;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Create by  sl on 2019-08-03 14:38
 */
@Slf4j
public class TestChromeDriver {


    public static void main(String[] args) throws IOException {

        WebDriver driver = CrawelUtils.getChromeDriver();

        // 让浏览器访问 Baidu
        driver.get("http://www.mafengwo.cn/i/12391442.html");
//        driver.get("http://www.mafengwo.cn/u/75334068/note.html");
//        driver.get("https://www.baidu.com/");
//        driver.get("http://www.mafengwo.cn");

        // 用下面代码也可以实现
//        driver.navigate().to("http://www.baidu.com");
        // 获取 网页的 title
        System.out.println(" Page title is: " + driver.getTitle());
        // 通过 id 找到 input 的 DOM
        WebElement element = driver.findElement(By.xpath("/html"));
        System.out.println(element.getAttribute("outerHTML"));

        // 输入关键字
        element.sendKeys("东鹏瓷砖");
        // 提交 input 所在的 form
        log.info(element.getText());
        // 通过判断 title 内容等待搜索页面加载完毕，间隔秒
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div")));

        // 显示搜索结果页面的 title
        System.out.println(" Page title is: " + driver.getTitle());

        log.info(driver.getPageSource());

        // 关闭浏览器
        driver.quit();
        // 关闭 ChromeDriver 接口
        CrawelUtils.service.stop();
    }


}