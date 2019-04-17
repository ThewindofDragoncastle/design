package com.dragon.patternframework.internet.message

import com.dragon.patternframework.MyLog
import com.dragon.patternframework.internet.CommodityUpload.MessageQuery
import com.dragon.patternframework.internet.CommodityUpload.Model
import com.dragon.patternframework.javaBean.MessageQueryData
import com.google.gson.Gson

class Query private constructor(): MessageQuery {
    private lateinit var messageQuery:com.dragon.patternframework.commodity.useinterface.MessageQuery
    override fun connectInternet(ip: String, use_id: String) {
        MyLog.i("TAG登录请求","地址：$ip")
        val map=HashMap<String,String>()
        map["use_id"]=use_id
        Model(ip, this).upload(true, map)
    }

    override fun setMessageQuery(needPost: com.dragon.patternframework.commodity.useinterface.MessageQuery) {
      this.messageQuery=needPost
    }

    override fun info(msg: String) {
        MyLog.i(javaClass.name, "从服务器获取的信息为：$msg")
        val ss = msg.replace("\"[", "[").replace("]\"", "]")
        val systemData = Gson().fromJson(ss, MessageQueryData::class.java)
        messageQuery.success(systemData.payload)
    }

    override fun error(e: String) {
        messageQuery.fail(e)
    }

    companion object {
        private val ourInstance = Query()
        fun getInstance(): Query {
            return ourInstance
        }
    }

}