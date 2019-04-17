package com.dragon.patternframework.internet.ordershow

import com.dragon.patternframework.MyLog
import com.dragon.patternframework.UserRecord
import com.dragon.patternframework.internet.CommodityUpload.AllOrderQuery
import com.dragon.patternframework.internet.CommodityUpload.Model
import com.dragon.patternframework.javaBean.OrderData
import com.dragon.patternframework.person.useinterface.AllOrderInterface
import com.google.gson.Gson
import java.util.*

class AllOrderQueryPre : AllOrderQuery {
    override fun setAllOrder(allOrder: AllOrderInterface) {
        changeUrgent=allOrder
    }

    private lateinit var changeUrgent: AllOrderInterface
    override fun info(msg: String) {
        MyLog.i("TAG查询所有订单请求","信息：$msg")
        var ss = msg.replace("\"[", "[").replace("]\"", "]")
        print("接收成功！${ss}")
        ss = ss.replace("\"{", "{").replace("}\"", "}")
        val orderData=Gson().fromJson(ss,OrderData::class.java)
        changeUrgent.success(orderData.payload)
    }

    override fun error(e: String) {
        changeUrgent.fail(e)
    }

    override fun connectInternet(ip: String) {
        MyLog.i("TAG注册请求","地址：$ip")
        val map= HashMap<String,String>()
        map["useId"]="${UserRecord.getInstance().id}"
        Model(ip, this).upload(true, map)
    }


    companion object {
        private val ourInstance = AllOrderQueryPre()
        fun getInstance(): AllOrderQueryPre{
            return ourInstance
        }
    }
}