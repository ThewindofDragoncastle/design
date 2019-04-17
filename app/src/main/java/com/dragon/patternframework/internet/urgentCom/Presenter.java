package com.dragon.patternframework.internet.urgentCom;

import com.dragon.patternframework.MyLog;
import com.dragon.patternframework.commodity.setting.MainActivity_Setting;
import com.dragon.patternframework.commodity.useinterface.ChangeUrgent;
import com.dragon.patternframework.internet.CommodityUpload.Model;
import com.dragon.patternframework.internet.CommodityUpload.UrgentQuery;
import com.dragon.patternframework.javaBean.Commodity;
import com.dragon.patternframework.javaBean.SystemData;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;

/**
 * Created by 40774 on 2017/12/5.
 */

public class Presenter implements UrgentQuery {
    public static final Presenter ourInstance = new Presenter();
    public static Presenter getInstance() {
        return ourInstance;
    }

    private ChangeUrgent urgent;

    private Presenter() {
    }

    private MainActivity_Setting setting;
    @Override
    public void info(@NotNull String msg) {
        MyLog.i(getClass().getName(),"滚动广告从服务器获取的信息为："+msg);
        String ss=msg.replace("\"[","[").replace("]\"","]");
        SystemData systemData=new Gson().fromJson(ss, SystemData.class);
        List<Commodity> commodities= systemData.getPayload();
        urgent.setProduct(commodities);
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
    public void setChangeUrgentInterface(@NotNull ChangeUrgent changeUrgent) {
        urgent=changeUrgent;
    }
}
