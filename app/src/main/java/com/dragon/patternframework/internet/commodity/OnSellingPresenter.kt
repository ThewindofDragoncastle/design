package com.dragon.patternframework.internet.commodity

import com.dragon.patternframework.MyLog
import com.dragon.patternframework.UserRecord
import com.dragon.patternframework.function.useinterface.RemoveSup
import com.dragon.patternframework.internet.CommodityUpload.Model
import com.dragon.patternframework.internet.CommodityUpload.OnSellingQuery
import com.dragon.patternframework.javaBean.SystemData
import com.google.gson.Gson
import java.util.*

class OnSellingPresenter : OnSellingQuery {
    private lateinit var removeSup: RemoveSup
    override fun setRemoveSupInterface(removeSup: RemoveSup) {
        this.removeSup=removeSup
    }

    override fun connectInternet(ip: String) {
        MyLog.i("TAG请求","地址：$ip")
        val map= HashMap<String,String>()
        map["useId"]="${UserRecord.getInstance().id}"
        Model(ip, this).upload(true, map)
    }
    /*正在销售的查询*/
    override fun info(msg: String) {
        MyLog.i(javaClass.name, "从服务器获取的信息为：$msg")
        val ss = msg.replace("\"[", "[").replace("]\"", "]")
        val systemData = Gson().fromJson(ss, SystemData::class.java)
//        new Gson().fromJson(msg ,new TypeToken<List<String>>() {}.getType());
        val commodities = systemData.payload
        removeSup.success(commodities)
        /*防止出现空异常*/
        if (commodities.size != 0)
            MyLog.i(javaClass.name, "商品信息为：" + commodities[0].comLabel)
    }

    override fun error(e: String) {
        removeSup.fail(e)
    }

    companion object {
        private val ourInstance = OnSellingPresenter()
        fun getInstance(): OnSellingPresenter{
            return ourInstance
        }
    }
}