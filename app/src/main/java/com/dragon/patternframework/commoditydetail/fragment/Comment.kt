package com.dragon.patternframework.commoditydetail.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.dragon.patternframework.GetIP
import com.dragon.patternframework.R
import com.dragon.patternframework.UserRecord
import com.dragon.patternframework.commoditydetail.activity.CommodityDetailDisplay
import com.dragon.patternframework.commoditydetail.adapter.CommodityCommentReclyerViewAdapter
import com.dragon.patternframework.commoditydetail.data.CommentUser
import com.dragon.patternframework.commoditydetail.useinterface.ComIdPresenterIm
import com.dragon.patternframework.commoditydetail.useinterface.ComIdPresenterIm2
import com.dragon.patternframework.internet.intention.ComIdPresenter
import com.dragon.patternframework.internet.intention.PostBigInPresenter
import com.dragon.patternframework.javaBean.Commodity
import com.dragon.patternframework.javaBean.CommodityIntention
import kotlinx.android.synthetic.main.cd_comment_salerword.*
import kotlinx.android.synthetic.main.cd_commodity_comment.*
import java.net.URL


class Comment : Fragment(), ComIdPresenterIm, ComIdPresenterIm2 {
    private  lateinit var com:Commodity

    val ls = mutableListOf<CommentUser>()
    private val presenter= ComIdPresenter.getInstance()
    private val presenterIm2= PostBigInPresenter.getInstance()
    override fun success() {
        Toast.makeText(context,"评论成功！",Toast.LENGTH_SHORT).show()
    }
    override fun success(commodityIntentions: List<CommodityIntention>) {
        ls.clear()
     commodityIntentions.map {
//         var timeYYD: String = "2017-12-12",
//         var timeHHMM: String = "23:59"
         val cu = CommentUser()
             cu.isSure = true
             cu.nickname = it.nickName
             cu.content =it.message
         if (it.cTime!=null)
         {
             cu.timeYYD="${it.cTime.year}-${it.cTime.month}-${it.cTime.day}"
             cu.timeHHMM="${it.cTime.hours}:${it.cTime.seconds}"
         }

         if (it.images!=null)
         cu.headSculpture= URL(it.images)
         else
             cu.headSculpture= URL("http://39.108.123.220/Customer/headprotrait/q002.jpg")
         ls.add(cu)
      }
        ls.reverse()
        cd_comment_recyclerView.adapter.notifyDataSetChanged()
    }

    override fun fail(s: String) {
       Toast.makeText(context,"失败！$s",Toast.LENGTH_SHORT).show()
    }

    /*评论*/
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.cd_commodity_comment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        com=(activity as CommodityDetailDisplay).commodity
        presenter.setPicUpload(this)
        presenterIm2.setPicUpload(this)
        presenter.connectInternet(GetIP.getIp(context,"comIntention"),com.id.toInt())
        cd_comment_recyclerView.adapter = CommodityCommentReclyerViewAdapter(context, ls, cd_action_button,false)
        cd_comment_recyclerView.layoutManager = LinearLayoutManager(context)
        cd_action_button.setOnClickListener {
            /*悬浮键 ：滑动到第一个*/
            cd_comment_recyclerView.smoothScrollToPosition(0)
        }
        button2.setOnClickListener { showDialog() }
        content.text=com.user.nickname
        textView8.text=com.notice
        /* 屏幕实际大小为这样：
       隐藏输入文本框 视图 由于子控件拦截了分发 所以对整个视图进行监听显得不是很有必要：
       在xml文件中进行控制 android:descendantFocusability=”  afterDescendants”*/
    }
    private fun showDialog() {
        val  view =LayoutInflater.from(context).inflate(R.layout.revise_dialog, ConstraintLayout(context),false)
        val title1= view.findViewById<TextView>(R.id.revise_dialog_title)
        val edit= view.findViewById<TextView>(R.id.revise_dialog_edit)
        val save= view.findViewById<TextView>(R.id.revise_dialog_save)
        val cancel= view.findViewById<TextView>(R.id.revise_dialog_cancel)
        title1.text= "评论"
        val dialog= AlertDialog.Builder(context).setView(view).show()
        save.setOnClickListener {
            val cu = CommentUser()
            val user = UserRecord.getInstance()
            cu.isSure = true
            cu.content =edit.text.toString()
            cu.nickname = user.nickname
            if (user.image!=null)
                cu.headSculpture= URL(user.image)
            else
                cu.headSculpture= URL("http://39.108.123.220/Customer/headprotrait/q002.jpg")
            ls.add(0,cu)
            cd_comment_recyclerView.adapter.notifyDataSetChanged()
            /*同时请求网络保存评论*/
            val map=HashMap<String,String>()
            map["accountAmount"]=cu.likeAmount.toString()
            map["msg"]=cu.content
            map["useId"]=UserRecord.getInstance().id.toString()
            map["comId"]=com.id.toString()
            presenterIm2.connectInternet(GetIP.getIp(context,"saveIntention"), map)
            dialog.cancel()
        }
        cancel.setOnClickListener {
            dialog.cancel()
        }
    }
}