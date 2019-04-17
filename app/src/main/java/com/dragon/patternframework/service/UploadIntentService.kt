package com.dragon.patternframework.service

import android.app.IntentService
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Binder
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.support.v7.app.NotificationCompat
import android.widget.Toast
import com.dragon.patternframework.MyLog
import com.dragon.patternframework.R
import com.dragon.patternframework.function.useinterface.NewCallback
import com.dragon.patternframework.internet.PictureUpload.PresenterCallBack
import com.dragon.patternframework.internet.PictureUpload.Upload
import java.io.Serializable
import java.util.*


class UploadIntentService : IntentService("图片上传服务"), PresenterCallBack {
    private val up=Upload.getInstance(this)
    private var callBack: NewCallback? =null
    private var intent:Intent?=null
    override fun cancel() {
        val handler = Handler(Looper.getMainLooper())
        handler.post(Runnable { Toast.makeText(baseContext,"上传取消",Toast.LENGTH_SHORT).show() })
        stopForeground(true)
        stopSelf()
        onUnbind(intent)
    }
    fun cancelUpload()
    {
        up.cancel()
    }

    override fun complete() {
        val handler = Handler(Looper.getMainLooper())
        callBack?.complete()
        handler.post(Runnable { Toast.makeText(baseContext,"上传完成",Toast.LENGTH_SHORT).show() })
        stopForeground(true)
        stopSelf()
        onUnbind(intent)
    }

    override fun fail() {
        val handler = Handler(Looper.getMainLooper())
        handler.post(Runnable { Toast.makeText(baseContext,"上传失败",Toast.LENGTH_SHORT).show() })
        stopForeground(false)
        getNotificationManager().notify(1, getNotification("上传失败", -1))
        stopSelf()
        onUnbind(intent)
    }

    override fun progressChange(order:Int,progress: Int) {
        getNotificationManager().notify(1, getNotification("第$order 图片上传中...", progress))
    }

    private val binder=UploadBinder()
    override fun onHandleIntent(intent: Intent?) {
        MyLog.i(javaClass.name,"当前线程：${Thread.currentThread().name}")
        this.intent=intent
        startForeground(1, getNotification("上传中...", 0))
        val pro= Properties()
        pro.load(baseContext.resources.assets.open("system.properties"))
        val ip=pro.getProperty("uploadpicture")
        up.start(intent?.getStringArrayExtra("filesPath"),ip)
    }

    override fun onBind(intent: Intent): IBinder? {
        return binder
    }
  inner class UploadBinder : Binder(),Serializable
    {
        fun setCallBack(newCallback: NewCallback)
        {
            callBack=newCallback
        }
        fun cancel()
        {
            cancelUpload()
        }
    }

    private fun getNotificationManager(): NotificationManager {
        return getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }
    private fun getNotification(title: String, progress: Int): Notification {
        val builder = NotificationCompat.Builder(this)
        val intent=Intent("com.example.uiwidgettest.FORWARDMUSIC")
        intent.putExtra("binder",binder)
        builder.setContentTitle(title).setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher))
                .setContentIntent(PendingIntent.getBroadcast(this, 2,
                        intent, FLAG_UPDATE_CURRENT))
        if (progress >= 0) {
            builder.setContentText("当前进度：$progress%")
            builder.setProgress(100, progress, false)
        }
        return builder.build()
    }
}
