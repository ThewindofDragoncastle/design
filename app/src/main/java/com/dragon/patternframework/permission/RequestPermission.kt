package com.dragon.patternframework.permission

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.support.v4.app.ActivityCompat
import android.widget.Toast
import com.dragon.patternframework.function.activity.FunctionMainActivity

/**
 * Created by WYL on 2018/2/1.
 */
class RequestPermission {
    companion object {
        /*拨打电话的权限*/
        /*访问存储服务*/
        fun requestSd(activity: Activity):Boolean{
            if (!isGoOn())
                if (ActivityCompat.checkSelfPermission(activity,
                                android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    activity.requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 0)
                    return true
                }
            else
                {
                    return false
                }
            return false
        }

        fun onRequestPermissionsResult(activity: Activity, requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
            when (requestCode) {
                0 -> {
                    if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        if(activity is FunctionMainActivity)
                        {
                          activity.replaceNew()
                        }
                    } else {
                        Toast.makeText(activity, "拒绝权限将无法使用此功能。", Toast.LENGTH_SHORT).show()
                        activity.finish()
                    }

                }
            }
        }

        private fun isGoOn(): Boolean {
            return Build.VERSION.SDK_INT < 23
        }
    }

}