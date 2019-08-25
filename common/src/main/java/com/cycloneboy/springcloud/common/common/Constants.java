package com.cycloneboy.springcloud.common.common;

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

    // kafka 输出topic
    public static final String KAFKA_SENDER_TRAVEL_IMAGE_OUTPUT = "travel-image-output";
    public static final String KAFKA_SENDER_TRAVEL_IMAGE_INPUT = "travel-image-input";
}
