package com.dragon.patternframework.commodity.support;


import com.dragon.patternframework.commodity.setting.MainActivity_Setting;
import com.dragon.patternframework.commodity.setting.MainPage_Setting;
import com.dragon.patternframework.commodity.setting.Setting;
import com.dragon.patternframework.commodity.useinterface.TabSetting;

/**
 * Created by 40774 on 2017/12/6.
 */

public class BufferData implements Setting {
    //    由于设置活动未开启时 数据无法暂时存储所以利用这个类进行数据缓存
//    各碎片活动实例
    private MainActivity_Setting MainActivity_setting;
    private TabSetting tabSetting;
    private MainPage_Setting mainPage_setting;
    private static final BufferData ourInstance = new BufferData();

    public static BufferData getInstance() {
        return ourInstance;
    }

    private BufferData() {
    }

    @Override
    public void setMainActivitySettingInterface(MainActivity_Setting settingInterface) {
        MainActivity_setting = settingInterface;
    }

    @Override
    public void setMainPageSettingInterface(MainPage_Setting settingInterface) {
        this.mainPage_setting = settingInterface;
    }

    @Override
    public void setTabSettingInterface(TabSetting settingInterface) {
        this.tabSetting = settingInterface;
    }

    @Override
    public void modifyTabBackgroundColor(int color) {
        tabSetting.CustomerSetTabBackgroundColor(color);
    }

    @Override
    public void modifyToolbarColor(int color) {
        mainPage_setting.CustomerSettingToolbarColor(color);
    }

    @Override
    public void modifyTabIndicationColor(int indication_color) {
        tabSetting.CustomerSetIndicationColor(indication_color);
    }

    @Override
    public void modifyTabColor(int select_color, int indication_color) {
        tabSetting.CustomerSetTabColor(select_color, indication_color);
    }

    @Override
    public void modifyBottomBackgroundColor(String color) {
        MainActivity_setting.settingBottomBackgroundColor(color);
    }

    @Override
    public void modifyBottomItemColor(String Active_color, String InActive_color) {
        MainActivity_setting.settingBottomItemColor(Active_color, InActive_color);
    }

}
