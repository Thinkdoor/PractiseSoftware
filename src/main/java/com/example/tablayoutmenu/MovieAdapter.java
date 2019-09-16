package com.example.tablayoutmenu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tablayoutmenu.model.MovieItem;
import com.example.tablayoutmenu.model.NewsInfo;
import com.example.tablayoutmenu.util.AsyncImageLoader;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by k42 on 2019-06-19.
 */

public class MovieAdapter extends BaseAdapter{

    private List<MovieItem> list;
    //LayoutInflater将布局文件实例化为View对象
    private LayoutInflater layoutInflater;

    private AsyncImageLoader asyncImageLoader;

    public MovieAdapter(List<MovieItem> list, Context context){
        this.list = list;
        layoutInflater = LayoutInflater.from(context);
        this.asyncImageLoader = new AsyncImageLoader(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //将布局文件实例化为View对象
        View view = layoutInflater.inflate(R.layout.movie_item,null);
        //从布局取textview
        TextView textView = view.findViewById(R.id.tv_movie_title);
        ImageView imageView = (ImageView)view.findViewById(R.id.iv_movie);
        TextView textView1 = view.findViewById(R.id.tv_movie_rate);
        TextView textView2 = view.findViewById(R.id.tv_movie_time);
        //取当前需要显示的对象
        MovieItem movieItem = list.get(position);

        //给textview赋值
        textView.setText(movieItem.getTitle());
        textView1.setText(movieItem.getRating().getAverage());
        String duration = "";
        for(int i = 0;i<movieItem.getDurations().length;i++){
            duration+=movieItem.getDurations()[i]+" ";
        }
        textView2.setText(duration);
        duration = null;
        //加载图片
        asyncImageLoader.asyncloadImage(imageView, movieItem.getImages().getMedium());
        return view;
    }
}
