package com.example.tablayoutmenu;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

import com.example.tablayoutmenu.model.NewsDetail;
import com.example.tablayoutmenu.util.JsonUitl;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * 新闻详情页
 属性：WebView
 操作：1.初始化页面
    2.联网请求，得到响应数据，进行处理
        1.创建Handler，重写handlerMeassage方法
        2.创建OkHttpClient和Request对象
        3.创建Call对象
        4.重写Call#enqueue(Callback)方法
            1.把返回数据转为json对象
            2.把json对象转为java对象
            3.创建Message，发送数据到主线程
        5.handler处理消息
            1.得到所需消息
            2.WebView赋值
    3.完善结尾

 */
public class NewsDetailActivity extends AppCompatActivity {

    //日志TAG
    private static final String TAG = "NewsDetailActivity";

    //创建WebView
    private WebView webView;

    //创建Handler
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            NewsDetail newsDetail = (NewsDetail)msg.obj;
            //用webview加载网页
            webView.loadUrl(newsDetail.getShare_url());
        }
    };

    /**
     * 创建页面
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        webView = (WebView) findViewById(R.id.web_view);
        //设置可以执行JS脚本
        webView.getSettings().setJavaScriptEnabled(true);
        //加载数据
        getNews();
    }

    /**
     * 加载新闻数据
     */
    public void getNews(){
        //知乎新闻详情
        String path = "http://news-at.zhihu.com/api/4/news/";
        Intent intent = getIntent();
        long id = intent.getLongExtra("id",0);
        path += id;
        //1.创建OkHttpClient对象和Request对象
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .get()
                .url(path)
                .build();
        //2.创建Call对象
        Call call = okHttpClient.newCall(request);
        //3.执行请求，访问网络数据
        call.enqueue(new Callback() {
            //请求失败
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure() called with: call = [" + call + "], e = [" + e + "]");
            }

            //在子线程中运行的(不能更新UI),请求成功
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                Log.d(TAG, str);
                try {
                    //1.转换成JSON对象
                    JSONObject jsonObject = new JSONObject(str);
                    //2.通过Gson把json字符串转换成java对象
                    NewsDetail newsDetail
                            =  (NewsDetail) JsonUitl.stringToObject(jsonObject.toString(),NewsDetail.class);
                    //3.创建Message，发送消息
                    Message message = new Message();
                    message.obj = newsDetail;
                    handler.sendMessage(message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 关闭页面的完善处理
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView!=null){
            webView.destroy();
        }
    }
}
