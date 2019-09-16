package com.example.tablayoutmenu;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.tablayoutmenu.model.News;
import com.example.tablayoutmenu.model.NewsInfo;
import com.example.tablayoutmenu.util.JsonUitl;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 新闻页面

 注：News和NewsInfo都是实体model类，见到不要蒙圈
前提准备：OkHttpClient包、Gson包、联网授权
 1.属性：ListView、OkHttpClient（网络通信异步请求对象）、List<NewsInfo>列表（NewsInfo是新闻信息的实体类）
 2.操作：1.OkHttpClient+Handler请求网络，得到响应数据，并封装到List<NewsInfo>列表
                1).创建Handler，重写handlerMessage方法
                2).使用OkHttpClient实现网络异步请求
                    1.创建Request对象和OkHttpClient对象
                    2.通过前两个对象创建Call对象
                    3.通过Call的enqueue(Callback)方法来提交异步请求，子线程
                        1.把响应字符串转为json对象
                        2.把json对象转为java对象
                        3.创建Message对象，初始化后传输给Handler
                3).handlerMessage方法中获取List
        2.用添加好的List创建列适配器，并添加
        3.设置监听，点击跳转详情页面
                1).从list中的对象中获取属性
                2).通过intent的putExtra设置传输
                3).完成跳转
 * @author thinkdoor
 */
public class FragmentOne extends Fragment implements AdapterView.OnItemClickListener{

    //日志TAG
    private static final String TAG = "FragmentOne";

    private ListView listView;
    //存放新闻信息的列表
    private List<NewsInfo> data;

    /**
     * 1).创建Handler，重写handlerMessage方法
     */
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            News news = (News)msg.obj;
            //3).handlerMessage方法中获取List
            data = news.getStories();
            List<NewsInfo> data2 = news.getTop_stories();
            //2.用添加好的List创建列适配器，并给ListView添加适配器
            listView.setAdapter(new NewsAdapter(data,data2,getContext()));
        }
    };

    /**
     * 创建视图
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //用Fragment_one来填充View视图
        View view = inflater.inflate(R.layout.fragment_one, container, false);

        listView = view.findViewById(R.id.list_view);
        listView.setOnItemClickListener(this);

        //初始化加载新闻数据
        initNews();

        return view;
    }

    /**
     * 加载新闻数据，2).使用OkHttpClient实现网络异步请求
     */
    public void initNews(){
        //知乎新闻
        String path = "http://news-at.zhihu.com/api/4/news/latest";
        //1.创建Request对象和OkHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .get()
                .url(path)
                .build();
        //2.通过前两个对象创建Call对象
        Call call = okHttpClient.newCall(request);
        //3.通过Call的enqueue(Callback)方法来提交异步请求，子线程
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
                    //1.把响应字符串转为json对象
                    JSONObject jsonObject = new JSONObject(str);
                    //2.把json对象转为java对象
                    News news =  (News)JsonUitl.stringToObject(jsonObject.toString(),News.class);
                    Log.d(TAG, news.toString());
                    /**
                     //3.创建Message对象，包装对象后，传输给Handler
                     */
                    Message message = new Message();
                    message.obj = news;
                    handler.sendMessage(message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 点击列表item显示新闻详情，取出当前点击的记录，传ID到详情页
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //1).从list中的对象中获取属性
        NewsInfo newsInfo = data.get(position);
        Intent intent = new Intent();
        //2).通过intent的putExtra设置传输
        intent.putExtra("id",newsInfo.getId());
        //3).完成跳转
        intent.setClass(getContext(),NewsDetailActivity.class);
        startActivity(intent);
    }
}