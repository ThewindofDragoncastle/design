package com.dragon.patternframework.function.fragment.info

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dragon.patternframework.R
import com.dragon.patternframework.function.activity.ReviseInfoActivity
import com.dragon.patternframework.function.adapter.AmendRecyclerAdapter
import com.dragon.patternframework.function.data.User
import kotlinx.android.synthetic.main.function_info_amend.*

/**
 * Created by WYL on 2018/2/9.
 */
class InfoAmend : Fragment() {
    /*信息完善页面*/
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.function_info_amend, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val user = User("", "龙城飞絮",
                "", "", "disorderwind@gmail.com"
                , "132 5820 0962 ", "", "成都大学", "", "")
        /*数据为空才需要完善 故此判断每一项的数据是否为空即可*/
        val noData = mutableListOf<String>()
        val dataInfo = mutableListOf<String>()
        val dataLabel = mutableListOf<String>()
        val add = fun(title: String, info: String) {
            dataInfo.add(info)
            dataLabel.add(title)
        }
        /*数据数组尺寸*/
        user.run {
            if (portrait.isEmpty()) noData.add("头像") else {
                add("头像", portrait)
            }
            if (falseName.isEmpty()) noData.add("呢称") else {
                add("呢称", falseName)
            }
            if (email.isEmpty()) noData.add("邮箱") else {
                add("邮箱", email)
            }
            if (sign.isEmpty()) noData.add("签名") else {
                add("签名", sign)
            }
            if (qq.isEmpty()) noData.add("QQ") else {
                add("QQ", qq)
            }
            if (phone.isEmpty()) noData.add("电话") else {
                add("电话", phone)
            }
            if (address.isEmpty()) noData.add("地址") else {
                add("地址", address)
            }
            if (whichClass.isEmpty()) noData.add("班级") else {
                add("班级", whichClass)
            }
            if (school.isEmpty()) noData.add("学校") else {
                add("学校", school)
            }
            if (trueName.isEmpty()) noData.add("真实姓名") else {
                add("真实姓名", trueName)
            }
        }
        val array = arrayOfNulls<String>(noData.size)
        noData.mapIndexed { index, s ->
            array[index] = s
        }
        val arrayInfo = arrayOfNulls<String>(dataInfo.size)
        dataInfo.mapIndexed { index, s ->
            arrayInfo[index] = s
        }
        val arrayLabel = arrayOfNulls<String>(dataLabel.size)
        dataLabel.mapIndexed { index, s ->
            arrayLabel[index] = s
        }
        viewMessage.setOnClickListener {
            val intent = Intent(context, ReviseInfoActivity::class.java)
            /*将有数据的数组 数组传递过去*/
            intent.putExtra("array_info", arrayInfo)
            intent.putExtra("array_label", arrayLabel)
            context.startActivity(intent)
        }
        fun_amend_recycler.adapter = AmendRecyclerAdapter(context, user, array)
        fun_amend_recycler.layoutManager = LinearLayoutManager(context)
    }
}