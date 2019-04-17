package com.dragon.patternframework.internet.intention

import com.dragon.patternframework.MyLog
import com.dragon.patternframework.internet.CommodityUpload.Model
import com.dragon.patternframework.internet.CommodityUpload.MyComment
import com.dragon.patternframework.javaBean.IntentionData
import com.google.gson.Gson

class MyCommentPresenter :MyComment{
    private lateinit var myComment: com.dragon.patternframework.function.useinterface.MyComment
    override fun setBack(myComment: com.dragon.patternframework.function.useinterface.MyComment) {
        this.myComment=myComment
    }

    override fun connectInternet(ip: String, map: Map<String, String>) {
        Model(ip, this).upload(true, map)
    }

    /*正在销售的查询*/
    override fun info(msg: String) {
        MyLog.i(javaClass.name, "从服务器获取的信息为：$msg")
        var ss = msg.replace("\"[", "[").replace("]\"", "]")
        ss = ss.replace("\"{", "{").replace("}\"", "}")
        val systemData = Gson().fromJson(ss, IntentionData::class.java)
        val commodities = systemData.payload
        myComment.success(commodities)
    }

    override fun error(e: String) {
       myComment.fail(e)
    }

    companion object {
        private val ourInstance = MyCommentPresenter()
        fun getInstance(): MyCommentPresenter{
            return ourInstance
        }
    }
}