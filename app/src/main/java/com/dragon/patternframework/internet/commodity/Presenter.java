package com.dragon.patternframework.internet.commodity;

import com.dragon.patternframework.MyLog;
import com.dragon.patternframework.commodity.setting.MainPage_Setting;
import com.dragon.patternframework.internet.CommodityUpload.ComQuery;
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

public class Presenter implements ComQuery {
    public static final Presenter ourInstance = new Presenter();
    private MainPage_Setting settingInterface = null;

    public static Presenter getInstance() {
        return ourInstance;
    }

    private Presenter() {
    }

    @Override
    public void info(@NotNull String msg) {
        MyLog.i(getClass().getName(), "从服务器获取的信息为：" + msg);
        String ss = msg.replace("\"[", "[").replace("]\"", "]");
        SystemData systemData = new Gson().fromJson(ss, SystemData.class);
//        new Gson().fromJson(msg ,new TypeToken<List<String>>() {}.getType());
        List<Commodity> commodities = systemData.getPayload();
        returnProducts(commodities);
        /*防止出现空异常*/
        if (commodities.size() != 0)
            MyLog.i(getClass().getName(), "商品信息为：" + commodities.get(0).getComLabel());
    }

    @Override
    public void error(@NotNull String e) {

    }

    @Override
    public void connectInternet(String ip) {
        //        启动
//        伪代码
        new Model(ip, this).upload(false, new HashMap<String, String>());
    }

    @Override
    public void returnProducts(@NotNull List<Commodity> products) {
        settingInterface.settingProduct(products);
    }

    @Override
    public void setMainPageSettingInterface(@NotNull MainPage_Setting settingInterface) {
        this.settingInterface = settingInterface;
    }
}
