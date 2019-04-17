package com.dragon.patternframework.commodity.fragment;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.support.annotation.Nullable;
import android.util.Log;

import com.dragon.patternframework.R;
import com.dragon.patternframework.commodity.support.BufferData;


/**
 * Created by 40774 on 2017/12/6.
 */

public class CustomerSetting extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
    private BufferData bufferData = BufferData.getInstance();
    final private String TAG = getClass().getName();
    private Activity activity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting);
        activity = getActivity();
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
//        设置改变时更新界面
//        可是有的时候我们如果希望更改后立马被系统获知选项已被更改，又该如何？
// 复写继承了PreferenceActivity的类中的onPreferenceTreeClick方法，这个方法会在设置修改时触发。


//        补充：点击某个Preference控件后，会先回调onPreferenceChange()方法，即是否保存值，然后再回            调onPreferenceClick以及onPreferenceTreeClick()方法，因此在onPreferenceClick/onPreferenceTreeClick
//        方法中我们得到的控件值就是最新的Preference控件值。
        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }

//    @Override
//    public boolean onPreferenceChange(Preference preference, Object o) {
////        说明：  当Preference的元素值发送改变时，触发该事件。
////        返回值：true  代表将新值写入sharedPreference文件中。
////        false 则不将新值写入sharedPreference文件
//        return true;
//    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("custom_setting_theme")) {
            String data = sharedPreferences.getString("custom_setting_theme", null);
            Log.d(TAG, "para:" + data);
            if (data != null) {
//                如果不为空加载预设数据
                setThemeColor(data, "#ffffff");
                activity.finish();
            }
        }
    }

    public void setThemeColor(String MainColor, String selectcolor) {
        int IntMainColor = Color.parseColor(MainColor);
        int Intselectcolor = Color.parseColor(selectcolor);
        bufferData.modifyTabBackgroundColor(Intselectcolor);
        bufferData.modifyTabColor(Color.BLACK, IntMainColor);
        bufferData.modifyToolbarColor(IntMainColor);
        bufferData.modifyTabIndicationColor(IntMainColor);

        bufferData.modifyBottomItemColor(MainColor, "#000000");
        bufferData.modifyBottomBackgroundColor("#ffffff");
    }
}
