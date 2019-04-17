package com.dragon.patternframework.internet.setting;

import android.util.Log;

import com.dragon.patternframework.GetIP;
import com.dragon.patternframework.UserRecord;
import com.dragon.patternframework.commodity.setting.MainActivity_Setting;
import com.dragon.patternframework.commodity.useinterface.PresenterSetting;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by 40774 on 2017/12/5.
 */

public class Presenter implements PresenterSetting {
    public static final Presenter ourInstance = new Presenter();

    public static Presenter getInstance() {
        return ourInstance;
    }

    private Presenter() {
    }

    private MainActivity_Setting setting;

    @Override
    public void start() {
        //启动
//        伪代码
        settingCustomerBackground(null);
        settingCustomerCircleImageView(null);
    }

    @Override
    public void settingMainActivity(MainActivity_Setting setting) {
        this.setting=setting;
    }


    @Override
    public void settingCustomerCircleImageView(URL url) {
        try {
            String image= UserRecord.getInstance().getImage();
            if (image==null)
               setting.settingUserImage(new URL("http://172.16.3.7/headp/1.jpeg"));
            else
                setting.settingUserImage(new URL(image));
        } catch (MalformedURLException e) {
            Log.d("", "url未能发现");
        }
    }

    @Override
    public void settingCustomerBackground(URL url) {
        try {
            setting.settingUserBackground(new URL("http://172.16.3.7/headb/b002.jpg"));
        } catch (MalformedURLException e) {
            Log.d("", "url未能发现");
        }
    }
}
