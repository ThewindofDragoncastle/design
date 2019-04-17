package com.dragon.patternframework.commodity.activity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dragon.patternframework.R;
import com.dragon.patternframework.commodity.adapter.FragmentAdapter;
import com.dragon.patternframework.commodity.mainfragment.MainPage;
import com.dragon.patternframework.commodity.setting.MainActivity_Setting;
import com.dragon.patternframework.commodity.support.BufferData;
import com.dragon.patternframework.internet.setting.Presenter;
import com.dragon.patternframework.person.activity.InfoActivity;
import com.dragon.patternframework.person.mainpage.AllOrder;
import com.dragon.patternframework.person.mainpage.Intention;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
        , MainActivity_Setting, BottomNavigationBar.OnTabSelectedListener {
    private NavigationView mnavigationView;
    //用户界面
    private DrawerLayout drawerLayout;
    private CircleImageView circle_view;

    private ImageView background;
    private final String TAG = getClass().getName();
    private ViewPager pager;
    List<Fragment> mainPages;
    //    底部导航栏
    private BottomNavigationBar navigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
        //        伪代码 网络获取
        BufferData data = BufferData.getInstance();
        data.setMainActivitySettingInterface(this);
        initCustomerSetting(data);
        mainPages = new ArrayList<>();
        MainPage mainPage = new MainPage();
        mainPage.settingDrawerLayout(drawerLayout);
        mainPages.add(mainPage);
        mainPages.add(new Intention());
        mainPages.add(new AllOrder());
        mainPages.add(new com.dragon.patternframework.person.mainpage.MainPage());
        List<String> stringList = new ArrayList<>();
//        碎片名字
        stringList.add("推荐");
        stringList.add("评论");
        stringList.add("订单");
        stringList.add("功能");
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), mainPages, stringList);
        pager.setAdapter(adapter);
        Presenter presenter1= Presenter.getInstance();
        presenter1.settingMainActivity(this);
        presenter1.start();

    }

    public void initUI() {
        mnavigationView = findViewById(R.id.customer_navigation);
        drawerLayout = findViewById(R.id.drawer_layout);

        //    获取用户界面视图
        View view = mnavigationView.getHeaderView(0);
        background = view.findViewById(R.id.customer_background);
        circle_view = view.findViewById(R.id.custom_circle_ImageView);
        navigationBar = findViewById(R.id.bottom_navigation_bar);
        pager = findViewById(R.id.Main_viewpager);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            /*页面同底部栏一起移动*/
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                navigationBar.selectTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    public void setNavigationView() {
//        菜单颜色加载
        ColorStateList colorStateList =
                (ColorStateList) getResources().getColorStateList(R.color.navigation_menu);
        mnavigationView.setItemTextColor(colorStateList);
        mnavigationView.setNavigationItemSelectedListener(this);
    }

    public void setBottomBar() {
        BottomNavigationItem item1 = new BottomNavigationItem(R.drawable.sysn, "推荐");
        BottomNavigationItem item2 = new BottomNavigationItem(R.drawable.comment, "评论");
        BottomNavigationItem item3 = new BottomNavigationItem(R.drawable.sysn, "订单");
        BottomNavigationItem item4 = new BottomNavigationItem(R.drawable.fun_pin, "功能");
        navigationBar.addItem(item1);
        navigationBar.addItem(item2);
        navigationBar.addItem(item3);
        navigationBar.addItem(item4);
        navigationBar.setMode(BottomNavigationBar.MODE_DEFAULT);
        navigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        navigationBar.setTabSelectedListener(this);
        navigationBar.initialise();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_setting:
                if (item.isChecked())
                    item.setChecked(false);
                else {
//                    item.setChecked(true);
                    drawerLayout.closeDrawers();
                    Intent intent = new Intent(this, SettingActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.navigation_test:
                if (item.isChecked())
                    item.setChecked(false);
                else {
                    item.setChecked(true);
                    Intent intent = new Intent(this, InfoActivity.class);
                    startActivity(intent);
                }
                break;
        }
        return true;
    }


    @Override
    public void settingUserImage(URL url) {
        RequestOptions options = new RequestOptions().placeholder(R.drawable.loading)
                .error(R.drawable.noload);
        Glide.with(this).load(url).apply(options).into(circle_view);
    }

    @Override
    public void settingUserBackground(URL url) {
        RequestOptions options = new RequestOptions().placeholder(R.drawable.loading)
                .error(R.drawable.noload);
        Glide.with(this).load(url).apply(options).into(background);
    }

    @Override
    public void settingBottomItemColor(String Active_color, String unActive_color) {

        Log.i(TAG, "Active_color=" + Active_color + " unActive_color" + unActive_color);
//        选择颜色
        navigationBar.setActiveColor(Active_color);
//        未选中颜色
        navigationBar.setInActiveColor(unActive_color);
    }

    @Override
    public void settingBottomBackgroundColor(String color) {
        navigationBar.setBarBackgroundColor(color);
    }

    @Override
    public void initCustomerSetting(BufferData data) {

        settingBottomItemColor("#0066cc", "#000000");
        //        设置用户自定义颜色 或者默认
        String color =
                PreferenceManager.getDefaultSharedPreferences(this).getString("custom_setting_theme", null);
        if (color != null) {
            //       设置默认颜色
//            背景白色
            settingBottomBackgroundColor("#FFFFFF");

        } else {
            settingBottomBackgroundColor("#FFFFFF");
        }
        setNavigationView();
        setBottomBar();
    }

    @Override
    public void onTabSelected(int position) {
        pager.setCurrentItem(position);
    }

    @Override
    public void onTabUnselected(int position) {
    }

    @Override
    public void onTabReselected(int position) {
    }


}
