package com.dragon.patternframework.login.activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.dragon.patternframework.MyLog;
import com.dragon.patternframework.R;
import com.dragon.patternframework.login.fragment.CutoverListener;
import com.dragon.patternframework.login.fragment.Login;
import com.dragon.patternframework.login.fragment.SignIn;


public class MainPage extends AppCompatActivity {
    private final String TAG = " MainPage: ";
    //actionbar工具
    private Toolbar logintoolbar;
    public static Login login;
    public static SignIn signinfragment = new SignIn();
    public static CutoverListener cutoverListener;
    private LinearLayout mainpage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //预先实例化接口 以免碎片调用接口方法出现空指针异常
        cutoverListener = new CutoverListener() {
            @Override
            public void cutoverfragment(Fragment fragment, View view, Fragment removeframent) {
                MyLog.d(TAG, "回调监听，切换碎片");
                mainpage.removeView(view);
                replacefragment(fragment, removeframent);
            }
        };

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        mainpage = findViewById(R.id.main_page);
        //引入自定义toolbar
        logintoolbar = findViewById(R.id.logintoolbar);
        setSupportActionBar(logintoolbar);
    }

    public void replacefragment(Fragment addfragment, Fragment removeframent) {
        //在事务中移除原本碎片 增加待添加的碎片
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.framereplace, addfragment);
        fragmentTransaction.remove(removeframent);
        fragmentTransaction.commit();
    }
}
