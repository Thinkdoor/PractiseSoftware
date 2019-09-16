package com.example.tablayoutmenu.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;


import com.example.tablayoutmenu.R;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/11/10.
 */

public class AsyncImageLoader {

    private Context context;
    public AsyncImageLoader(Context context){
        this.context = context;
    }

    /**
     * @param url　本地或网络的url
     * @return　　　url的bitmap
     */
    public Bitmap getLocalOrNetBitmap(String url)
    {
        Bitmap  bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
        if (url != null) {
            InputStream in = null;
            BufferedOutputStream out = null;
            try
            {
                //读取图片输入流
                in = new BufferedInputStream(new URL(url).openStream(), 2 * 1024);
                //准备输出流
                final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
                out = new BufferedOutputStream(dataStream, 2 * 1024);
                byte[] b = new byte[1024];
                int read;
                //将输入流变为输出流
                while ((read = in.read(b)) != -1) {
                    out.write(b, 0, read);
                }
                out.flush();
                //将输出流转换为bitmap
                byte[] data = dataStream.toByteArray();
                bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                data = null;
                return bitmap;
            }
            catch (IOException e)
            {
                e.printStackTrace();
                return bitmap;
            }
        }
        return bitmap;
    }

    public void asyncloadImage(final ImageView imageView, final String uri) {
        final Handler mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 1) {
                    Bitmap bitmap = (Bitmap) msg.obj;
                    if (imageView != null && uri != null) {
                        imageView.setImageBitmap(bitmap);
                    }

                }
            }
        };
        // 子线程，开启子线程去下载或者去缓存目录找图片，并且返回图片在缓存目录的地址
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    //这个URI是图片下载到本地后的缓存目录中的URI
                    if (uri != null ) {
                        Bitmap bitmap = getLocalOrNetBitmap(uri);
                        Message msg = new Message();
                        msg.what = 1;
                        msg.obj = bitmap;
                        mHandler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(runnable).start();
    }

}

