package com.dragon.patternframework.internet.intention

import com.dragon.patternframework.MyLog
import com.dragon.patternframework.commoditydetail.useinterface.ComIdPresenterIm
import com.dragon.patternframework.internet.CommodityUpload.Model
import com.dragon.patternframework.internet.CommodityUpload.comIdIntention11
import com.dragon.patternframework.javaBean.IntentionData
import com.google.gson.Gson
import java.util.*

class AllPresenter : comIdIntention11 {
    private lateinit var comIdPresenter: ComIdPresenterIm
    override fun setPicUpload(comId:ComIdPresenterIm) {
        this.comIdPresenter=comId
    }


    override fun connectInternet(ip:String) {
        MyLog.i("TAG请求","地址：$ip")
        val map= HashMap<String,String>()
        Model(ip, this).upload(false, map)
    }
    /*正在销售的查询*/
    override fun info(msg: String) {
        MyLog.i(javaClass.name, "从服务器获取的信息为：$msg")
        var ss = msg.replace("\"[", "[").replace("]\"", "]")
        ss = ss.replace("\"{", "{").replace("}\"", "}")
        val systemData = Gson().fromJson(ss, IntentionData::class.java)
        val commodities = systemData.payload
        if (commodities!=null)
          comIdPresenter.success(commodities)
    }

    override fun error(e: String) {
        comIdPresenter.fail(e)
    }

    companion object {
        private val ourInstance = AllPresenter()
        fun getInstance(): AllPresenter{
            return ourInstance
        }
    }
}