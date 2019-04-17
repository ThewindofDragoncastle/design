package com.dragon.patternframework.internet.CommodityUpload

import android.app.AlertDialog
import android.content.DialogInterface
import com.dragon.patternframework.function.fragment.newFragment.Gather
import com.dragon.patternframework.javaBean.Commodity
import java.util.*

class View constructor(val new:Gather) {
    private var presenter :Presenter
    private lateinit var com:Commodity
    private val alertDialog:AlertDialog.Builder
    init {
        val pro= Properties()
        pro.load(new.context.resources.assets.open("system.properties"))
        val ip=pro.getProperty("newcommodity")
        alertDialog=AlertDialog.Builder(new.context).setPositiveButton("返回") { p0, p1 ->
            new.activity.finish() }
        presenter =Presenter.getInstance(ip,this)
    }
    //    视图
      fun start(com: Commodity)
    {
        this.com=com
      presenter.connectInternet(com)
    }
    fun showError(e:String){
        alertDialog.setNegativeButton("重新连接",DialogInterface.OnClickListener{
            dialog,which->
            dialog.cancel()
            start(com)
        }).setMessage(e).show()
    }
    fun showMsg(msg:String){
        alertDialog.setNegativeButton("确认",DialogInterface.OnClickListener{
            dialog,which->
            dialog.cancel()
        }).setMessage(msg).show()
    }
}