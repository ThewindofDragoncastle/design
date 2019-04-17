package com.dragon.patternframework.function.fragment.message

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dragon.patternframework.R
import com.dragon.patternframework.function.adapter.MessageFragmentAdapter
import kotlinx.android.synthetic.main.function_message.*

/**
 * Created by WYL on 2018/2/9.
 */
class Message : Fragment() {
    /*消息页面*/
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.function_message, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val fragments = listOf(MessageSeller(), MessageSystem())
        val adapter = MessageFragmentAdapter(fragmentManager, fragments, message_tablayout)
        message_pager.adapter = adapter
        message_tablayout.setupWithViewPager(message_pager)
    }
}