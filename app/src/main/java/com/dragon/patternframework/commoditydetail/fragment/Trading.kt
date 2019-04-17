package com.dragon.patternframework.commoditydetail.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dragon.patternframework.MyLog
import com.dragon.patternframework.R
import com.dragon.patternframework.commoditydetail.activity.CommodityDetailDisplay
import com.dragon.patternframework.commoditydetail.activity.PostNeedOrDeleteActivity
import com.dragon.patternframework.commoditydetail.adapter.TradingReclyerViewAdapter
import com.dragon.patternframework.javaBean.Commodity
import kotlinx.android.synthetic.main.cd_commodity_trading.*


class Trading : Fragment() {
    private lateinit var commodity: Commodity
    /*交易*/
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.cd_commodity_trading, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        commodity=(activity as CommodityDetailDisplay).commodity
        trading_merchant.text=commodity.user.nickname
        if (commodity.deal!=null)
        trading_recyclerView.adapter = TradingReclyerViewAdapter(context,commodity.deal )
        else
            trading_recyclerView.adapter = TradingReclyerViewAdapter(context,"我希望诚信交易。" )
        trading_recyclerView.layoutManager = LinearLayoutManager(context)
        MyLog.d(javaClass.name,commodity.time)
        trading_time.text =commodity.time
        /*点击联系按钮 跳转页面提交需求*/
        trading_contact.setOnClickListener {
            val intent = Intent(context, PostNeedOrDeleteActivity::class.java)
            intent.putExtra("product", commodity)
            context.startActivity(intent)
        }
    }
}