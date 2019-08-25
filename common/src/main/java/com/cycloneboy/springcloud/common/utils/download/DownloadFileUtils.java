package com.cycloneboy.springcloud.common.utils.download;

import static com.cycloneboy.springcloud.common.common.Constants.DOWNLOAD_TIME_OUT;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

/**
 * Create by  sl on 2019-08-25 22:19
 */
public class DownloadFileUtils {

  /**
   * 获取文件长度
   */
  public static long getContentLength(String urlLocation) throws IOException {

    HttpURLConnection conn = DownloadFileUtils.getHttpUrlConnection(urlLocation);

    return conn.getContentLength();
  }

  /**
   * 获取链接
   */
  public static HttpURLConnection getHttpUrlConnection(URL url) throws IOException {
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setReadTimeout(DOWNLOAD_TIME_OUT);
    conn.setRequestMethod("GET");

    return conn;
  }

  /**
   * 获取链接
   */
  public static HttpURLConnection getHttpUrlConnection(String urlLocation) throws IOException {
    URL url = null;
    if (urlLocation != null) {
      url = new URL(urlLocation);
    }
    return getHttpUrlConnection(url);
  }


  //由路径获取文件名。
  public static String getFileName(String filePath) {
    return filePath.substring(filePath.lastIndexOf('/') + 1);
  }


  public static void main(String[] args) {
    //文件下载路径
    String filePath = "http://p4-q.mafengwo.net/s14/M00/9B/43/wKgE2l0hhqOAAQg8AAkz36AHSzE097.jpg";
    //文件保存路径
    String destination = "/home/sl/workspace/image";
    //打算开启的线程数
    int threadNum = 1;

    Date startDate = new Date();
    DownloadFileWithThreadPool pool = new DownloadFileWithThreadPool();
    pool.getFileWithThreadPool(filePath, destination, threadNum, null);

    Date endDate = new Date();
    System.out.println("用时： " + (endDate.getTime() - startDate.getTime()));

  }
}
