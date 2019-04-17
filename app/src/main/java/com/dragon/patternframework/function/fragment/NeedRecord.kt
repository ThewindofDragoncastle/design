package com.dragon.patternframework.function.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dragon.patternframework.R
import com.dragon.patternframework.person.adapter.OrderItemAdapter
import kotlinx.android.synthetic.main.function_need_record.*

/**
 * Created by 40774 on 2018/3/27.
 */
class NeedRecord:Fragment()
{
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.function_need_record, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        fun_record_recycler.adapter=OrderItemAdapter(context,2, mutableListOf())
        fun_record_recycler.layoutManager=LinearLayoutManager(context)
    }

}