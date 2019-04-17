package com.dragon.patternframework.internet.ordershow

import com.dragon.patternframework.MyLog
import com.dragon.patternframework.internet.CommodityUpload.Model
import com.dragon.patternframework.internet.CommodityUpload.SignIn
import com.dragon.patternframework.login.fragment.SignControlInterface
import java.util.*

class NotCompleteOrderQueryPre : SignIn {
    private lateinit var changeUrgent: SignControlInterface;
    override fun info(msg: String) {

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
        private val ourInstance = NotCompleteOrderQueryPre()
        fun getInstance(): NotCompleteOrderQueryPre{
            return ourInstance
        }
    }
}