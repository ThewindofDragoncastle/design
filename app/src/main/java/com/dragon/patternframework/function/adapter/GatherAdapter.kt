package com.dragon.patternframework.function.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.dragon.patternframework.R
import com.dragon.patternframework.function.adapter.GatherAdapter.Icon.*
import com.dragon.patternframework.javaBean.Commodity

class GatherAdapter(val context: Context, private val sellerProduct: Commodity) : RecyclerView.Adapter<GatherAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.function_new_gather_item, parent, false))
    }

    override fun getItemCount(): Int {
        return values().size
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.run {
            val icon = values()[position]
            holder.icon.setImageResource(icon.res)
            holder.message.text = when (icon) {
                CONTACT -> {
/*哪样为空就不输入哪样*/
                    if ((sellerProduct.qq.isEmpty() || sellerProduct.qq == "error")
                            && (sellerProduct.email.isEmpty() || sellerProduct.email == "error"))
                        "电话：${sellerProduct.phone}"
                    else if (sellerProduct.qq.isEmpty() || sellerProduct.qq == "error")
                        "email:${sellerProduct.email}\n" +
                                "电话：${sellerProduct.phone}"
                    else if (sellerProduct.email.isEmpty() || sellerProduct.email == "error")
                        "qq:${sellerProduct.qq}\n" +
                                "电话：${sellerProduct.phone}"
                    else
                        "qq:${sellerProduct.qq}\nemail:${sellerProduct.email}\n电话：${sellerProduct.phone}"
                }
                DELIVERY -> if (sellerProduct.isDispatch.toInt()==0) "顾客自取"
                else "商家送货"
                CONTRACT -> sellerProduct.deal
                NOTICE -> sellerProduct.notice
                SAVE -> {
                    if (sellerProduct.isBargain.toInt()==1) "能议价"
                    else "不能议价"
                }
            }
        }
    }

    /*核实商品信息页面适配器*/
    inner class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val message = item.findViewById<TextView>(R.id.message)!!
        val icon = item.findViewById<ImageView>(R.id.icon)!!
    }

    enum class Icon(val res: Int) {
        DELIVERY(R.drawable.delivery),
        SAVE(R.drawable.save),
        CONTACT(R.drawable.contact),
        CONTRACT(R.drawable.contract),
        NOTICE(R.drawable.notice);
    }
}