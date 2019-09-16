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

import com.example.tablayoutmenu.model.SongsList;
import com.example.tablayoutmenu.model.SongsInfo;
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
 * 音乐页面

 注：SongsList和SongsInfo都是实体model类，见到不要蒙圈
前提：联网请求、使用Gson、OkHttpClient，记得添加权限和包
 1.属性：ListView、OkHttpClient（网络通信异步请求对象）、List<SongsInfo>列表（SongsInfo是新闻信息的实体类）
 2.操作：1.OkHttpClient+Handler请求网络，得到响应数据，并封装到List<SongInfo>列表
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
public class FragmentTwo extends Fragment implements AdapterView.OnItemClickListener{

    private static final String TAG = "FragmentTwo";
    private ListView listView;
    //存放音乐信息的列表
    private List<SongsInfo> data;

    /**
     * 创建handler，重写handlerMessage方法
     */
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            SongsList songsList = (SongsList) msg.obj;
            data = songsList.getSongs().getSongsInfo();
            //给listview设置适配器
            listView.setAdapter(new SongsAdapter(data,getContext()));
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
        View view = inflater.inflate(R.layout.fragment_two, container, false);
        listView = (ListView)view.findViewById(R.id.list_view);
        listView.setOnItemClickListener(this);

        //加载数据
        initSongs();
        return view;

    }

    public void init(){

    }

    /**
     * 加载歌曲数据
     */
    public void initSongs(){
        //酷狗音乐排行榜
        String path = "http://m.kugou.com/rank/info/?rankid=8888&page=1&json=true";
        OkHttpClient okHttpClient = new OkHttpClient();
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
                    SongsList songsList = (SongsList) JsonUitl.stringToObject(jsonObject.toString(), SongsList.class);
                    Log.d(TAG, songsList.toString());
                    /**
                     * 创建Message，用handler传送message到主线程
                     */
                    Message message = new Message();
                    message.obj = songsList;
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
        SongsInfo songsInfo = data.get(position);
        Intent intent = new Intent();
        intent.putExtra("id", songsInfo.getHash());
        intent.setClass(getContext(),SongsDetailActivity.class);
        startActivity(intent);
    }
}