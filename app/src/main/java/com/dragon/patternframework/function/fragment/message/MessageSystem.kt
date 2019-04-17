package com.dragon.patternframework.function.fragment.message

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dragon.patternframework.R
import com.dragon.patternframework.function.adapter.MessageRecyclerAdapter
import kotlinx.android.synthetic.main.function_message_system.*

/**
 * Created by WYL on 2018/2/9.
 */
class MessageSystem : Fragment() {
    /*消息页面*/
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.function_message_system, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        message_recyclerView.layoutManager = LinearLayoutManager(context)
        message_recyclerView.adapter = MessageRecyclerAdapter(context, mutableListOf())
    }
}