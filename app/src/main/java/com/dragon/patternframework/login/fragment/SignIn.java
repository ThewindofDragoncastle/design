package com.dragon.patternframework.login.fragment;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dragon.patternframework.R;
import com.dragon.patternframework.internet.login.SignInPresenter;
import com.dragon.patternframework.login.activity.MainPage;

import java.io.IOException;
import java.util.Properties;

import static android.content.Context.MODE_PRIVATE;


/**
 * Created by 40774 on 2017/9/25.
 */

public class SignIn extends Fragment implements View.OnClickListener ,SignControlInterface{
    private String ip;
    private SignInPresenter presenter;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private CheckBox RememberPassword;
    private Fragment login;
    private EditText pa1;
    private EditText pa2;
    private EditText account;
    //判断是否点击了“是，我已阅读”，然后对chebox设置，只有点了是才勾
    private CutoverListener cutoverListener;
    private View v;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        builder=new AlertDialog.Builder(getContext()).setCancelable(false).setNegativeButton("取消",null).setMessage("连接网络中，请稍候。");
        presenter= SignInPresenter.Companion.getInstance();
        presenter.setSignControllInterface(this);
        Properties pro=new Properties();
        try {
            pro.load(getResources().getAssets().open("system.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ip=pro.getProperty("signin");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.signin, container, false);
        ImageView signin = v.findViewById(R.id.Signin);
        //将注册图标设为不可见
        signin.setVisibility(View.GONE);
        cutoverListener = MainPage.cutoverListener;
        login = MainPage.login;

        Button back = v.findViewById(R.id.toolbarback);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(this);
        pa1=v.findViewById(R.id.passwordinputagin);
        pa2=v.findViewById(R.id.passwordinput);
        account=v.findViewById(R.id.accountinput);
        TextView titletoolbar = v.findViewById(R.id.toolbartitle);

        Button signinbutton = v.findViewById(R.id.login);

        RememberPassword = v.findViewById(R.id.isrememberpassword);
        RememberPassword.setText("我已同意注册条款");
        RememberPassword.setOnClickListener(this);

        Button isunRemember = v.findViewById(R.id.isunremember);
        isunRemember.setVisibility(View.GONE);
        //注册监听
        signinbutton.setText("确认注册");
        signinbutton.setOnClickListener(this);
        //标题栏显示
        titletoolbar.setText("欢迎注册");
        ActionBar actionBar = getActivity().getActionBar();
        if (actionBar != null)
            actionBar.setDisplayShowHomeEnabled(true);
        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                check();
                break;
            case R.id.isrememberpassword:
                //只有在选择时才弹出这个对话
                if (RememberPassword.isChecked()) {
                    RememberPassword.setChecked(false);
                    AlertDialog dialog = new AlertDialog.Builder(getContext())
                            .setTitle("注册条款须知")
                            .setMessage("   是否已经阅读注册条例？")
                            .setPositiveButton("是，我已阅读", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    RememberPassword.setChecked(true);
                                }
                            })
                            .setNegativeButton("否，立即阅读", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    RememberPassword.setChecked(false);
                                }
                            })
                            .show();
                }
                break;
            //返回活动
            case R.id.toolbarback:
                cutoverListener.cutoverfragment(login, v, this);
                break;
        }
    }
    @Override
    public void showFail(String msg) {
        if (dialog.isShowing())
            dialog.cancel();
        Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void signSuccess() {
        //            //进入下一目标
        if (dialog.isShowing())
            dialog.cancel();
        Toast.makeText(getContext(),"注册成功！返回登录界面。",Toast.LENGTH_SHORT).show();
        SharedPreferences pre=getActivity().getSharedPreferences("user_record", MODE_PRIVATE);
        SharedPreferences.Editor c = pre.edit();
        String p=pa1.getText().toString();
        String u=account.getText().toString();
        /*将数据存储 以方便打开登录页面直接填充*/
        c.putBoolean("isRemember",true);
        c.putString("account", u);
        c.putString("password",p);
        c.apply();
        pa1.setText("");
        pa2.setText("");
        account.setText("");
        cutoverListener.cutoverfragment(login, v, this);
    }

    @Override
    public void showWait() {
        dialog=builder.show();
    }
    private void check()
    {
        String ac=account.getText().toString();
        String ps1=pa1.getText().toString();
        String ps2=pa2.getText().toString();
        if (ac.isEmpty())
            account.setError("账号不能为空");
        else if (ac.length()<6)
            account.setError("账号长度过短");
        else if (ac.length()>13)
            account.setError("账号长度过长");
       else if (ps1.isEmpty())
            pa1.setError("密码不能为空");
        else if (ps1.length()<6)
            pa1.setError("账号长度过短");
        else if (ps1.length()>13)
            pa1.setError("账号长度过长");
        else if (!ps2.equals(ps1))
            pa2.setError("密码不一致");
        else if (!RememberPassword.isChecked())
            Toast.makeText(getContext(),"请同意使用协议。",Toast.LENGTH_SHORT).show();
        else
            presenter.connectInternet(ip,ac,ps1);
    }
}
