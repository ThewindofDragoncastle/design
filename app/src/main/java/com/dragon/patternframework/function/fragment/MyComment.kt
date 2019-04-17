package com.dragon.patternframework.function.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.dragon.patternframework.GetIP
import com.dragon.patternframework.R
import com.dragon.patternframework.UserRecord
import com.dragon.patternframework.commoditydetail.activity.CommodityDetailDisplay
import com.dragon.patternframework.commoditydetail.adapter.CommodityCommentReclyerViewAdapter
import com.dragon.patternframework.commoditydetail.data.CommentUser
import com.dragon.patternframework.commoditydetail.useinterface.ComIdPresenterIm
import com.dragon.patternframework.commoditydetail.useinterface.ComIdPresenterIm3
import com.dragon.patternframework.function.useinterface.MyComment
import com.dragon.patternframework.internet.commodity.IDComPresenter
import com.dragon.patternframework.internet.intention.MyCommentPresenter
import com.dragon.patternframework.javaBean.Commodity
import com.dragon.patternframework.javaBean.CommodityIntention
import kotlinx.android.synthetic.main.person_intention.*
import java.net.URL
import java.util.*

/**
 * Created by 40774 on 2018/3/27.
 */
class MyComment:Fragment(),MyComment, ComIdPresenterIm, ComIdPresenterIm3 {
    override fun Im3Sucess(com: Commodity) {
        val intent= Intent(context, CommodityDetailDisplay::class.java)
        intent.putExtra("commodity",com)
        context.startActivity(intent)
    }

    val ls = mutableListOf<CommentUser>()
    private val presenter= MyCommentPresenter.getInstance()
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.person_intention, container, false)
        return view
    }
    override fun success(commodityIntentions: List<CommodityIntention>) {
        intent_menu.visibility = View.VISIBLE
        intent_circleView?.visibility = View.GONE
        ls.clear()
        commodityIntentions.map {
            val cu = CommentUser()
            cu.comId= it.com_id.toInt()
            cu.isSure = true
            cu.nickname = it.nickName
            cu.content =it.message
            cu.comname=it.com_name
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
        intent_menu.adapter.notifyDataSetChanged()
    }

    override fun fail(s: String) {
        Toast.makeText(context,"失败！$s", Toast.LENGTH_SHORT).show()
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter.setBack(this)
        val map=HashMap<String,String>()
        map["useId"]=UserRecord.getInstance().id.toString()
        presenter.connectInternet(GetIP.getIp(context,"myIntention"),map)
        order_toolbar.visibility=View.GONE
        val idComPresenter= IDComPresenter.getInstance()
        idComPresenter.setMainPageSettingInterface(this)
        intent_menu.adapter = CommodityCommentReclyerViewAdapter(context, ls, intent_action_button,true)
        intent_menu.layoutManager = LinearLayoutManager(context)
        intent_action_button.setOnClickListener {
            /*悬浮键 ：滑动到第一个*/
            intent_menu.smoothScrollToPosition(0)
        }
        intent_menu.visibility = View.GONE
    }
}