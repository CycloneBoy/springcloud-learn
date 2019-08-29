package com.cycloneboy.springcloud.common.utils.download;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Create by  sl on 2019-08-27 00:11
 */
@Slf4j
@Getter
public class DownloadFileThread extends Thread {

  private static final int BUFFER_SIZE = 10240;

  private String urlLocation;

  private String filePath;

  private String rename;

  private String fileName;

  private File file = null;

  private URL url = null;

  public DownloadFileThread(URL url, File file) {
//    this.urlLocation = urlLocation;
//    this.filePath = filePath;
//    this.rename = rename;
    this.url = url;
    this.file = file;
  }

  public DownloadFileThread(String urlLocation, String filePath, String rename) {
    this.urlLocation = urlLocation;
    this.filePath = filePath;
    this.rename = rename;

    this.fileName = DownloadFileUtils.getFileName(urlLocation);

    if (rename != null) {
      fileName = rename + fileName.substring(fileName.lastIndexOf("."));
    }

    File file = new File(filePath + File.separator + fileName);

    File pathfile = new File(filePath);
    if (!pathfile.exists()) {
      pathfile.mkdir();
    }
    try {
      this.url = new URL(urlLocation);
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
    this.file = file;
  }


  @Override
  public void run() {
    HttpURLConnection conn = null;
    try {
      // 通过下载路径获取连接
      conn = DownloadFileUtils.getHttpUrlConnection(url);
      // 判断连接是否正确。
      if (conn.getResponseCode() == 200) {
        // 获取文件大小。
        int fileSize = conn.getContentLength();
        log.info("filename:{} filesize:{} kb", fileName, fileSize / 1024);

        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        raf.setLength(fileSize);
        raf.seek(0);

        //读取数据并写入
        InputStream inStream = conn.getInputStream();
        byte[] b = new byte[BUFFER_SIZE];
        int len = 0;
        while ((len = inStream.read(b)) != -1) {
          raf.write(b, 0, len);
        }
        raf.close();
//        System.out.println("线程" + threadId + "下载完毕");
//        }
//        log.debug("success download file : {}", filename);
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (null != conn) {
        conn.disconnect();
        log.info("success download file : {}", url.toString());
      }
    }

  }
}
