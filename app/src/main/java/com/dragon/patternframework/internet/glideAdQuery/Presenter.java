package com.dragon.patternframework.internet.glideAdQuery;

import com.dragon.patternframework.MyLog;
import com.dragon.patternframework.commodity.setting.MainActivity_Setting;
import com.dragon.patternframework.commodity.setting.MainPage_Setting;
import com.dragon.patternframework.internet.CommodityUpload.GlideAdQuery;
import com.dragon.patternframework.internet.CommodityUpload.Model;
import com.dragon.patternframework.javaBean.Commodity;
import com.dragon.patternframework.javaBean.SystemData;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;

/**
 * Created by 40774 on 2017/12/5.
 */

public class Presenter implements GlideAdQuery {
    public static final Presenter ourInstance = new Presenter();
    public static Presenter getInstance() {
        return ourInstance;
    }

    private MainPage_Setting mainPage_setting;

    private Presenter() {
    }

    private MainActivity_Setting setting;
    @Override
    public void info(@NotNull String msg) {
          MyLog.i(getClass().getName(),"滚动广告从服务器获取的信息为："+msg);
          String ss=msg.replace("\"[","[").replace("]\"","]");
          SystemData systemData=new Gson().fromJson(ss, SystemData.class);
          List<Commodity> commodities= systemData.getPayload();
          returnADProducts(commodities);
    }

    @Override
    public void error(@NotNull String e) {

    }

    @Override
    public void connectInternet(String ip) {
        //        启动
//        伪代码
        new Model(ip,this).upload(false, new HashMap<String,String>());
    }

    @Override
    public void returnADProducts(@NotNull List<Commodity> products) {
        mainPage_setting.settingAD(products);
    }

    @Override
    public void setMainPageSettingInterface(@NotNull MainPage_Setting settingInterface) {
        this.mainPage_setting=settingInterface;
    }
}
