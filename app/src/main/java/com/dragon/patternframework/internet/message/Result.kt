package com.dragon.patternframework.internet.message

import com.dragon.patternframework.MyLog
import com.dragon.patternframework.internet.CommodityUpload.MessageResult
import com.dragon.patternframework.internet.CommodityUpload.Model

class Result private constructor() : MessageResult {
    private lateinit var messageResult: com.dragon.patternframework.commodity.useinterface.MessageResult
    override fun setMessageQuery(needPost: com.dragon.patternframework.commodity.useinterface.MessageResult) {
        this.messageResult = needPost
    }


    override fun connectInternet(ip: String, map: Map<String, String>) {
        MyLog.i("TAG登录请求", "地址：$ip")
        Model(ip, this).upload(true, map)
    }


    override fun info(msg: String) {
        messageResult.success()
    }

    override fun error(e: String) {
      messageResult.fail(e)
    }

    companion object {
        private val ourInstance = Result()
        fun getInstance(): Result {
            return ourInstance
        }
    }

}