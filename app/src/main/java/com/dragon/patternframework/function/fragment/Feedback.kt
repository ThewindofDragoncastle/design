package com.dragon.patternframework.function.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.dragon.patternframework.R
import kotlinx.android.synthetic.main.function_info_feedback.*

/**
 * Created by 40774 on 2018/3/27.
 */
class Feedback:Fragment()
{
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.function_info_feedback, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        fun_feedback_send.setOnClickListener {
            Toast.makeText(context,"管理员已经收到你的消息！",Toast.LENGTH_SHORT).show()
        }
    }

}