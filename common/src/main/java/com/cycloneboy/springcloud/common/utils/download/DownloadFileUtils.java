package com.cycloneboy.springcloud.common.utils.download;

import static com.cycloneboy.springcloud.common.common.Constants.DEFAULT_THREAD_NUM;
import static com.cycloneboy.springcloud.common.common.Constants.DOWNLOAD_TIME_OUT;
import static com.cycloneboy.springcloud.common.common.Constants.FILE_IMAGE_DESTINATION_DIR;
import static com.cycloneboy.springcloud.common.common.Constants.IMAGE_URL_1;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

/**
 * Create by  sl on 2019-08-25 22:19
 */
public class DownloadFileUtils {

  private static int BUFFER_SIZE = 10240; // 缓冲区大小(缓冲区越大下载的越快,但是要根据自己的服务器配置)

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

  /**
   * 下载文件
   *
   * @param urlLocation 文件路径
   * @param filePath    保存文件本地路径
   * @param rename      重命名文件
   * @return
   */
  public static String downloadFile(String urlLocation, String filePath, String rename) {
    HttpURLConnection conn = null;
    String fileName = null;
    try {
      // 通过下载路径获取连接
      conn = DownloadFileUtils.getHttpUrlConnection(urlLocation);
      // 判断连接是否正确。

      if (conn.getResponseCode() == 200) {
        // 获取文件大小。
        int fileSize = conn.getContentLength();
        // 得到文件名
        fileName = DownloadFileUtils.getFileName(urlLocation);
        // 根据文件大小及文件名，创建一个同样大小，同样文件名的文件
        if (rename != null) {
          fileName = rename + fileName.substring(fileName.lastIndexOf("."));
        }
        File pathfile = new File(filePath);
        if (!pathfile.exists()) {
          pathfile.mkdir();
        }
        File file = new File(filePath + File.separator + fileName);

        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        raf.setLength(fileSize);
        raf.seek(0);

//        //此步骤是关键。
//        conn.setRequestProperty("Range", "bytes=" + 0 + "-" + fileSize);
//        if (conn.getResponseCode() == 206) {
        //读取数据并写入
        InputStream inStream = conn.getInputStream();
        byte[] b = new byte[BUFFER_SIZE];
        int len = 0;
        while ((len = inStream.read(b)) != -1) {
          raf.write(b, 0, len);
        }
//        System.out.println("线程" + threadId + "下载完毕");
//        }

      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (null != conn) {
        conn.disconnect();
      }
    }

    return fileName;
  }

  public static void main(String[] args) {

    Date startDate = new Date();
    DownloadFileWithThreadPool pool = new DownloadFileWithThreadPool();
    pool.getFileWithThreadPool(IMAGE_URL_1, FILE_IMAGE_DESTINATION_DIR, DEFAULT_THREAD_NUM, null);

    Date endDate = new Date();
    System.out.println("用时： " + (endDate.getTime() - startDate.getTime()));


  }
}
