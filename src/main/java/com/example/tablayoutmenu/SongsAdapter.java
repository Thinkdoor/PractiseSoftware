package com.example.tablayoutmenu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tablayoutmenu.model.SongsInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by k42 on 2019-06-19.
 */

public class SongsAdapter extends BaseAdapter{

    private List<SongsInfo> list = new ArrayList<>();
    //LayoutInflater将布局文件实例化为View对象
    private LayoutInflater layoutInflater;

//    private AsyncImageLoader asyncImageLoader;

    public SongsAdapter(List<SongsInfo> list, Context context){
        this.list = list;
        layoutInflater = LayoutInflater.from(context);
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
        View view = layoutInflater.inflate(R.layout.songs_item,null);
        //从布局取textview
        TextView textView = (TextView)view.findViewById(R.id.textView_songs);
        //取当前需要显示的对象
        SongsInfo songsInfo = list.get(position);
        //给textview赋值
        textView.setText(songsInfo.getFilename());
        return view;
    }
}
