package com.cycloneboy.springcloud.common.common;

import java.util.Arrays;
import java.util.List;

/**
 * create by CycloneBoy on 2019-06-21 20:48
 */
public class Constants {

    public static final String CONFIG_FILE_NAME = "generator.properties";
    public static final String CONFIG_MAIN_PATH = "mainPath";

    public static final String DEFAULT_MAN_PATH = "com.cycloneboy.springcloud.noxue";

    public static final String CONFIG_TABLE_PREFIX = "tablePrefix";
    public static final String CONFIG_UNKNOWN_TYPE = "unknownType";
    public static final String CONFIG_BIG_DECIMAL = "BigDecimal";
    public static final String PRI = "PRI";

    public static final String CONFIG_FILE_RESOURCE_LOADER_CLASS_KEY = "fileResourceLoaderClassKey";
    public static final String CONFIG_FILE_RESOURCE_LOADER_CLASS_VALUE = "fileResourceLoaderClassValue";
    public static final String CONFIG_PACKAGE = "package";
    public static final String CONFIG_MODULE_NAME = "moduleName";
    public static final String CONFIG_AUTHOR = "author";
    public static final String CONFIG_EMAIL = "email";
    public static final String FILE_ENCODE_UTF8 = "UTF-8";


    /**
     * 游记URI
     */
    public static final String TRAVEL_NOTE_URI = "http://MAFENGWO/travelnote";

    public static final int DOWNLOAD_TIME_OUT = 5000;

    //文件保存路径
    public static final String FILE_IMAGE_DESTINATION_DIR = "/home/sl/workspace/image";

    public static final String FILE_IMAGE_DESTINATION_DIR_2 =
        FILE_IMAGE_DESTINATION_DIR + "/test_thread2";

    // kafka 输出topic
    // 游记图片
    public static final String KAFKA_SENDER_TRAVEL_IMAGE_OUTPUT = "travel-image-output";
    public static final String KAFKA_SENDER_TRAVEL_IMAGE_SEND = "travel-image-send";

    // 热门游记
    public static final String KAFKA_SENDER_TRAVEL_NOTE_OUTPUT = "travel-note-output";
    public static final String KAFKA_SENDER_TRAVEL_NOTE_SAVE = "travel-note-save";

    // 热门游记详情
    public static final String KAFKA_SENDER_TRAVEL_NOTE_DETAIL_OUTPUT = "travel-note-detail-output";
    public static final String KAFKA_SENDER_TRAVEL_NOTE_DETAIL_SAVE = "travel-note-detail-save";

    // 游记作者
    public static final String KAFKA_SENDER_TRAVEL_AUTHOR_OUTPUT = "travel-note-author-output";
    public static final String KAFKA_SENDER_TRAVEL_AUTHOR_SAVE = "travel-note-author-save";

    //测试图片1
    public static final String IMAGE_URL_1 = "http://p4-q.mafengwo.net/s14/M00/9B/43/wKgE2l0hhqOAAQg8AAkz36AHSzE097.jpg";
    public static final String IMAGE_URL_2 = "http://p2-q.mafengwo.net/s13/M00/81/EC/wKgEaVxurrWAUTOOAA1LEKH6-kk79.jpeg";
    public static final String IMAGE_URL_3 = "http://n1-q.mafengwo.net/s11/M00/83/64/wKgBEFqWr3mAEBGaAARhMfgKY9Q27.jpeg";
    public static final String IMAGE_URL_4 = "http://p1-q.mafengwo.net/s13/M00/75/12/wKgEaVyFX4-AR8AeAAbAftcsx7g85.jpeg";
    public static final String IMAGE_URL_5 = "http://p1-q.mafengwo.net/s11/M00/83/55/wKgBEFqWr3KAJUtDAA6N1sNBoTA89.jpeg";
    public static final String IMAGE_URL_6 = "http://n3-q.mafengwo.net/s14/M00/AC/60/wKgE2l1dYiOARVnGABJSNuQS7Nw131.jpg";
    public static final String IMAGE_URL_7 = "http://n1-q.mafengwo.net/s11/M00/83/47/wKgBEFqWr2iAE5jMAAsNI_cwcsI24.jpeg";
    public static final String IMAGE_URL_8 = "http://p4-q.mafengwo.net/s11/M00/83/81/wKgBEFqWr4iALB0iAAYbQDiRIyk88.jpeg";
    public static final String IMAGE_URL_9 = "http://n2-q.mafengwo.net/s11/M00/F1/76/wKgBEFpmfjWAZLVlAAQp4c-rFoY71.jpeg";
    public static final String IMAGE_URL_10 = "http://p4-q.mafengwo.net/s14/M00/F4/42/wKgE2l1PmGCAX12nAA5h2_8o-1A06.jpeg";

    public static final List<String> IMAGE_URL_LIST = Arrays
        .asList(IMAGE_URL_1, IMAGE_URL_2, IMAGE_URL_3, IMAGE_URL_4, IMAGE_URL_5,
            IMAGE_URL_6, IMAGE_URL_7, IMAGE_URL_8, IMAGE_URL_9, IMAGE_URL_10);
    //下载图片的开启的线程数
    public static final int DEFAULT_THREAD_NUM = 1;

    // 历历在目的首页链接
    public static final String TRAVEL_NOTE_LIST_BY_YEAR_URL = "http://www.mafengwo.cn/app/calendar.php?year=";

    /**
     * 常用分页数量
     */
    public static final int PAGE_SIZE_10 = 10;
    public static final int PAGE_SIZE_100 = 100;
    public static final int PAGE_SIZE_1000 = 1000;
    public static final int PAGE_SIZE_5000 = 5000;

    public static final String MAFENGWO_HOST_URL = "http://www.mafengwo.cn";


}
