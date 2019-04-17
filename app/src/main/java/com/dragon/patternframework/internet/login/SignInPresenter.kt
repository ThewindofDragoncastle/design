package com.dragon.patternframework.internet.login

import com.dragon.patternframework.MyLog
import com.dragon.patternframework.internet.CommodityUpload.Model
import com.dragon.patternframework.internet.CommodityUpload.SignIn
import com.dragon.patternframework.javaBean.LoginData
import com.dragon.patternframework.login.fragment.SignControlInterface
import com.google.gson.Gson
import org.json.JSONObject
import java.util.*

class SignInPresenter:SignIn {
    private lateinit var changeUrgent: SignControlInterface;
    override fun info(msg: String) {
        MyLog.i("TAG注册结果","信息：$msg")
        var ss= msg.replace("\"[","[").replace("]\"","]")
        print("接收成功！$ss")
        ss= ss.replace("\"{","{").replace("}\"","}")
        val isSuccess = JSONObject(ss).getBoolean("success")
        if (isSuccess) {
            val com = Gson().fromJson(ss, LoginData::class.java)
            changeUrgent.signSuccess()
            changeUrgent.showFail("注册成功！你的默认昵称为：${com.payload.nickname}。")
        } else {
            changeUrgent.showFail("此账号已经被使用。")
        }
    }

    override fun error(e: String) {
       changeUrgent.showFail(e)
    }

    override fun connectInternet(ip: String,account:String,password:String) {
        MyLog.i("TAG注册请求","地址：$ip")
        val map= HashMap<String,String>()
        map["account"] = account
        map["password"]=password
        changeUrgent.showWait()
        Model(ip, this).upload(true, map)
    }

    override fun setSignControllInterface(changeUrgent: SignControlInterface) {
        this.changeUrgent=changeUrgent
    }

    companion object {
        private val ourInstance = SignInPresenter()
        fun getInstance(): SignInPresenter{
            return ourInstance
        }
    }
}