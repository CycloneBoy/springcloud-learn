package com.cycloneboy.springcloud.travelnote.utils;

import static com.cycloneboy.springcloud.travelnote.common.Constants.MAFENGWO_PHOTO_SCENERY_URL_REGEX;

import com.cycloneboy.springcloud.common.entity.TravelNoteDetail;
import com.cycloneboy.springcloud.travelnote.domain.Note.AuthorAndNoteList;
import com.cycloneboy.springcloud.travelnote.entity.NoteAuthor;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

/**
 * Create by  sl on 2019-07-31 23:59
 */
@Slf4j
public class CrawelUtils {

    public static ChromeDriverService service;

    /**
     * 开始爬去URL
     */
    private String startUrl;


    public static Site getSite() {
        Site site = Site.me().setRetryTimes(3).setSleepTime(100)
                //添加cookie之前一定要先设置主机地址，否则cookie信息不生效
                .setDomain("http://www.mafengwo.cn")
                //添加抓包获取的cookie信息
                .addCookie("__jsluid_h", "c410509a622d04461270e4e3bda23997")
                .addCookie("mfw_uuid", "5d3d7282-ca3e-843b-9988-ed49f7452ce3")
                .addCookie("oad_n", "a%3A3%3A%7Bs%3A3%3A%22oid%22%3Bi%3A1029%3Bs%3A2%3A%22dm%22%3Bs%3A15%3A%22www.mafengwo.cn%22%3Bs%3A2%3A%22ft%22%3Bs%3A19%3A%222019-07-28+18%3A01%3A38%22%3B%7D")
                .addCookie("__mfwc", "direct")
                .addCookie("uva", "s%3A91%3A%22a%3A3%3A%7Bs%3A2%3A%22lt%22%3Bi%3A1564308099%3Bs%3A10%3A%22last_refer%22%3Bs%3A23%3A%22http%3A%2F%2Fwww.mafengwo.cn%2F%22%3Bs%3A5%3A%22rhost%22%3BN%3B%7D%22%3B")
                .addCookie("__mfwurd", "a%3A3%3A%7Bs%3A6%3A%22f_time%22%3Bi%3A1564308099%3Bs%3A9%3A%22f_rdomain%22%3Bs%3A15%3A%22www.mafengwo.cn%22%3Bs%3A6%3A%22f_host%22%3Bs%3A3%3A%22www%22%3B%7D")
                .addCookie("__mfwuuid", "5d3d7282-ca3e-843b-9988-ed49f7452ce3")
                .addCookie("UM_distinctid", "16c38075280829-03423db5c72b4d-3f71045b-1fa400-16c38075281976")
                .addCookie("mafengwo", "03d512cebb1d3f8def099843dc67ccb8_33736599_5d3d728dd476c6.39300375_5d3d728dd476f2.77290633")
                .addCookie("__omc_chl", "")
                .addCookie("__omc_r", "")
                .addCookie("PHPSESSID", "03eitbfun20qpbb203884l5e66")
                .addCookie("mfw_uid", "33736599")
                .addCookie("Hm_lvt_8288b2ed37e5bc9b4c9f7008798d2de0", "1564491449,1564585557,1564674144,1564747680")
                .addCookie("__jsl_clearance", "1564753650.363|0|OOyJUhvrbbMlmUdtbUhyaTvVCRA%3D")
                .addCookie("CNZZDATA30065558", "cnzz_eid%3D65657079-1564306354-http%253A%252F%252Fwww.mafengwo.cn%252F%26ntime%3D1564750277")
                .addCookie("uol_throttle", "33736599")
                .addCookie("__mfwb", "5e733b6329be.1.direct")
                .addCookie("__mfwa", "1564308099297.51694.14.1564747680196.1564756599972")
                .addCookie("__mfwlv", "1564756599")
                .addCookie("__mfwvn", "9")
                .addCookie("__mfwlt", "1564756599")
                .addCookie("Hm_lpvt_8288b2ed37e5bc9b4c9f7008798d2de0", "156475660")
                //添加请求头，有些网站会根据请求头判断该请求是由浏览器发起还是由爬虫发起的

                .addHeader("User-Agent",
                        "ozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.80 Safari/537.36 Core/1.47.516.400 QQBrowser/9.4.8188.400")
                .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                .addHeader("Accept-Encoding", "gzip, deflate, sdch")
                .addHeader("Accept-Language", "zh-CN,zh;q=0.8")
                .addHeader("Connection", "keep-alive")
                .addHeader("Referer", "http://www.mafengwo.cn");
        return site;
    }


    public static WebDriver getChromeDriver() throws IOException {
//        System.setProperty("webdriver.chrome.driver", "C:/Users/sunlc/AppData/Local/Google/Chrome/Application/chrome.exe");
        // 创建一个 ChromeDriver 的接口，用于连接 Chrome（chromedriver.exe 的路径可以任意放置，只要在newFile（）的时候写入你放的路径即可）
        service = new ChromeDriverService.Builder()
                .usingDriverExecutable(new File("/usr/local/bin/chromedriver"))
                .usingAnyFreePort()
                .build();
        service.start();
        // 创建一个 Chrome 的浏览器实例
        return new RemoteWebDriver(service.getUrl(), DesiredCapabilities.chrome());
    }


    public static PhantomJSDriver getPhantomJSDriver() {
        //设置必要参数
        DesiredCapabilities dcaps = new DesiredCapabilities();
        //ssl证书支持
        dcaps.setCapability("acceptSslCerts", true);
        //截屏支持
        dcaps.setCapability("takesScreenshot", false);
        //css搜索支持
        dcaps.setCapability("cssSelectorsEnabled", true);
        //js支持
        dcaps.setJavascriptEnabled(true);
        //驱动支持
        dcaps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, "/usr/local/bin/phantomjs");

        PhantomJSDriver driver = new PhantomJSDriver(dcaps);
        return driver;
    }


    public static String getHtmlFromUrl(String url) throws IOException {
        WebDriver driver = getChromeDriver();

        // 让浏览器访问 Baidu
        driver.get(url);
//        driver.get("http://www.mafengwo.cn/u/75334068/note.html");
//        driver.get("https://www.baidu.com/");
//        driver.get("http://www.mafengwo.cn");

        // 用下面代码也可以实现
//        driver.navigate().to("http://www.baidu.com");
        // 获取 网页的 title
        System.out.println(" Page title is: " + driver.getTitle());
        // 通过 id 找到 input 的 DOM
        WebElement element = driver.findElement(By.xpath("/html"));
        log.info(element.getAttribute("outerHTML"));

        // 输入关键字
        element.sendKeys("东鹏瓷砖");
        // 提交 input 所在的 form
        log.info(element.getText());
        // 通过判断 title 内容等待搜索页面加载完毕，间隔秒
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div")));


        // 显示搜索结果页面的 title
        log.info(" Page title is: " + driver.getTitle());

        String pageDate = driver.getPageSource();
        //        log.info(pageDate);
        log.info("页面抓取完毕：" + url);
//        log.info(pageDate);

        // 关闭浏览器
        driver.quit();
        // 关闭 ChromeDriver 接口
        service.stop();

        return pageDate;
    }


    public static String getHtmlNoteListFromUrl(String url, int pageNumber) throws IOException {
        WebDriver driver = getChromeDriver();

        // 让浏览器访问 Baidu
        driver.get(url);

        // 通过判断 title 内容等待搜索页面加载完毕，间隔秒
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div")));

        // 抓取下一页
        WebElement element = driver.findElement(By.xpath("//div[@class='m-pagination _j_pager_content']/a[" + pageNumber + "]"));

        // 输入关键字
        element.click();

        WebElement element2 = driver.findElement(By.xpath("//div[@class='m-pagination _j_pager_content']"));
        WebDriverWait wait2 = new WebDriverWait(driver, 10);
        wait2.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div")));

        // 显示搜索结果页面的 title
        log.info(" Page title is: " + driver.getTitle());

        String pageDate = driver.getPageSource();
        //        log.info(pageDate);
        log.info("页面抓取完毕：" + url);
//        log.info(pageDate);

        // 关闭浏览器
        driver.quit();
        // 关闭 ChromeDriver 接口
        service.stop();

        return pageDate;
    }

    /**
     * 从游记主页提取游记的主要内容
     *
     * @param pageSource
     */
    public static TravelNoteDetail extractNoteHtml(String pageSource) {
        Html html = new Html(pageSource);

        String authorUrl = html.xpath("//div[@class='person']/a[@class='per_pic']/@href").toString();

        String noteName = html.xpath("//div[@class='view_info']/div[@class='vi_con']/h1/text()").toString();
        String upNumber = html.xpath("//div[@class='ding _j_ding_father']/div/text()").toString();
        String noteId = html.xpath("//div[@class='ding _j_ding_father']/a/@data-iid").toString();

        String content = html.xpath("//div[@class='_j_content_box']/tidyText()").toString().trim().replace(" ", "");

        String vcTime = html.xpath("//div[@class='vc_time']/span[@class='time']/text()").toString();
        String viewAndCommentCount = html.xpath("//div[@class='vc_time']/span[2]/text()").toString();

        String collectNumber = html.xpath("//div[@class='bs_collect']/a/span/text()").toString();
        String shareNumber = html.xpath("//div[@class='bs_share']/a/span/text()").toString();

        String wordTotalNumber = html.xpath("//div[@class='vc_total _j_help_total']/span/text()").toString();
        String imageTotalNumber = html.xpath("//div[@class='vc_total _j_help_total']/span[2]/text()").toString();
        String peopleTotalNumber = html.xpath("//div[@class='vc_total _j_help_total']/b[@class='_j_total_person']/text()").toString();
        String destination = html.xpath("//div[@class='vc_total _j_help_total']/b[@class='_j_total_mdd']/text()").toString();


        String travelTime = html.xpath("//div[@class='tarvel_dir_list clearfix']/ul/li[@class='time']/text()").toString();
        String travelDay = html.xpath("//div[@class='tarvel_dir_list clearfix']/ul/li[@class='day']/text()").toString();
        String travelPeople = html.xpath("//div[@class='tarvel_dir_list clearfix']/ul/li[@class='people']/text()").toString();
        String travelCost = html.xpath("//div[@class='tarvel_dir_list clearfix']/ul/li[@class='cost']/text()").toString();

        List<String> imageList = html.links().regex(MAFENGWO_PHOTO_SCENERY_URL_REGEX).all();
//        imageList.forEach(url -> {
//            log.info(url);
//        });

        String imageUrl = imageList.get(0);
        String cityId = imageUrl.substring("/photo/".length(), imageUrl.lastIndexOf("/scenery"));
        log.info("城市ID：" + cityId);

        TravelNoteDetail travelNoteDetail = new TravelNoteDetail();

        travelNoteDetail.setDestinationId(Integer.parseInt(cityId));
        try {
            travelNoteDetail.setUid(Long.parseLong(authorUrl.substring(3, authorUrl.lastIndexOf("."))));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        travelNoteDetail.setNoteId(Integer.parseInt(noteId));
        travelNoteDetail.setUrl("/i/" + noteId + ".html");

        travelNoteDetail.setNoteName(noteName);
        travelNoteDetail.setUpCount(Integer.parseInt(upNumber));
        travelNoteDetail.setContent(content);
        travelNoteDetail.setTravelTime(vcTime);

        try {
            travelNoteDetail.setCollectCount(Integer.parseInt(collectNumber));
            travelNoteDetail.setShareCount(Integer.parseInt(shareNumber));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        String viewCount = viewAndCommentCount.split("/")[0];
        String commentCount = viewAndCommentCount.split("/")[1];

        try {
            travelNoteDetail.setViewCount((int) (Double.parseDouble(viewCount.substring(0, viewCount.lastIndexOf("w"))) * 10000));
            travelNoteDetail.setCommentCount(Integer.parseInt(commentCount));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        travelNoteDetail.setWordCount(Integer.parseInt(wordTotalNumber));
        travelNoteDetail.setImageCount(Integer.parseInt(imageTotalNumber));
        travelNoteDetail.setHelpPeopleCount(Integer.parseInt(peopleTotalNumber));
        travelNoteDetail.setDestination(destination);

        travelNoteDetail.setTravelTime(travelTime.substring(travelTime.lastIndexOf("间") + 1));
        try {
            travelNoteDetail.setTravelDay(Integer.parseInt(
                    travelDay.replace(" ", "").substring(
                            travelDay.lastIndexOf("数") + 1,
                            travelDay.lastIndexOf("天") - 1)));
            travelNoteDetail.setTravelPeople(travelPeople.substring(travelPeople.lastIndexOf("物") + 1).trim());
            travelNoteDetail.setTravelCost(Integer.parseInt(
                travelCost
                    .substring(travelCost.lastIndexOf("用") + 1, travelCost.lastIndexOf("RMB"))));

        } catch (Exception e) {
            log.info("parse 出游时间 人数和天数出错");
        }

        travelNoteDetail.setStatus(1);
        travelNoteDetail.setCrawlTime(LocalDateTime.now());

//        log.info(noteName);
//        log.info(upNumber);
//        log.info(content);
//        log.info(vcTime);
//        log.info(collectNumber);
//        log.info(shareNumber);
//
//        log.info(wordTotalNumber);
//        log.info(imageTotalNumber);
//        log.info(peopleTotalNumber);
//        log.info(destination);
//
//        log.info(travelTime);
//        log.info(travelDay);
//        log.info(travelPeople);
//        log.info(travelCost);

        log.info("travelNoteDetail： " + travelNoteDetail.toString());

        return travelNoteDetail;
    }

    /**
     * 从个人主页提取游记列表
     *
     * @param pageSource
     */
    public static AuthorAndNoteList extractAuthorNoteList(String pageSource) {
        Html html = new Html(pageSource);
        AuthorAndNoteList authorAndNoteList = new AuthorAndNoteList();

        String authorUrl = html.xpath("//ul[@class='flt2']/li/a/@href").toString();
        String authorImageUrl = html.xpath("//div[@class='MAvaImg flt1']/img/@src").toString();
        String authorName = html.xpath("//span[@class='MAvaName']/text()").toString();
        String authorLevel = html.xpath("//span[@class='MAvaLevel']/text()").toString();
        List<String> authorHomeMusicUrlList = html.xpath("//div[contains(@class,'_j_musicitem')]/@data-music").all();

        Integer authorNoteNumber = Integer.parseInt(html.xpath("//div[@class='MAvaMore clearfix']/div[1]/strong/text()").toString());
        Long authorReplayNumber = Long.parseLong(html.xpath("//div[@class='MAvaMore clearfix']/div[2]/strong/text()").toString());
        Long authorViewNumber = Long.parseLong(html.xpath("//div[@class='MAvaMore clearfix']/div[3]/strong/text()").toString());

        log.info(authorName + " - " + authorLevel);
        log.info("authorNoteNumber: " + authorNoteNumber);
        log.info("authorReplayNumber: " + authorReplayNumber);
        log.info("authorViewNumber: " + authorViewNumber);
        log.info("authorHomeMusicUrlList: " + authorHomeMusicUrlList.toString());

        NoteAuthor noteAuthor = new NoteAuthor();
        noteAuthor.setUrl(authorUrl);
        noteAuthor.setUid(Long.parseLong(authorUrl.substring(3, authorUrl.lastIndexOf("."))));
        noteAuthor.setName(authorName.substring(0, authorName.lastIndexOf("(")));
        noteAuthor.setCity(authorName.substring(authorName.lastIndexOf("(") + 1, authorName.lastIndexOf(")")));
        noteAuthor.setLevel(authorLevel.substring(3));
        noteAuthor.setTravelTotal(authorNoteNumber);
        noteAuthor.setTravelCommentCount(authorReplayNumber);
        noteAuthor.setTravelViewCount(authorViewNumber);
        noteAuthor.setImageUrl(authorImageUrl);
        authorAndNoteList.setNoteAuthor(noteAuthor);

        List<TravelNoteDetail> travelNoteDetailList = new ArrayList<>();

        List<Selectable> selectableList = html.xpath("//div[@class='notes_list']/ul/li").nodes();
        selectableList.forEach(selectable -> {
            String url = selectable.xpath("dl/dt/a/@href").toString();
            String noteImageUrl = selectable.xpath("dl/dt/a/img/@src").toString();

            String destination = selectable.xpath("//div[@class='thumb_description']/strong/text()").toString();
            String destinationLarge = selectable.xpath("//div[@class='thumb_description']/span[1]/text()").toString();
            String noteMonth = selectable.xpath("//div[@class='thumb_description']/span[2]/text()").toString();

            // 游记信息
            Integer noteUpNumber = Integer.parseInt(selectable.xpath("//div[@class='note_title clearfix']/div[@class='MDing']/span/text()").toString());
//            String noteUrl = selectable.xpath("//h3[@class='hasxjicon']/a[2]/@href").toString();
            String noteName = selectable.xpath("//div[@class='note_info']/h3/allText()").toString();

            // 浏览信息
            String noteViewAndReplay = selectable.xpath("//div[@class='note_more']/span/em/text()").toString();
            Integer noteViewNumber = Integer.parseInt(noteViewAndReplay.split("/")[0]);
            Integer noteReplayNumber = Integer.parseInt(noteViewAndReplay.split("/")[1]);

            Integer noteCollectionNumber = Integer.parseInt(selectable.xpath("//div[@class='note_more']/span[2]/em/text()").toString());
            String noteDate = selectable.xpath("//div[@class='note_more']/span[@class='time']/text()").toString();

            String noteContent = selectable.xpath("//div[@class='note_word']/text()").toString().trim();
            log.info(noteName + " -> " + url);
//            log.info(url + " - " + noteImageUrl + " - " + destination);
//            log.info(destinationLarge + " - " + noteMonth + " - " + noteUpNumber);
//            log.info(noteName + " - " + noteViewNumber + " - " + noteReplayNumber);
//            log.info(noteCollectionNumber + " - " + noteDate + " - " + noteContent);

            TravelNoteDetail travelNoteDetail = new TravelNoteDetail();

            travelNoteDetail.setNoteId(Integer.parseInt(url.substring(3, url.lastIndexOf(".html"))));
            travelNoteDetail.setUid(noteAuthor.getUid());
            travelNoteDetail.setUrl(url);
            travelNoteDetail.setImageUrl(noteImageUrl);
            travelNoteDetail.setDestination(destination.substring(0, destination.lastIndexOf("/")));
            travelNoteDetail.setUpCount(noteUpNumber);
            travelNoteDetail.setNoteName(noteName);

//            travelNoteDetail.setViewCount(noteName);
            travelNoteDetail.setViewCount(noteViewNumber);
            travelNoteDetail.setCommentCount(noteReplayNumber);
            travelNoteDetail.setCollectCount(noteCollectionNumber);
            travelNoteDetail.setShortContent(noteContent);
            travelNoteDetail.setTravelTime(noteDate);
            travelNoteDetail.setStatus(0);

            travelNoteDetailList.add(travelNoteDetail);

        });
        authorAndNoteList.setTravelNoteDetailList(travelNoteDetailList);
        log.info("总共游记数：" + authorAndNoteList.getTravelNoteDetailList().size());

        return authorAndNoteList;
    }

}
