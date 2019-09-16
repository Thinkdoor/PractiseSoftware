package com.example.tablayoutmenu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tablayoutmenu.model.NewsInfo;
import com.example.tablayoutmenu.util.AsyncImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 新闻适配器类

 1.因为是List的适配器，所以继承BaseAdapter，并实现必要方法
 2.创建需要的List集合
 3.重写getView方法实现适配界面

 * @author thinkdoor
 */

public class NewsAdapter extends BaseAdapter{

    private List<NewsInfo> list;
    //LayoutInflater将布局文件实例化为View对象
    private LayoutInflater layoutInflater;

    private AsyncImageLoader asyncImageLoader;

    public NewsAdapter(List<NewsInfo> list, Context context){
        this.list = list;
        layoutInflater = LayoutInflater.from(context);
        this.asyncImageLoader = new AsyncImageLoader(context);
    }

    public NewsAdapter(List<NewsInfo> list1,List<NewsInfo> list2, Context context){
        this.list = mergeList(list1,list2);
        layoutInflater = LayoutInflater.from(context);
        this.asyncImageLoader = new AsyncImageLoader(context);
    }
    /**
     * 两个列表合并的方法
     *
     * @param list1
     * @param list2
     * @return
     */
    public List<NewsInfo> mergeList(List<NewsInfo> list1,List<NewsInfo> list2){
        List<NewsInfo> list = new ArrayList<>();
        //遍历列表1，并添加到列表3中
        for(int i = 0;i < list1.size();i++){
            NewsInfo newsInfo = list1.get(i);
            list.add(newsInfo);
        }
        //遍历列表2，并把String类型的image转化成String[]类型，然后添加到列表3中
        for(int i = 0;i < list2.size();i++){
            NewsInfo newsInfo = list2.get(i);
            newsInfo.setImages(new String[]{newsInfo.getImage()});
            list.add(newsInfo);
        }
        return list;
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
        View view = layoutInflater.inflate(R.layout.news_item,null);
        //从布局取textview
        TextView textView = (TextView)view.findViewById(R.id.textView);
        ImageView imageView = (ImageView)view.findViewById(R.id.imageView);
        //取当前需要显示的对象
        NewsInfo newsInfo = list.get(position);
        //给textview赋值
        textView.setText(newsInfo.getTitle());
        //加载图片
        asyncImageLoader.asyncloadImage(imageView, newsInfo.getImages()[0]);
        return view;
    }
}
