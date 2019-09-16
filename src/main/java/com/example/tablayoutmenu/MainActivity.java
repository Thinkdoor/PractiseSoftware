package com.example.tablayoutmenu;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

/**
 * 登录后的主页面
 * 前提：TabLayout依赖的design包
 * 属性：①TabLayout、ViewPager
 * 操作： 1.初始化一个适配器类
 * 1.  定义标题和页面列表
 * 2.  把三个Fragment添加到列表中
 * 3.  用列表和标题创建适配器
 * 2. 进行关联操作
 * 1.  ViewPager添加适配器
 * 2.  TabLayout与ViewPager关联
 *
 * @author thinkdoor
 */

public class MainActivity extends AppCompatActivity {

    /*
    定义UI组件
     */
    private TabLayout tabLayout;
    private ViewPager viewPager;

    /**
     * 创建的回调方法
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化界面组件
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.view_pager);

        //获取初始化的适配器
        MyFragmentPagerAdapter myFragmentPagerAdapter = init();

        //添加适配器
        viewPager.setAdapter(myFragmentPagerAdapter);

        //设置tablayou和viewpage关联
        tabLayout.setupWithViewPager(viewPager);
    }

    /**
     * 初始化适配器
     *
     * @return：初始化后的适配器
     */
    public MyFragmentPagerAdapter init() {
        //菜单标题
        String[] title = {"新闻", "音乐", "电影"};

        //创建装载Fragment的列表
        List<Fragment> fragmentlist;

        /*
            初始化列表，并把创建的三个Fragment页面添加到列表中
         */
        fragmentlist = new ArrayList<>();
        fragmentlist.add(new FragmentOne());
        fragmentlist.add(new FragmentTwo());
        fragmentlist.add(new FragmentThree());

        //创建Fragment适配器
        MyFragmentPagerAdapter myFragmentPagerAdapter;

        //适配器进行适配,传入列表与标题
        myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),
                fragmentlist, title);
        return myFragmentPagerAdapter;
    }
}
