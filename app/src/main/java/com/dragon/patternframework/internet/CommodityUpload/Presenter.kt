package com.dragon.patternframework.internet.CommodityUpload

import com.dragon.patternframework.UserRecord
import com.dragon.patternframework.javaBean.Commodity
class Presenter private constructor(private val view: View):ComUpload {
    companion object {
        private lateinit var model: Model
        fun getInstance(ip: String,view: View): Presenter {
            val pre=Presenter(view)
            model = Model(ip,pre)
            return pre
        }
    }
    override fun connectInternet(com:Commodity) {
        val map=HashMap<String,String>()
        map["deal"] = com.deal
        map["notice"] = com.notice
        map["phone"] = com.phone
        map["images"] = com.images
        map["label"] = com.comLabel
        map["note"] = com.note
        map["status"] = "${com.comStatus}"
        map["qq"] = com.qq
        map["price"] = com.price.toString()
        map["email"] = com.email
        map["amount"] = com.amount.toString()
        map["tab"] = com.tab.toString()
        map["isDispatch"] = com.isDispatch.toString()
        map["isBargain"] = com.isBargain.toString()
        map["user"] =UserRecord.getInstance().id.toString()
        model.upload(true,map)
    }

    override fun info(msg: String) {
       view.showMsg(msg)
    }

    override fun error(e: String) {
        view.showError(e)
    }
}