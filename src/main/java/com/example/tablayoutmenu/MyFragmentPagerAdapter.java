package com.example.tablayoutmenu;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * fragment的适配器类
 * 1.集成FragmentPagerAdapter，实现相关方法
 * 2.创建需要显示的List集合
 * 3.重写getPageTitle设置标题
 *
 * @author thinkdoor
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    //装载fragment的列表
    private List<Fragment> list;

    //标题
    private String[] title;

    /**
     * 构造方法
     *
     * @param fm
     * @param list：装载fragment的列表
     * @param title：标题栏
     */
    public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> list, String[] title) {
        super(fm);
        this.list = list;
        this.title = title;
    }

    /**
     * 返回当前位置的fragment
     *
     * @param position：当前页面的位置
     * @return
     */
    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    /**
     * 获取list中fragment的个数
     *
     * @return
     */
    @Override
    public int getCount() {
        return list.size();
    }

    /**
     * 返回当前的标题
     *
     * @param position：当前页面的位置
     * @return
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}
