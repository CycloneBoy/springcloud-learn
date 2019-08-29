package com.cycloneboy.springcloud.common.utils.download;

import static com.cycloneboy.springcloud.common.common.Constants.FILE_IMAGE_DESTINATION_DIR;
import static com.cycloneboy.springcloud.common.common.Constants.IMAGE_URL_1;
import static com.cycloneboy.springcloud.common.common.Constants.IMAGE_URL_LIST;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * Create by  sl on 2019-08-26 23:46
 */
@Slf4j
public class DownloadFileUtilsTest {

  @Test
  public void downloadFile() {
    log.info("begain download...");
    String filename = DownloadFileUtils
        .downloadFile(IMAGE_URL_1, FILE_IMAGE_DESTINATION_DIR + "/test", null);
    log.info("success download file : {}", filename);
  }

  @Test
  public void testDownloadList() {
    Date startDate = new Date();
    IMAGE_URL_LIST.forEach(url -> {
          String filename = DownloadFileUtils
              .downloadFile(url, FILE_IMAGE_DESTINATION_DIR + "/test", null);
          log.info("success download file : {}", filename);
        }
    );
    Date endDate = new Date();
    log.info("下载{}张图片,用时： {} ms", IMAGE_URL_LIST.size(), (endDate.getTime() - startDate.getTime()));

  }

  @Test
  public void testDownloadListThread() throws MalformedURLException {
    Date startDate = new Date();
    ExecutorService threadPool = Executors.newCachedThreadPool();
//    IMAGE_URL_LIST.forEach(url -> {
//      new DownloadFileThread(url, FILE_IMAGE_DESTINATION_DIR + "/test_thread", null).start();
//    });

    // 得到文件名
    String fileName = DownloadFileUtils.getFileName(IMAGE_URL_1);
    // 根据文件大小及文件名，创建一个同样大小，同样文件名的文件
//    if (rename != null) {
//      fileName = rename + fileName.substring(fileName.lastIndexOf("."));
//    }
    String filePath = FILE_IMAGE_DESTINATION_DIR + "/test_thread";
    File file = new File(filePath + File.separator + fileName);

    File pathfile = new File(filePath);
    if (!pathfile.exists()) {
      pathfile.mkdir();
    }

    Thread thread1 = new DownloadFileThread(new URL(IMAGE_URL_1), file);
    thread1.start();

    Date endDate = new Date();
    log.info("下载{}张图片,用时： {} ms", IMAGE_URL_LIST.size(), (endDate.getTime() - startDate.getTime()));
  }

}
