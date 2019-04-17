package com.dragon.patternframework.internet.commodity

import com.dragon.patternframework.MyLog
import com.dragon.patternframework.commoditydetail.useinterface.ComIdPresenterIm3
import com.dragon.patternframework.internet.CommodityUpload.Model
import com.dragon.patternframework.internet.CommodityUpload.returnIDCom
import com.dragon.patternframework.javaBean.SystemData
import com.google.gson.Gson
import java.util.*

class IDComPresenter : returnIDCom {
    private lateinit var im3: ComIdPresenterIm3
    override fun connectInternet(ip: String, int: Int) {
        val map= HashMap<String,String>()
        map["comId"]="$int"
        Model(ip, this).upload(true, map)
    }

    override fun setMainPageSettingInterface(settingInterface: ComIdPresenterIm3) {
        this.im3=settingInterface
    }

    /*正在销售的查询*/
    override fun info(msg: String) {
        MyLog.i(javaClass.name, "从服务器获取的信息为：$msg")
        val ss = msg.replace("\"[", "[").replace("]\"", "]")
        val systemData = Gson().fromJson(ss, SystemData::class.java)
//        new Gson().fromJson(msg ,new TypeToken<List<String>>() {}.getType());
        val commodities = systemData.payload
        /*防止出现空异常*/
        im3.Im3Sucess(commodities[0])
        if (commodities.size != 0)
            MyLog.i(javaClass.name, "商品信息为：" + commodities[0].comLabel)
    }

    override fun error(e: String) {
        im3.fail(e)
    }

    companion object {
        private val ourInstance = IDComPresenter()
        fun getInstance(): IDComPresenter{
            return ourInstance
        }
    }
}