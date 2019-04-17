package com.dragon.patternframework.commodity.setting;


import com.dragon.patternframework.commodity.support.BufferData;

import java.net.URL;

/**
 * Created by 40774 on 2017/12/4.
 */

public interface MainActivity_Setting {
    void settingUserImage(URL url);

    void settingUserBackground(URL url);

    //    底部导航栏颜色
    void settingBottomItemColor(String Active_color, String unActive_color);

    void settingBottomBackgroundColor(String color);

    //    初始化用户设置
    void initCustomerSetting(BufferData data);
}
