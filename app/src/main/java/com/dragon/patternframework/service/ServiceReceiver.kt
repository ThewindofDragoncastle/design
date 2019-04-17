package com.dragon.patternframework.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class ServiceReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
      Toast.makeText(context,"取消成功！",Toast.LENGTH_SHORT).show()
       val binder= intent.getSerializableExtra("binder") as UploadIntentService.UploadBinder
        binder.cancel()
    }
}
