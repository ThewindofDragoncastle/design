package com.dragon.patternframework.person.adapter


import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.dragon.patternframework.MyLog
import com.dragon.patternframework.R
import com.dragon.patternframework.function.activity.FunctionMainActivity
import com.dragon.patternframework.function.enumclass.FragmentIdentify

/**
 * Created by WYL on 2018/1/31.
 */
class PersonMainAdapter() : RecyclerView.Adapter<PersonMainAdapter.ViewHolder>() {
    /*评论细节的适配器*/
    private lateinit var context: Context
    private lateinit var array: Array<String>

    constructor(context: Context) : this() {
        this.context = context
        array = context.resources.getStringArray(R.array.personTab)
    }

    override fun getItemCount(): Int {
        return array.size+2
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        if (position<=array.size-1)
        {
        val name = array[position]
        holder.run {
            this!!.tab.text = name
            val res = when (name) {
                FragmentIdentify.MERCHANT_MESSAGE.label -> R.drawable.store_message
                FragmentIdentify.SYSTEM_MESSAGE.label -> R.drawable.system_message
                FragmentIdentify.INFORMATION_PERFECT.label -> R.drawable.person_info
                FragmentIdentify.COMPLETED_ORDER.label -> R.drawable.complete_order
                FragmentIdentify.INCOMPLETE_ORDER.label -> R.drawable.uncomplete_icon
                FragmentIdentify.MY_COMMENT.label -> R.drawable.mycomment
                FragmentIdentify.SYSTEMS_MANAGEMENT.label -> R.drawable.system_manage
                FragmentIdentify.HELP.label -> R.drawable.help
                FragmentIdentify.FEEDBACK.label -> R.drawable.feedback
                FragmentIdentify.ABOUT_THIS_PRODUCT.label -> R.drawable.about
                else -> 0
            }
            if (res != 0) {
                MyLog.i(javaClass.name, "加载资源名称：$name")
                try {
                    im.setImageResource(res)
                } catch (e: java.lang.OutOfMemoryError) {
                    MyLog.e(javaClass.name, "加载资源超出大小。：$name")
                }
            }
                itemView.setOnClickListener {
                    val intent = Intent(context, FunctionMainActivity::class.java)
                    /*给活动的碎片识别码*/
                    intent.putExtra("type", name)
                    context.startActivity(intent)
                }
        }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.person_mainpage_adapter, parent, false))
    }

    inner class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val tab = item.findViewById<TextView>(R.id.person_title)
        val im = item.findViewById<ImageView>(R.id.person_icon)
    }
}
