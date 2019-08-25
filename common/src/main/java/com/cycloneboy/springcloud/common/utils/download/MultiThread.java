package com.cycloneboy.springcloud.common.utils.download;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Create by  sl on 2019-08-25 21:04
 */
public class MultiThread {

  public static void main(String[] args) {
    //文件下载路径
    String filePath = "http://p4-q.mafengwo.net/s14/M00/9B/43/wKgE2l0hhqOAAQg8AAkz36AHSzE097.jpg";
    //文件保存路径
    String destination = "/home/sl/workspace/image";
    //打算开启的线程数
    int threadNum = 5;
    new MultiThread().download(filePath, destination, threadNum);
  }

  /**
   * 下载文件
   */
  private void download(String filePath, String destination, int threadNum) {
    try {
      //通过下载路径获取连接
      URL url = new URL(filePath);
      HttpURLConnection conn = DownloadFileUtils.getHttpUrlConnection(url);
      //判断连接是否正确。
      if (conn.getResponseCode() == 200) {
        // 获取文件大小。
        int fileSize = conn.getContentLength();
        //得到文件名
        String fileName = DownloadFileUtils.getFileName(filePath);
        //根据文件大小及文件名，创建一个同样大小，同样文件名的文件
        File file = new File(destination + File.separator + fileName);
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        raf.setLength(fileSize);
        raf.close();
        // 将文件分成threadNum = 5份。
        int block = fileSize % threadNum == 0 ? fileSize / threadNum
            : fileSize / threadNum + 1;
        for (int threadId = 0; threadId < threadNum; threadId++) {
          //传入线程编号，并开始下载。
          new DownloadThread(threadId, block, file, url).start();
        }

      }
    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


}
