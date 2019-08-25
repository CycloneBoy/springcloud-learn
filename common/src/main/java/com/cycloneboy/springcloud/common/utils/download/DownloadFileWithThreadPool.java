package com.cycloneboy.springcloud.common.utils.download;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Create by  sl on 2019-08-25 22:15
 */
public class DownloadFileWithThreadPool {

  public void getFileWithThreadPool(String urlLocation, String filePath, int poolLength,
      String rename) {
    ExecutorService threadPool = Executors.newCachedThreadPool();
    try {
      // 通过下载路径获取连接
      URL url = new URL(urlLocation);
      HttpURLConnection conn = DownloadFileUtils.getHttpUrlConnection(url);
      // 判断连接是否正确。

      if (conn.getResponseCode() == 200) {
        // 获取文件大小。
        int fileSize = conn.getContentLength();
        // 得到文件名
        String fileName = DownloadFileUtils.getFileName(urlLocation);
        // 根据文件大小及文件名，创建一个同样大小，同样文件名的文件
        File file = new File(filePath + File.separator + fileName);
        if (rename != null) {
          file = new File(
              filePath + File.separator + rename + fileName.substring(fileName.lastIndexOf(".")));
        }

        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        raf.setLength(fileSize);
        raf.close();

        // 将文件分成threadNum = 5份。
        int block = fileSize % poolLength == 0 ? fileSize / poolLength : fileSize / poolLength + 1;

        for (int threadId = 0; threadId < poolLength; threadId++) {
          // 传入线程编号，并开始下载。
          DownloadThread downloadThread = new DownloadThread(threadId, block, file, url);
          threadPool.execute(downloadThread);
        }
        threadPool.shutdown();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
