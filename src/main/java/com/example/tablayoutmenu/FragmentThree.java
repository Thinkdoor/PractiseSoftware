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

import com.example.tablayoutmenu.model.MovieItem;
import com.example.tablayoutmenu.model.Movies;
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
 * A simple {@link Fragment} subclass.
 */
public class FragmentThree extends Fragment implements AdapterView.OnItemClickListener{

    private static final String TAG = "FragmentThree";
    private OkHttpClient okHttpClient;
    String title = "电影";

    //豆瓣电影
    private String path = "https://api.douban.com/v2/movie/in_theaters?apikey=0b2bdeda43b5688921839c8ecb20399b";
    private ListView listView;
    //存放电影信息的列表
    private List<MovieItem> data;

    /**
     * 创建handler，重写handlerMessage方法
     */
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Movies movies = (Movies) msg.obj;
            data = movies.getSubjects();
            //给listview设置适配器
            listView.setAdapter(new MovieAdapter(data,getContext()));
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        listView = (ListView)view.findViewById(R.id.list_view);
        listView.setOnItemClickListener(this);
        okHttpClient = new OkHttpClient();
        //加载数据
        getNews();
        return view;

    }

    /**
     * 加载新闻数据
     */
    public void getNews(){
        //构造Request对象
        Request request = new Request.Builder()
                .get()
                .url(path)
                .build();
        //创建Call对象
        Call call = okHttpClient.newCall(request);
        //执行请求，访问网络数据
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
                    //转换成JSON对象
                    JSONObject jsonObject = new JSONObject(str);
                    //通过Gson把json字符串转换成java对象
                    Movies movies = (Movies) JsonUitl.stringToObject(jsonObject.toString(),Movies.class);
                    Log.d(TAG, movies.toString());
                    /**
                     * 创建Message，用handler传送message到主线程
                     */
                    Message message = new Message();
                    message.obj = movies;
                    handler.sendMessage(message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 点击列表item显示新闻详情
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //取出当前点击的记录，传ID到详情页
        MovieItem movieItem = data.get(position);
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("img",movieItem.getImages().getMedium());
        bundle.putString("title",movieItem.getTitle());
        bundle.putString("style",movieItem.toString(movieItem.getGenres()));
        bundle.putString("rate",movieItem.getRating().getAverage());
        bundle.putString("duration",movieItem.toString(movieItem.getDurations()));
        bundle.putString("alt",movieItem.getAlt());
        intent.putExtras(bundle);
        intent.setClass(getContext(),MoviesDetailActivity.class);
        startActivity(intent);
    }
}