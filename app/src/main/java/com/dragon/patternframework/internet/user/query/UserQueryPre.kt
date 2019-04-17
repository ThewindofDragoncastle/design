package com.dragon.patternframework.internet.user.query

import com.dragon.patternframework.MyLog
import com.dragon.patternframework.UserRecord
import com.dragon.patternframework.internet.CommodityUpload.Model
import com.dragon.patternframework.internet.CommodityUpload.UserQuery
import com.dragon.patternframework.javaBean.LoginData
import com.dragon.patternframework.person.useinterface.UserQueryInterface
import com.google.gson.Gson
import java.util.*

class UserQueryPre : UserQuery {
    private lateinit var userQueryInterface: UserQueryInterface
    override fun setUserQuery(userQueryInterface: UserQueryInterface) {
        this.userQueryInterface=userQueryInterface
    }
    override fun info(msg: String) {
        var ss = msg.replace("\"[", "[").replace("]\"", "]")
        print("接收成功！$ss")
        ss = ss.replace("\"{", "{").replace("}\"", "}")
        val pro= Gson().fromJson(ss,LoginData::class.java)
        userQueryInterface.success(pro.payload)
    }

    override fun error(e: String) {
        userQueryInterface.fail(e)
    }

    override fun connectInternet(ip: String) {
        MyLog.i("TAG注册请求","地址：$ip")
        val map= HashMap<String,String>()
        map["useId"] = UserRecord.getInstance().id.toString()
        Model(ip, this).upload(true, map)
    }


    companion object {
        private val ourInstance = UserQueryPre()
        fun getInstance(): UserQueryPre{
            return ourInstance
        }
    }
}