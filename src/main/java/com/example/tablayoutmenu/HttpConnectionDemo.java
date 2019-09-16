package com.example.tablayoutmenu;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * 网络请求示例
 *
 * @author thinkdoor
 */

public class HttpConnectionDemo {

    public static void main(String[] args){
        try {
            //1.获取连接
            URL url = new URL("http://www.baidu.com");
            URLConnection urlConnection = (URLConnection)url.openConnection();
            //2.进行设置
            //3.进行请求，并得到响应
            InputStream inputStream = urlConnection.getInputStream();
            System.out.println(inputStream);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
