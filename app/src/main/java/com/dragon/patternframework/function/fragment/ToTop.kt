package com.dragon.patternframework.function.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.dragon.patternframework.GetIP
import com.dragon.patternframework.R
import com.dragon.patternframework.function.adapter.ToTopAdapter
import com.dragon.patternframework.function.useinterface.RemoveSup
import com.dragon.patternframework.internet.commodity.OnSellingPresenter
import com.dragon.patternframework.javaBean.Commodity
import com.dragon.patternframework.person.useinterface.ImageUploadInterface
import kotlinx.android.synthetic.main.function_remove.*

/**
 * Created by 40774 on 2018/3/27.
 */
class ToTop: Fragment(), RemoveSup, ImageUploadInterface {
    private lateinit var present: OnSellingPresenter
    private val commodities= mutableListOf<Commodity>()

    override fun success() {
        Toast.makeText(context,"撤销成功！", Toast.LENGTH_SHORT).show()
    }

    override fun fail(e: String) {
        Toast.makeText(context, "失败！$e", Toast.LENGTH_SHORT).show()
    }

    override fun success(commodities:List<Commodity>) {
        this.commodities.clear()
        this.commodities.addAll(commodities)
        remove_recyclerview.adapter.notifyDataSetChanged()
        remove_recyclerview?.visibility = View.VISIBLE
        remove_circleView.visibility = View.GONE
    }
    /*商品下架*/
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.function_remove, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        remove_recyclerview.adapter = ToTopAdapter(true,context,commodities,this)
        remove_recyclerview.layoutManager = LinearLayoutManager(context)
        present= OnSellingPresenter.getInstance()
        present.setRemoveSupInterface(this)
        present.connectInternet(GetIP.getIp(context, "onSellingCommodity"))
    }
}