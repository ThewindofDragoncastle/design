package com.dragon.patternframework.function.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dragon.patternframework.R

class SellerMessageRecyclerAdapter(val context: Context) : RecyclerView.Adapter<SellerMessageRecyclerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.function_message_seller_item, parent, false))
    }

    override fun getItemCount(): Int {
        return 15
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.run {

        }
    }

    /*核实商品信息页面适配器*/
    inner class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {

    }
}