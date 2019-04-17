package com.dragon.patternframework.commodity.useinterface;


import com.dragon.patternframework.commodity.setting.MainActivity_Setting;

import java.net.URL;

/**
 * Created by 40774 on 2017/12/5.
 */

public interface PresenterSetting {

    void start();

    void settingMainActivity(MainActivity_Setting setting);

    //    设置图片
    void settingCustomerCircleImageView(URL url);

    void settingCustomerBackground(URL url);
}
