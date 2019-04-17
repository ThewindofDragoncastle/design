package com.dragon.patternframework.commoditydetail.adapter


import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.dragon.patternframework.R
import com.dragon.patternframework.commoditydetail.data.CommentItemUser
import com.dragon.patternframework.commoditydetail.useinterface.InputVisible
import de.hdodenhof.circleimageview.CircleImageView

/**
 * Created by WYL on 2018/1/31.
 */
class CDCitemRCAdapater() : RecyclerView.Adapter<CDCitemRCAdapater.ViewHolder>() {
    /*评论细节的适配器*/
    private lateinit var context: Context
    private lateinit var input: InputVisible
    private lateinit var ls: List<CommentItemUser>

    constructor(context: Context, input: InputVisible, ls: List<CommentItemUser>) : this() {
        this.context = context
        this.input = input
        this.ls = ls
    }

    override fun getItemCount(): Int {
        return ls.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder.run {
            val ciu = ls[position]
            this!!.content.text = ciu.content
            nickname.text = ciu.nickname
            time.text = ciu.timeYYD + " " + ciu.timeHHMM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.cdc_comment_detail_item, parent, false))
    }

    inner class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val headSculpture = item.findViewById<CircleImageView>(R.id.head_sculpture)
        val nickname = item.findViewById<TextView>(R.id.nickname)
        val content = item.findViewById<TextView>(R.id.content)
        val time = item.findViewById<TextView>(R.id.ac_id)

        init {
            item.setOnClickListener { input.inputInvisible() }
        }
    }
}
