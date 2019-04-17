package com.dragon.patternframework.commodity.setting;

import com.dragon.patternframework.commodity.useinterface.TabSetting;

/**
 * Created by 40774 on 2017/12/6.
 */

public interface Setting {
    void setMainActivitySettingInterface(MainActivity_Setting settingInterface);

    void setMainPageSettingInterface(MainPage_Setting settingInterface);

    void setTabSettingInterface(TabSetting settingInterface);

    void modifyTabBackgroundColor(int color);

    void modifyToolbarColor(int color);

    void modifyTabIndicationColor(int indication_color);

    void modifyTabColor(int text_color, int indication_color);

    //        暂时不能修改
    void modifyBottomBackgroundColor(String color);

    void modifyBottomItemColor(String Active_color, String InActive_color);
}
