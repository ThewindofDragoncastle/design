package com.dragon.patternframework.internet.CommodityUpload

import android.util.Log
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request

class Model ( val ip:String,val presenter: PresenterCallBack){
    private val ob=object : Observer<String>{
        override fun onComplete() {}
        override fun onSubscribe(d: Disposable) {}
        override fun onNext(t: String) {
            Log.i(javaClass.name,"回调线程onNext:$t"+Thread.currentThread().name)
            presenter.info(t)
        }
        override fun onError(e: Throwable) {
            Log.i(javaClass.name,"error:"+Thread.currentThread().name)
            presenter.error("连接服务器异常！")
        }
    }
//    地址从上层中加载
    fun upload(isPost:Boolean,name:Map<String,String>)
    {
        Observable.create<String> { e ->
            val client=OkHttpClient()
            val request= if (isPost){
                val  post=FormBody.Builder()
                name.map {
                    post.add(it.key,it.value)
                }
                Request.Builder().url(ip).post(post.build()).build()
            }
            else
                Request.Builder().url(ip).build()
            val response=client.newCall(request).execute()
             if (response.isSuccessful)
                 {
                        val msg=response?.body()?.string()
                        Log.i(javaClass.name,"回应线程:$msg"+Thread.currentThread().name)
                        e.onNext(msg!!)
                        e.onComplete()
                        response.close()
                 }
            else
             {
                 e.onError(Throwable("网络连接失败"))
                 response.close()
             }
            /*这里只是过路的会 回调此处*/
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(ob)
    }
}