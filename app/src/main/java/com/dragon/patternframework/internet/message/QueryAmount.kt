package com.dragon.patternframework.internet.message

import com.dragon.patternframework.MyLog
import com.dragon.patternframework.internet.CommodityUpload.MessageAmount
import com.dragon.patternframework.internet.CommodityUpload.Model
import com.dragon.patternframework.javaBean.MessageData
import com.google.gson.Gson

class QueryAmount private constructor(): MessageAmount {
    private lateinit var messageAmount: com.dragon.patternframework.commodity.useinterface.MessageAmount
    override fun setMessageQuery(needPost: com.dragon.patternframework.commodity.useinterface.MessageAmount) {
      this.messageAmount=needPost
    }

    override fun connectInternet(ip: String, use_id: String) {
        val map=HashMap<String,String>()
        map["use_id"]=use_id
        Model(ip, this).upload(true,map)
    }




    override fun info(msg: String) {
        MyLog.i(javaClass.name, "从服务器获取的信息为：$msg")
        val ss = msg.replace("\"[", "[").replace("]\"", "]")
        val systemData = Gson().fromJson(ss, MessageData::class.java)
        messageAmount.success(systemData.payload)
    }

    override fun error(e: String) {
       messageAmount.fail(e)
    }

    companion object {
        private val ourInstance = QueryAmount()
        fun getInstance(): QueryAmount {
            return ourInstance
        }
    }

}