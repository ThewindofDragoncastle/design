package com.dragon.patternframework.internet.login

import com.dragon.patternframework.MyLog
import com.dragon.patternframework.UserRecord
import com.dragon.patternframework.internet.CommodityUpload.Login
import com.dragon.patternframework.internet.CommodityUpload.Model
import com.dragon.patternframework.javaBean.LoginData
import com.dragon.patternframework.login.fragment.LoginControlInterface
import com.google.gson.Gson
import org.json.JSONObject
import java.util.*

class LoginPresenter  private constructor(): Login {
    private lateinit var loginControlInterface: LoginControlInterface
    override fun info(msg: String) {
        MyLog.i("TAG登录请求","登录：$msg")
        var ss = msg.replace("\"[", "[").replace("]\"", "]")
        //print("接收成功！${ss}")
        ss = ss.replace("\"{", "{").replace("}\"", "}")
        val isSuccess = JSONObject(ss).getBoolean("success")
        if (isSuccess) {
            val com = Gson().fromJson(ss, LoginData::class.java)
            val user=com.payload
            loginControlInterface.savePassword(user.account,user.password)
            loginControlInterface.loginSuccess()
            val userRecord=UserRecord.getInstance()
            userRecord.account=user.account
            userRecord.nickname=user.nickname
            userRecord.address=user.address
            userRecord.image=user.image
            userRecord.id=user.id
            userRecord.sign=user.sign
            userRecord.qq=user.qq
            userRecord.email=user.email
            userRecord.comId=user.comId
            userRecord.intentionId=user.intentionId
            userRecord.isSel=user.isSel
            userRecord.phone=user.phone
            userRecord.ordId=user.ordId
            userRecord.trueName=user.trueName
            userRecord.school=user.school
            userRecord.profession=user.profession
        } else {
            loginControlInterface.showFail("账号或者密码错误。")
        }
    }

    override fun error(e: String) {
       loginControlInterface.showFail(e)
    }

    override fun connectInternet(ip: String,account:String,password:String) {
       MyLog.i("TAG登录请求","地址：$ip")
        val map=HashMap<String,String>()
        map["account"] = account
        map["password"]=password
        loginControlInterface.showWait()
        Model(ip, this).upload(true, map)
    }

    override fun setLoginControllInterface(loginControlInterface: LoginControlInterface) {
        this.loginControlInterface=loginControlInterface
    }
    companion object {
        private val ourInstance = LoginPresenter()
        fun getInstance(): LoginPresenter {
            return ourInstance
        }
    }

}