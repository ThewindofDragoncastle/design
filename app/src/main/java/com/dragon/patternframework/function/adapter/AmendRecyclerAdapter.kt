package com.dragon.patternframework.function.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.dragon.patternframework.R
import com.dragon.patternframework.function.data.User
import com.dragon.patternframework.function.enumclass.InfoIdentify
import de.hdodenhof.circleimageview.CircleImageView

class AmendRecyclerAdapter(val context: Context, val user: User, array: Array<String?>) : RecyclerView.Adapter<AmendRecyclerAdapter.ViewHolder>() {
    private val titles: Array<String?> = array

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.function_info_amend_item, parent, false))
    }

    override fun getItemCount(): Int {
        return titles.size
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {

        holder?.run {
            val titleInfo = titles[position]
            top.visibility = View.GONE
            val contentInfo = when (titleInfo) {
                InfoIdentify.PORTRAIT.label -> {
                    portrait.visibility = View.VISIBLE
                    content.visibility = View.GONE
                    top.visibility = View.VISIBLE
                    user.portrait/*头像加载先不写*/
                }
                InfoIdentify.FALSE_NAME.label -> {
                    if (user.falseName.isEmpty()) "点击添加呢称" else user.falseName
                }
                InfoIdentify.SIGN.label -> {
                    if (user.sign.isEmpty()) "点击添加签名" else user.sign
                }
                InfoIdentify.QQ.label -> {
                    if (user.qq.isEmpty()) "点击添加QQ" else user.qq
                }
                InfoIdentify.EMAIL.label -> {
                    if (user.email.isEmpty()) "点击添加邮箱" else user.email
                }
                InfoIdentify.PHONE.label -> {
                    if (user.phone.isEmpty()) "点击添加电话" else user.phone
                }
                InfoIdentify.ADDRESS.label -> {
                    if (user.address.isEmpty()) "点击添加地址" else user.address
                }
            /*这里需要拆开 在查看全部信息时不需要*/
                "学校" -> {
                    if (user.school.isEmpty()) "点击添加学院" else user.school
                }
                "班级" -> {
                    if (user.whichClass.isEmpty()) "点击添加班级" else user.whichClass
                }
                InfoIdentify.TRUE_NAME.label -> {
                    if (user.trueName.isEmpty()) "点击添加真实姓名" else user.trueName
                }
                else ->/*说白了 就是空或者无*/ {
                    ""
                }
            }
            if (!titleInfo?.isEmpty()!!) {
                title.text = titleInfo
                content.text = contentInfo
            }
        }

    }

    /*核实商品信息页面适配器*/
    inner class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val portrait = item.findViewById<CircleImageView>(R.id.amend_portrait)
        val content = item.findViewById<TextView>(R.id.amend_content)
        val title = item.findViewById<TextView>(R.id.amend_title)
        val top = item.findViewById<View>(R.id.amend_top_view)
    }
}