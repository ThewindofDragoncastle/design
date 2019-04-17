package com.dragon.patternframework.function.support

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.dragon.patternframework.MyLog
import com.dragon.patternframework.function.useinterface.NewCallback
import com.dragon.patternframework.service.UploadIntentService

class UploadPictureServiceHelper private constructor(){
    public lateinit var binder:UploadIntentService.UploadBinder
    companion object {
        private var uploadPictureServiceHelper:UploadPictureServiceHelper?=null
        fun getInstance():UploadPictureServiceHelper
        {
            if (uploadPictureServiceHelper==null)
                uploadPictureServiceHelper= UploadPictureServiceHelper()
            return uploadPictureServiceHelper as UploadPictureServiceHelper
        }
    }
     fun start(files:Array<String>,activity:Activity,callback:NewCallback)
    {
        val intent= Intent(activity, UploadIntentService::class.java)
        intent.putExtra("filesPath",files)
        activity.startService(intent)
        activity.bindService(intent,object : ServiceConnection {
            override fun onServiceDisconnected(p0: ComponentName?) {
                MyLog.d(javaClass.name,"服务绑定断开")
            }

            override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
                binder=p1 as UploadIntentService.UploadBinder
                binder.setCallBack(callback)
                MyLog.d(javaClass.name,"服务绑定成功")
            }
        }, Context.BIND_AUTO_CREATE)
    }

}