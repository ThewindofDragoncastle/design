package com.dragon.patternframework.commodity.mainfragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.dragon.patternframework.GetIP;
import com.dragon.patternframework.R;
import com.dragon.patternframework.UserRecord;
import com.dragon.patternframework.commodity.fragment.GlideAD;
import com.dragon.patternframework.commodity.fragment.TabMerchandise;
import com.dragon.patternframework.commodity.setting.MainPage_Setting;
import com.dragon.patternframework.commodity.support.BufferData;
import com.dragon.patternframework.commodity.useinterface.MessageAmount;
import com.dragon.patternframework.function.activity.FunctionMainActivity;
import com.dragon.patternframework.function.enumclass.FragmentIdentify;
import com.dragon.patternframework.internet.commodity.Presenter;
import com.dragon.patternframework.internet.message.QueryAmount;
import com.dragon.patternframework.javaBean.Commodity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by 40774 on 2017/12/9.
 */

public class MainPage extends android.support.v4.app.Fragment implements NavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener, MainPage_Setting,MessageAmount {
    private Toolbar mtoolbar;
    private ImageView toobar_person;
    //标题栏搜索
    private ImageView toolbar_search;
    //用户搜索输入
    private EditText toolbar_editText;

    private final String TAG = getClass().getName();
    //滚动广告碎片
    private GlideAD glideAD;
    //    标签碎片
    private TabMerchandise tabMerchandise;
    //    活动传来的布局
    private DrawerLayout drawerLayout;
    private ImageView red;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mainpage_fragment, container, false);
        initUI(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//      设置接口 利于中间层调用
//        传入BUfferData
        BufferData bufferData = BufferData.getInstance();
        bufferData.setMainPageSettingInterface(this);
        String color =
                PreferenceManager.getDefaultSharedPreferences(getContext()).getString("custom_setting_theme", null);
        if (color != null) {
            bufferData.modifyToolbarColor(Color.parseColor(color));
        }
        connInternetForCom();
        connInternetForGlideAd();
        QueryAmount queryAmount= QueryAmount.Companion.getInstance();
        queryAmount.setMessageQuery(this);
        queryAmount.connectInternet(GetIP.getIp(getContext(),"messageAmount"), UserRecord.getInstance().getId()+"");
    }

    public void initUI(View view) {
        mtoolbar = view.findViewById(R.id.toolbar);
        toobar_person = view.findViewById(R.id.toolbar_person);
        red=view.findViewById(R.id.red_point);
//因为碎片包括了这两个碎片
//        getChildFragmentManager(). 必须使用这个方法
        glideAD = (GlideAD) getChildFragmentManager().findFragmentById(R.id.fragment_glidead);
        tabMerchandise = (TabMerchandise) getChildFragmentManager().findFragmentById(R.id.fragment_mall_tab);
        toolbar_search = view.findViewById(R.id.toolbar_search);
        toolbar_editText = view.findViewById(R.id.toolbar_edittext);
        ImageView toolbar_message = view.findViewById(R.id.toolbar_message);
        toolbar_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 /*增加一个从点击首页消息查询进入的界面 跳转至功能页面 传入消息页面的请求*/
                Intent intent = new Intent(getContext(), FunctionMainActivity.class);
                intent.putExtra("type", FragmentIdentify.ALL_MESSAGE.getLabel());
                getActivity().startActivity(intent);
                red.setVisibility(View.GONE);
            }
        });
        toolbar_editText.setOnClickListener(this);
        toobar_person.setOnClickListener(this);
        toolbar_search.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        toolbar_editText.clearFocus();
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(toolbar_editText.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    public void CustomerSettingToolbarColor(int color) {
        mtoolbar.setBackgroundColor(color);
    }

    @Override
    public void settingDrawerLayout(DrawerLayout drawerLayout) {
        this.drawerLayout = drawerLayout;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_person:
                drawerLayout.openDrawer(Gravity.START);
                break;
            case R.id.toolbar_search:
                /*跳转搜索页面*/
                gotoSearch();
                break;
            case R.id.toolbar_edittext:
                gotoSearch();
                break;
        }
    }

    private void gotoSearch() {
       /*跳转搜索页面*/
        Intent in = new Intent(getContext(), FunctionMainActivity.class);
        in.putExtra("type", FragmentIdentify.COMMODITY_SEARCH.getLabel());
        startActivity(in);
    }

    @Override
    public void settingAD(final List<Commodity> products1) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        glideAD.setImage(products1);
                    }
                });
            }
        }).start();

    }

    @Override
    public void settingProduct(List<Commodity> products) {
        tabMerchandise.setProduct(products);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
/*联网查询广告*/
   private void connInternetForCom()
   {
       //    中间变量
       Presenter presenter= Presenter.getInstance();
       presenter.setMainPageSettingInterface(this);
       presenter.connectInternet(GetIP.getIp(getContext(),"allcommodity"));
   }
    /*联网查询商品*/
    private void connInternetForGlideAd()
    {
        //        伪代码 网络获取
        com.dragon.patternframework.internet.glideAdQuery.Presenter presenter;
        presenter = com.dragon.patternframework.internet.glideAdQuery.Presenter.getInstance();
        presenter.setMainPageSettingInterface(this);
        presenter.connectInternet(GetIP.getIp(getContext(),"queryglideAd"));
    }
    @Override
    public void success(int amount) {
        /*添加角标*/
        if (amount>0)
            red.setVisibility(View.VISIBLE);
    }

    @Override
    public void fail(@NotNull String e) {
        Toast.makeText(getContext(),"错误！"+e.toString(),Toast.LENGTH_SHORT).show();
    }
}
