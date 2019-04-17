package com.dragon.patternframework.commoditydetail.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.dragon.patternframework.R

/**
 * Created by WYL on 2018/1/23.
 */
class TradingReclyerViewAdapter() : RecyclerView.Adapter<TradingReclyerViewAdapter.ViewHolder>() {
    /*商品信息适配器*/
    lateinit var context: Context
    lateinit var array: String

    constructor(context: Context, array: String) : this() {
        this.context = context
        this.array = array
    }

    override fun getItemCount(): Int {
        return 1
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder.run {
            this!!.number.text = "${position + 1}、"
            describe.text = array
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.cd_commodity_trading_item, parent, false))
    }

    inner class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val number = item.findViewById<TextView>(R.id.trading_number)!!
        val describe = item.findViewById<TextView>(R.id.trading_descibe)!!
    }
}