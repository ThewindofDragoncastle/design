package com.dragon.patternframework.internet.commodity

import com.dragon.patternframework.MyLog
import com.dragon.patternframework.UserRecord
import com.dragon.patternframework.internet.CommodityUpload.Model
import com.dragon.patternframework.internet.CommodityUpload.RemoveInterface
import com.dragon.patternframework.person.useinterface.ImageUploadInterface
import java.util.*

class RemovePresenter : RemoveInterface {
    private lateinit var imageUploadInterface: ImageUploadInterface
    override fun setRemoveSupInterface(imageUploadInterface: ImageUploadInterface) {
        this.imageUploadInterface=imageUploadInterface
    }
    override fun connectInternet(ip: String,comId:Int) {
        MyLog.i("TAG请求","地址：$ip")
        val map= HashMap<String,String>()
        map["useId"]="${UserRecord.getInstance().id}"
        map["comId"]=comId.toString()
        Model(ip, this).upload(true, map)
    }
    /*正在销售的查询*/
    override fun info(msg: String) {
        imageUploadInterface.success()
    }

    override fun error(e: String) {
        imageUploadInterface.fail(e)
    }

    companion object {
        private val ourInstance = RemovePresenter()
        fun getInstance(): RemovePresenter{
            return ourInstance
        }
    }
}