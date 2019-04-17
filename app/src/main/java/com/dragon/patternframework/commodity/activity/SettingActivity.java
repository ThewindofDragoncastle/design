package com.dragon.patternframework.commodity.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.dragon.patternframework.R;
import com.dragon.patternframework.commodity.fragment.CustomerSetting;


public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        android.app.FragmentTransaction transaction=getFragmentManager().beginTransaction();
        transaction.replace(android.R.id.content,new CustomerSetting());
        transaction.commit();
    }
}
