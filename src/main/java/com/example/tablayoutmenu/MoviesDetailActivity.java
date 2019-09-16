package com.example.tablayoutmenu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tablayoutmenu.util.AsyncImageLoader;

/**
 * 电影详情类

 通过上个页面传值来显示

 * @author thinkdoor
 */
public class MoviesDetailActivity extends AppCompatActivity {

    //日志
    private static final String TAG = "MoviesDetailActivity";

    /*
    创建属性，UI、网络
     */
    private TextView textView_title,textView_style,textView_duration,textView_rate;
    private ImageView imageView;

    /**
     * 创建视图
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Log.d(TAG, "onCreate: -------------");
        init();
    }

    public void init(){
        /*
        获取bundle数据
         */
        Bundle bundle = getIntent().getExtras();
        String title = bundle.getString("title");
        String img = bundle.getString("img");
        String style = bundle.getString("style");
        String rate = bundle.getString("rate");
        String duration = bundle.getString("duration");
        alt = bundle.getString("alt");
        /*
        初始化View
         */
        textView_title = (TextView) findViewById(R.id.tv_moviedetail_title);
        textView_style = (TextView) findViewById(R.id.tv_moviedetail_style);
        textView_rate = (TextView) findViewById(R.id.tv_moviedetail_rate);
        textView_duration = (TextView) findViewById(R.id.tv_moviedetail_duration);
        imageView = (ImageView) findViewById(R.id.iv_moviedetail);
        textView_title.setText(title);
        textView_style.setText(style);
        textView_rate.setText(rate);
        textView_duration.setText(duration);
        /*
        用异步图片装载工具类装载图片
         */
        AsyncImageLoader asyncImageLoader = new AsyncImageLoader(getApplication());
        asyncImageLoader.asyncloadImage(imageView,img);
    }

    String alt;

    public void jumpToMoive(View view){
        Intent intent = new Intent();
        intent.putExtra("alt",alt);
        Log.d(TAG, "onCreate:  -------------------"+alt);
        intent.setClass(this,MoiveSelfActivity.class);
        startActivity(intent);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
