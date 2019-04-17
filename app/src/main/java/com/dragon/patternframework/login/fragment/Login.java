package com.dragon.patternframework.login.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.dragon.patternframework.R;
import com.dragon.patternframework.commodity.activity.MainActivity;
import com.dragon.patternframework.internet.login.LoginPresenter;
import com.dragon.patternframework.login.activity.MainPage;
import com.dragon.patternframework.login.support.Logical;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Properties;

import static android.content.Context.MODE_PRIVATE;


/**
 * Created by 40774 on 2017/9/25.
 */

public class Login extends Fragment implements View.OnClickListener,LoginControlInterface{
    private String ip;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private LoginPresenter presenter;
    private SharedPreferences  pre;
    //是否可视按钮
    private Button eye;
    //密码编辑文本
    private EditText passwordinput;
    //账户编辑文本
    private EditText accountinput;
    //图片按钮Signin
    private ImageView Signin;

    //“学生”单选框
    private RadioButton Student;
    //选择框记住密码
    private CheckBox Rememberpassword;
    //登录按钮
    private Button Login;
    //忘记密码按钮
    private Button IsunRemember;
    //是否可视密码
    private boolean Cansee = false;
    //引入碎片
//    com.example.devicemanager.fragment.Signin signinfragment=new Signin();
    //引入全局布局
    private LinearLayout mainpage;
    //将返回键隐藏
    private Button back;
    //碎片数据
    private CutoverListener cutoverListener;
    private SignIn Signinfragment;
    //返回视图
    private View v;
    ////处理账号密码发送至服务器的逻辑
    private Logical logical;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        builder=new AlertDialog.Builder(getContext()).setCancelable(false).setNegativeButton("取消",null).setMessage("登录中，请稍候。");
        presenter= LoginPresenter.Companion.getInstance();
        presenter.setLoginControllInterface(this);
        Properties pro=new Properties();
        try {
            pro.load(getResources().getAssets().open("system.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ip=pro.getProperty("login");
        pre =getActivity().getSharedPreferences("user_record", MODE_PRIVATE);
        Rememberpassword.setChecked(pre.getBoolean("isRemember",true));
        /*提取密码*/
        accountinput.setText(pre.getString("account",""));
        passwordinput.setText(pre.getString("password",""));
        /*自动登录*/
       // Login.performClick();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.login, container, false);
        mainpage = v.findViewById(R.id.main_page);

        //静态调用父类以设置监听器 减低耦合性
        cutoverListener = MainPage.cutoverListener;
        Signinfragment = MainPage.signinfragment;
        MainPage.login = this;

        back = v.findViewById(R.id.toolbarback);
        back.setVisibility(View.GONE);

        passwordinput = v.findViewById(R.id.passwordinput);

        accountinput = v.findViewById(R.id.accountinput);

        Signin = v.findViewById(R.id.Signin);
        Signin.setOnClickListener(this);
/*将学生选上主要是方便直接登陆*/
        Student = v.findViewById(R.id.student);
        Student.setOnClickListener(this);
        Student.setChecked(true);

        Rememberpassword = v.findViewById(R.id.isrememberpassword);
        Rememberpassword.setOnClickListener(this);

        eye = v.findViewById(R.id.eyes);
        eye.setOnClickListener(this);

        Login = v.findViewById(R.id.login);
        Login.setOnClickListener(this);

        IsunRemember = v.findViewById(R.id.isunremember);
        IsunRemember.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.eyes:
                DisplayPassword();
                break;
            case R.id.Signin:
                //更换碎片
                cutoverListener.cutoverfragment(Signinfragment, v, this);
                break;
            case R.id.login:
                if (accountinput.getText().toString().isEmpty())
                    accountinput.setError("字符不能为空");
                else if (accountinput.getText().toString().length()>13)
                    accountinput.setError("字符过长");
                else if (passwordinput.getText().toString().isEmpty())
                    passwordinput.setError("字符不能为空");
                else if (passwordinput.getText().toString().length()>13)
                    passwordinput.setError("字符过长");
                else
                     RiadioChecked();
                break;
        }
    }

    private void DisplayPassword() {
        //点击眼睛是否显示密码
        if (Cansee) {
            //如果可见设为密码不可见
            passwordinput.setTransformationMethod(PasswordTransformationMethod.getInstance());
            passwordinput.setSelection(passwordinput.getText().length());
            Cansee = false;
            eye.setBackgroundResource(R.drawable.eye);
        } else {
            passwordinput.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            passwordinput.setSelection(passwordinput.getText().length());
            Cansee = true;
            eye.setBackgroundResource(R.drawable.closeeye);
        }
    }

    public void RiadioChecked() {
        //如果单选框，未选择，弹出，警示
        if (Student.isChecked()) {
            presenter.connectInternet(ip,accountinput.getText().toString(),passwordinput.getText().toString());
        } else {
            new AlertDialog.Builder(getActivity())
                    .setMessage(" 请先选择身份。")
                    .setTitle("选择身份")
                    .setIcon(R.drawable.completexiaohui)
                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .show();
        }
    }

    @Override
    public void showFail(String msg) {
        if (dialog.isShowing())
            dialog.cancel();
        if (msg.equals("账号或者密码错误。"))
            accountinput.setError("账号或者密码错误。");
        else
            Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loginSuccess() {
        //            //进入下一目标
        if (dialog.isShowing())
            dialog.cancel();
            Intent intent = new Intent(getContext(), MainActivity.class);
            startActivity(intent);
            this.getActivity().finish();
    }

    @Override
    public void showWait() {
         dialog=builder.show();
    }


    @Override
    public void savePassword(@NotNull String account, @NotNull String password) {
        SharedPreferences.Editor c = pre.edit();
        if (Rememberpassword.isChecked()) {
            c.putBoolean("isRemember",true);
            c.putString("account", account);
            c.putString("password", password);
        }
        else
        {
            c.putBoolean("isRemember",false);
            c.putString("account", "");
            c.putString("password", "");
        }
        c.apply();
    }

    @Override
    public void onResume() {
        super.onResume();
        /*提取密码*/
        accountinput.setText(pre.getString("account",""));
        passwordinput.setText(pre.getString("password",""));
    }
}
