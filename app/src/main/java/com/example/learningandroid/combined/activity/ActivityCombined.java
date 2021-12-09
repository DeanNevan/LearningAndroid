package com.example.learningandroid.combined.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.learningandroid.R;
import com.example.learningandroid.booklist.fragment.FragmentBookList;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class ActivityCombined extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager2 mViewPage;
    private String[] tabTitles;//tab的标题
    private int[] tabIconIDs;//tab的标题
    private List<Fragment> mDatas = new ArrayList<>();//ViewPage2的Fragment容器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combined);

        initData();
        //找到控件
        mTabLayout = findViewById(R.id.tab_layout);
        mViewPage = findViewById(R.id.view_page);

        mViewPage.setUserInputEnabled(true);

        //创建适配器
        MyViewPageAdapter mAdapter = new MyViewPageAdapter(this, mDatas);
        mViewPage.setAdapter(mAdapter);

        //TabLayout与ViewPage2联动关键代码
        new TabLayoutMediator(mTabLayout, mViewPage, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                Log.d("callback", "TabLayoutMediator.TabConfigurationStrategy.onConfigureTab");
                tab.setText(tabTitles[position]);
                tab.setIcon(tabIconIDs[position]);
            }
        }).attach();

        //ViewPage2选中改变监听
        mViewPage.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                if (position == 2){
                    mViewPage.setUserInputEnabled(false);
                }
                else{
                    mViewPage.setUserInputEnabled(true);
                }
                super.onPageSelected(position);
                Log.d("callback", "ViewPager2.OnPageChangeCallback:" + position);
            }
        });
        //TabLayout的选中改变监听
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.d("callback", "TabLayout.OnTabSelectedListener.onTabSelected");
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Log.d("callback", "TabLayout.OnTabSelectedListener.onTabUnselected");
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Log.d("callback", "TabLayout.OnTabSelectedListener.onTabReselected");
            }
        });

    }

    //初始化数据
    private void initData() {
        tabTitles = new String[]{"图书", "新闻", "卖家"};
        tabIconIDs = new int[]{R.drawable.menu_list, R.drawable.share2, R.drawable.basket};
        FragmentBookList fragmentBookList = new FragmentBookList();
        FragmentNews fragmentNews = new FragmentNews();
        FragmentSeller fragmentSeller = new FragmentSeller();
        mDatas.add(fragmentBookList);
        mDatas.add(fragmentNews);
        mDatas.add(fragmentSeller);
    }

    public void createBookListMenu(Menu menu)
    {
        for (Fragment fragment : mDatas){
            if (fragment instanceof FragmentBookList){
                ((FragmentBookList) fragment).createMenu(menu);
            }
        }
    }

}