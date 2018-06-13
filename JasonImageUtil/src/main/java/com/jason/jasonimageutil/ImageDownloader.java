package com.jason.jasonimageutil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ImageDownloader {
    public static boolean loadFromIntert(String urlString, String path) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            // 超时时间
            urlConnection.setConnectTimeout(10000);
            // 设置是否输出
            urlConnection.setDoOutput(false);
            // 设置是否读入
            urlConnection.setDoInput(true);
            // 设置是否使用缓存
            urlConnection.setUseCaches(false);
            urlConnection.setRequestProperty("Accept-Charset", "UTF-8");
            urlConnection.connect();
            File file = new File(path);
            int length = urlConnection.getContentLength();
            if (file.exists() && length == file.length()) {
                urlConnection.disconnect();
                return true;
            }
            InputStream inputStream = urlConnection.getInputStream();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            byte[] bytes = new byte[1024 * 10];
            int total = 0;
            while ((total = inputStream.read(bytes)) != -1) {
                fileOutputStream.write(bytes, 0, total);
            }
            fileOutputStream.close();
            inputStream.close();
            return true;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }


}
