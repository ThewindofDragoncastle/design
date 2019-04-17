package com.dragon.patternframework.function.fragment.message

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
import com.dragon.patternframework.commodity.useinterface.MessageQuery
import com.dragon.patternframework.function.adapter.MessageRecyclerAdapter
import com.dragon.patternframework.internet.message.Query
import com.dragon.patternframework.javaBean.Message
import kotlinx.android.synthetic.main.function_message_seller.*


/**
 * Created by WYL on 2018/2/9.
 */
class MessageSeller : Fragment() ,MessageQuery{
   private val messages= mutableListOf<Message>()
    override fun success(messages: List<Message>) {
        this.messages.clear()
        this.messages.addAll(messages)
        message_recyclerView.adapter.notifyDataSetChanged()
    }

    override fun fail(e: String) {
        Toast.makeText(context,"失败！"+e.toString(),Toast.LENGTH_SHORT).show()
    }

    /*消息页面*/
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.function_message_seller, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val query=Query.getInstance()
        query.setMessageQuery(this)
        query.connectInternet(GetIP.getIp(context,"messageQuery"),"${UserRecord.getInstance().id}")
        message_recyclerView.layoutManager = LinearLayoutManager(context)
        message_recyclerView.adapter = MessageRecyclerAdapter(context,messages)
    }
}