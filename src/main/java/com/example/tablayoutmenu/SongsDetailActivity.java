package com.example.tablayoutmenu;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tablayoutmenu.model.NewsDetail;
import com.example.tablayoutmenu.model.SongsDetail;
import com.example.tablayoutmenu.util.JsonUitl;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 歌曲详情页面

 1.属性：TextView
 2.操作

 * @author thinkdoor
 */



public class SongsDetailActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener{

    private static final String TAG = "SongsDetailActivity";

    /*
    创建属性，UI、网络
     */
    private TextView textView_songsTitle,textView_current,textView_total;
    private MediaPlayer mediaPlayer;
    private SeekBar seekBar;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            SongsDetail songsdetail = (SongsDetail) msg.obj;
            //用Media加载音乐
                try {
                    Log.d(TAG, "handleMessage: 创建音乐对象");
                    initMedia(songsdetail.getUrl());
                    seekBar.setMax(mediaPlayer.getDuration());
                    textView_total.setText(getTime(mediaPlayer.getDuration()));
                    textView_songsTitle.setText(songsdetail.getSongName());
                } catch (IOException e) {
                    Toast.makeText(getApplication(),"出了点问题！",Toast.LENGTH_SHORT).show();
                }
            }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs_detail);
        init();

        //加载数据
        getSongs();

        updateSeekBar();
    }

    public String getTime(int time){
        SimpleDateFormat dateformate = new SimpleDateFormat("mm:ss");
        String t = dateformate.format(time);
        return t;
    }
    Runnable run;
    public void updateSeekBar(){
        run = new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run:     ----------------------");
                if(mediaPlayer!=null){
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    textView_current.setText(getTime(mediaPlayer.getCurrentPosition()));
                }
                handler.postDelayed(this,1000);
            }
        };
        runOnUiThread(run);
    }

    /**
     * 初始化UI组件
     */
    public void init(){
        textView_songsTitle = (TextView) findViewById(R.id.tv_songsdetail_songstitle);
        seekBar = (SeekBar) findViewById(R.id.sb_songsdetail);
        textView_total = (TextView) findViewById(R.id.tv_songsdetail_total);
        textView_current = (TextView) findViewById(R.id.tv_songsdetail_current);
        seekBar.setOnSeekBarChangeListener(this);
    }

    public void initMedia(String url) throws IOException {
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(url);
        } catch (Exception e){
            Toast.makeText(getApplication(),"出了点问题！",Toast.LENGTH_SHORT).show();
        }
        mediaPlayer.prepare();
    }

    /**
     * 加载音乐数据
     */
    public void getSongs(){
        //音乐信息json页
        String path = "http://m.kugou.com/app/i/getSongInfo.php?cmd=playInfo&hash=6CD70DCC1EC5596A105E2944A8C781E8";
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        path += id;
        //构造OkHttpClient对象和Request对象
        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        Request request = builder.get().url(path).build();
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
                    SongsDetail songsdetail = (SongsDetail) JsonUitl.stringToObject(jsonObject.toString(), SongsDetail.class);
                    Message message = new Message();
                    message.obj = songsdetail;
                    handler.sendMessage(message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    /**
     * 点击开始歌曲的监听方法
     * @param view
     */
    public void startSong(View view){
        if(!mediaPlayer.isPlaying())
            mediaPlayer.start();
        Log.d(TAG, "startSong: "+mediaPlayer.getCurrentPosition());
    }

    /**
     * 点击暂停歌曲的监听方法
     * @param view
     */
    public void pauseSong(View view){
        if(mediaPlayer.isPlaying())
            mediaPlayer.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mediaPlayer!=null){
            mediaPlayer.stop();
            mediaPlayer.release();
            handler.removeCallbacks(run);
            Log.d(TAG, "onDestroy:    ------------------------------------");
        }
    }

    /**
     * 进度条变化的监听方法，用户拖动进度条能播放歌曲制定位置
     * @param seekBar
     * @param i
     * @param b
     */
    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        if(b){
            mediaPlayer.seekTo(i);
        }
    }


    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
