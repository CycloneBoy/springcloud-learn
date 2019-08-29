package com.cycloneboy.springcloud.travelnote.utils;

public class FileUtils {


    /**
     *      * 下载图片到本地
     *      * @param picUrl 图片Url
     *      * @param localPath 本地保存图片地址
     *      * @return
     *     
     */
    public String downloadImage(String picUrl, String localPath) {
        String filePath = null;
        String url = null;
//        try {
//            URL httpurl = new URL(picUrl);
//            String fileName = getFileNameFromUrl(picUrl);
//            filePath = localPath + fileName;
//            File f = new File(filePath);
//            FileUtils.copyURLToFile(httpurl, f);
//            Function fun = new Function();
//            url = filePath.replace("/www/web/imgs", fun.getProValue("IMG_PATH"));
//        } catch (Exception e) {
//
//            return null;
//        }
        return url;
    }

    /**
     * 根据url获取文件名
     *
     * @param url
     * @return 文件名
     */
    public static String getFileNameFromUrl(String url) {
        //获取后缀
        String sux = url.substring(url.lastIndexOf("."));
        if (sux.length() > 4) {
            sux = ".jpg";
        }
        int i = (int) (Math.random() * 1000);
        //随机时间戳文件名称
        String name = Long.toString(System.currentTimeMillis()) + i + sux;
        return name;
    }
}
