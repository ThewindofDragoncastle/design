package com.dragon.patternframework.internet.ordershow

import com.dragon.patternframework.MyLog
import com.dragon.patternframework.commoditydetail.useinterface.NeedPost
import com.dragon.patternframework.internet.CommodityUpload.Model
import com.dragon.patternframework.internet.CommodityUpload.OrderPost
import org.json.JSONObject

class OrderPostPre private constructor(): OrderPost {
    private lateinit var needPost: NeedPost
    override fun setSignControllInterface(needPost: NeedPost) {
        this.needPost=needPost
    }
    override fun connectInternet(ip: String,map:Map<String,String>) {
        MyLog.i("TAG登录请求","地址：$ip")
        map.map {
            MyLog.i("TAG登录请求","内容：${it.key} ${it.value}")
        }
        Model(ip, this).upload(true, map)
    }



    override fun info(msg: String) {
       if (JSONObject(msg).getInt("code")==101)
           needPost.fail("你已经提交过订单。请取消后重试。")
        else
           needPost.success()
    }

    override fun error(e: String) {
        needPost.fail(e)
    }

    companion object {
        private val ourInstance = OrderPostPre()
        fun getInstance(): OrderPostPre {
            return ourInstance
        }
    }

}