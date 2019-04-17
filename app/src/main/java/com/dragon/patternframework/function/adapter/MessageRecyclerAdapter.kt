package com.dragon.patternframework.function.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dragon.patternframework.GetIP
import com.dragon.patternframework.R
import com.dragon.patternframework.UserRecord
import com.dragon.patternframework.commodity.useinterface.MessageResult
import com.dragon.patternframework.internet.message.Result
import com.dragon.patternframework.javaBean.Message

class MessageRecyclerAdapter(val context: Context, val messages: List<Message>) : MessageResult,RecyclerView.Adapter<MessageRecyclerAdapter.ViewHolder>() {
    private val presenter=Result.getInstance()
   init {
       presenter.setMessageQuery(this)
   }
    override fun success() {
        Toast.makeText(context,"成功！",Toast.LENGTH_SHORT).show()
    }

    override fun fail(e: String) {
        Toast.makeText(context,"失败！$e",Toast.LENGTH_SHORT).show()
    }


    val options = RequestOptions().placeholder(R.drawable.loading)
            .error(R.drawable.noload)
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(
                LayoutInflater.from(context)
                        .inflate(R.layout.function_message_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val message = messages[position]
        holder?.run {
            if (message.isHan.toInt() == 0) {
                refuse.visibility = View.GONE
                sure.visibility = View.GONE

            }
            else {
                refuse.visibility = View.VISIBLE
                sure.visibility = View.VISIBLE
                val map=HashMap<String,String>()
                map["from"]=message.fromId.toString()
                map["use_id"]=UserRecord.getInstance().id.toString()
                map["mes_id"]=message.id.toString()
                map["ord_id"]=message.ordId.toString()
                refuse.setOnClickListener {
                    map["isAgree"]="false"
                    presenter.connectInternet(GetIP.getIp(context,"messageresult"),map)
                }
                sure.setOnClickListener {
                    map["isAgree"]="true"
                    presenter.connectInternet(GetIP.getIp(context,"messageresult"),map)
                }
            }
            mes.text = message.message
            nickname.text = message.nickname
            Glide.with(context).load(message.image).apply(options).into(image)
        }
    }

    /*核实商品信息页面适配器*/
    inner class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val refuse = item.findViewById<TextView>(R.id.mes_refuse)
        val sure = item.findViewById<TextView>(R.id.mes_sure)
        val nickname = item.findViewById<TextView>(R.id.textView16)
        val image = item.findViewById<ImageView>(R.id.amend_portrait)
        val mes = item.findViewById<TextView>(R.id.textView12)
    }
}