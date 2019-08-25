package com.cycloneboy.springcloud.mafengwo.utils;

import static com.cycloneboy.springcloud.common.common.Constants.FILE_IMAGE_DESTINATION_DIR;

import com.cycloneboy.springcloud.common.utils.download.DownloadFileWithThreadPool;
import com.cycloneboy.springcloud.mafengwo.entity.TravelImage;
import java.io.File;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Create by  sl on 2019-08-25 23:44
 */
public class CommonUtils {

  public static int saveTravelImage(List<TravelImage> travelImages) {
    AtomicInteger total = new AtomicInteger();
    travelImages.forEach(travelImage -> {
      //文件下载路径
      String filePath = travelImage.getOriginalUrl();

      //打算开启的线程数
      int threadNum = 20;
      String destination = FILE_IMAGE_DESTINATION_DIR + "/" + travelImage.getNoteId();
      File file = new File(destination);
      if (!file.exists()) {
        file.mkdir();
      }

      DownloadFileWithThreadPool pool = new DownloadFileWithThreadPool();
      pool.getFileWithThreadPool(filePath, destination, threadNum,
          travelImage.getNoteId() + "_" + travelImage.getVoteNum());

//      log.info("save image success: " + travelImage.getImageId());
      total.getAndIncrement();
    });

    return total.get();
  }

}
