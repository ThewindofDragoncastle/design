package com.dragon.patternframework.function.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dragon.patternframework.R
import kotlinx.android.synthetic.main.function_to_seller.*
import okhttp3.*
import java.io.IOException

/**
 * Created by 40774 on 2018/3/27.
 */
class ToSeller:Fragment()
{
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.function_to_seller, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        address.setText("http://192.168.43.135:12451")
        send.setOnClickListener { start("","") }
        post.setOnClickListener { start("name",search_edit.text.toString()) }
    }
    private fun start(head:String,data:String)
    {
        val client=OkHttpClient()
        val request= if(head.isNotEmpty())
       {
           val fromBody=FormBody.Builder().add(head,data).build()
           Request.Builder().url(address.text.toString()).post(fromBody).build()
       }
        else
       {
           Request.Builder().url(address.text.toString()).build()
       }

        client.newCall(request).enqueue(object :Callback{
            override fun onFailure(call: Call?, e: IOException?) {
                activity.runOnUiThread({
                 display.text="获取失败"
                })
            }

            override fun onResponse(call: Call?, response: Response?) {
                activity.runOnUiThread( {
                    display.text=response?.body()?.string()
                })
            }
        })
    }
}