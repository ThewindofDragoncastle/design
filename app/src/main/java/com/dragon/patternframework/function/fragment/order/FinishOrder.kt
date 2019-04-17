package com.dragon.patternframework.function.fragment.order

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.dragon.patternframework.GetIP
import com.dragon.patternframework.R
import com.dragon.patternframework.internet.ordershow.AllOrderQueryPre
import com.dragon.patternframework.javaBean.OrderAndCommodity
import com.dragon.patternframework.person.adapter.OrderItemAdapter
import com.dragon.patternframework.person.useinterface.AllOrderInterface
import kotlinx.android.synthetic.main.order.*

/**
 * Created by WYL on 2018/2/8.
 */
class FinishOrder :Fragment(), AllOrderInterface {
    private lateinit var present:AllOrderQueryPre
    private val orderAndCommodities= mutableListOf<OrderAndCommodity>()
    override fun fail(e: String) {
        Toast.makeText(context, "查询失败！$e", Toast.LENGTH_SHORT).show()
    }

    override fun success(orderAndCommodity:List<OrderAndCommodity>) {
        this.orderAndCommodities.clear()
        this.orderAndCommodities.addAll(orderAndCommodity)
        order_menu.adapter.notifyDataSetChanged()
        order_menu?.visibility = View.VISIBLE
        order_circleView?.visibility = View.GONE
    }
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.order,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        /*进入这个碎片可能是三种加载方式*/
        super.onActivityCreated(savedInstanceState)
        present= AllOrderQueryPre.getInstance()
        present.setAllOrder(this)
        present.connectInternet(GetIP.getIp(context, "CompleteOrderQuery"))
        viewFinish()
    }

    private fun viewFinish()
    {
        /*隐藏标题栏*/
        order_toolbar.visibility=View.GONE
        order_menu.adapter= OrderItemAdapter(context,1, orderAndCommodities)
        order_menu.layoutManager=LinearLayoutManager(context)
        order_menu.visibility=View.GONE
    }
}