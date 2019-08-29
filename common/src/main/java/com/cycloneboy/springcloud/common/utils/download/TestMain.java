package com.cycloneboy.springcloud.common.utils.download;

import static com.cycloneboy.springcloud.common.common.Constants.FILE_IMAGE_DESTINATION_DIR;
import static com.cycloneboy.springcloud.common.common.Constants.IMAGE_URL_1;
import static com.cycloneboy.springcloud.common.common.Constants.IMAGE_URL_2;
import static com.cycloneboy.springcloud.common.common.Constants.IMAGE_URL_LIST;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

/**
 * Create by  sl on 2019-08-27 20:20
 */
@Slf4j
public class TestMain {

  public static void main(String[] args) {
//    testDownload();
//    testDownload10();

//    testDownload10WithPool();

//    testDownloadFile();
    log.info("----------------------------");
//    testDownload10WithPool10();

    testDownloadMusicWithPool();
  }

  public static void testDownloadFile() {
    long start = System.currentTimeMillis();

    IMAGE_URL_LIST.forEach(url -> {
          String filename = DownloadFileUtils
              .downloadFile(url, FILE_IMAGE_DESTINATION_DIR + "/test_thread0", null);
          log.info("success download file : {}", filename);
        }
    );

    long end = System.currentTimeMillis();
    log.info("下载{}张图片,用时： {} ms", IMAGE_URL_LIST.size(), (end - start));
  }

  public static void testDownload() {
    Date startDate = new Date();

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

    Thread thread1 = null;
    try {
      thread1 = new DownloadFileThread(new URL(IMAGE_URL_1), file);
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }

    thread1.start();

    Thread thread2 = new DownloadFileThread(IMAGE_URL_2, filePath, null);
    thread2.start();

    Date endDate = new Date();
    log.info("下载{}张图片,用时： {} ms", IMAGE_URL_LIST.size(), (endDate.getTime() - startDate.getTime()));
  }

  /**
   * 多线程下载10张照片
   */
  public static void testDownload10() {
    long start = System.currentTimeMillis();
    IMAGE_URL_LIST.forEach(url -> {
      new DownloadFileThread(url, FILE_IMAGE_DESTINATION_DIR + "/test_thread1", null).start();
    });

    long end = System.currentTimeMillis();
    log.info("下载{}张图片,用时： {} ms", IMAGE_URL_LIST.size(), (end - start));
  }

  /**
   * 多线程下载10张照片
   */
  public static void testDownload10WithPool() {
    ExecutorService threadPool = Executors.newCachedThreadPool();

    Date startDate = new Date();
    IMAGE_URL_LIST.forEach(url -> {
      Thread thread = new DownloadFileThread(url, FILE_IMAGE_DESTINATION_DIR + "/test_thread",
          null);
      threadPool.execute(thread);
    });

    threadPool.shutdown();
    Date endDate = new Date();
    log.info("下载{}张图片,用时： {} ms", IMAGE_URL_LIST.size(), (endDate.getTime() - startDate.getTime()));


  }

  /**
   * 多线程下载10张照片
   */
  public static void testDownload10WithPool10() {
    ExecutorService threadPool = Executors.newFixedThreadPool(16);

    long start = System.currentTimeMillis();
    IMAGE_URL_LIST.forEach(url -> {
      Thread thread = new DownloadFileThread(url, FILE_IMAGE_DESTINATION_DIR + "/test_thread2",
          null);
      threadPool.execute(thread);
    });

    threadPool.shutdown();

    try {
      while (!threadPool.awaitTermination(1, TimeUnit.SECONDS)) {
        log.info("线程还在执行。。。");
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    long end = System.currentTimeMillis();
    log.info("下载{}张图片,用时： {} ms", IMAGE_URL_LIST.size(), (end - start));


  }

  /**
   * 下载音乐
   */
  public static void testDownloadMusicWithPool() {
    ExecutorService threadPool = Executors.newFixedThreadPool(16);

    long start = System.currentTimeMillis();
    List<String> musicList = new ArrayList<>();
    musicList.add(
        "http://mp3file.mafengwo.net/201908302122/0209ee35b38ede9cc3de64f7c49401f1/s13/M00/9F/88/wKgEaVyQtyOAZKWwALJbPQ3L0cE890.mp3");

    musicList.forEach(url -> {
      Thread thread = new DownloadFileThread(url, FILE_IMAGE_DESTINATION_DIR + "/test_music",
          null);
      threadPool.execute(thread);
    });

    threadPool.shutdown();

    try {
      while (!threadPool.awaitTermination(1, TimeUnit.SECONDS)) {
        log.info("线程还在执行。。。");
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    long end = System.currentTimeMillis();
    log.info("下载{}张图片,用时： {} ms", IMAGE_URL_LIST.size(), (end - start));


  }

}
