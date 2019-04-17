package com.dragon.patternframework.commoditydetail.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dragon.patternframework.R
import com.dragon.patternframework.commoditydetail.adapter.CommodityInfoReclyerViewAdapter
import com.dragon.patternframework.javaBean.Commodity
import kotlinx.android.synthetic.main.cd_commodity_info.*

/**
 * Created by WYL on 2018/1/23.
 */
class ComDetail : Fragment() {
    /*商品细节介绍*/
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.cd_commodity_info, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        cd_info_recyclerView.layoutManager = LinearLayoutManager(context)
        val com= activity.intent.getSerializableExtra("commodity") as Commodity
        val id=activity.intent.getIntExtra("id",-1)
        cd_info_recyclerView.adapter = CommodityInfoReclyerViewAdapter(context, com,id)
    }

}