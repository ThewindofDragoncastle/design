package com.dragon.patternframework.internet.user.pictureUpload

import com.dragon.patternframework.MyLog
import com.dragon.patternframework.UserRecord
import com.dragon.patternframework.internet.CommodityUpload.Model
import com.dragon.patternframework.internet.CommodityUpload.PictureUploadInterface
import com.dragon.patternframework.person.useinterface.ImageUploadInterface
import java.util.*

class PictureUploadPresenter: PictureUploadInterface {
    private lateinit var imageUploadInterface: ImageUploadInterface
    override fun setPicUpload(imageUploadInterface: ImageUploadInterface) {
        this.imageUploadInterface=imageUploadInterface
    }

        override fun info(msg: String) {
           imageUploadInterface.success()
        }

        override fun error(e: String) {
           imageUploadInterface.fail(e)
        }

        override fun connectInternet(ip: String,filename:String) {
            MyLog.i("TAG注册请求","地址：$ip")
            val map= HashMap<String,String>()
            map["useId"] = UserRecord.getInstance().id.toString()
            map["name"] = filename
            Model(ip, this).upload(true, map)
        }


        companion object {
            private val ourInstance = PictureUploadPresenter()
            fun getInstance(): PictureUploadPresenter{
                return ourInstance
            }
    }
}