package com.dragon.patternframework.commodity.setting;

import android.support.v4.widget.DrawerLayout;

import com.dragon.patternframework.javaBean.Commodity;

import java.util.List;

/**
 * Created by 40774 on 2017/12/10.
 */

public interface MainPage_Setting {
    //    主活动接口
    void settingAD(List<Commodity> products);

    void settingProduct(List<Commodity> products);

    //    修改标题栏颜色
    void CustomerSettingToolbarColor(int color);

    void settingDrawerLayout(DrawerLayout drawerLayout);
}
