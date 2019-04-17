package com.dragon.patternframework.commoditydetail.adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dragon.patternframework.GetIP
import com.dragon.patternframework.MyLog
import com.dragon.patternframework.R
import com.dragon.patternframework.commoditydetail.data.CommentUser
import com.dragon.patternframework.internet.commodity.IDComPresenter
import com.dragon.patternframework.loadingview.SuspendingView
import de.hdodenhof.circleimageview.CircleImageView

/**
 * Created by WYL on 2018/1/23.
 */
class CommodityCommentReclyerViewAdapter(val context: Context
                                         , val last: List<CommentUser>
                                         , val fa: FloatingActionButton,val isAll:Boolean
) : RecyclerView.Adapter<CommodityCommentReclyerViewAdapter.ViewHolder>() {
    val options = RequestOptions().placeholder(R.drawable.loading)
            .error(R.drawable.noload)
    /*商品评论适配器*/
    /*留下上一次的viewholder以方便线性布局点击时调用*/
    private lateinit var viewholder: ViewHolder
    /*上一个位置 为了记录器读取*/
    private var lastPosition = 0

    override fun getItemCount(): Int {
        return last.size
    }


    init {
        /*从逻辑上说只要设计监听一次就好了*/

    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder?, @SuppressLint("RecyclerView") position: Int) {
        /*为悬浮键的出现和隐藏进行设置
        * 判断机制如果大于10可以则出现*/
        if (fa.visibility == View.VISIBLE && position <= 10)
            fa.visibility = View.INVISIBLE
        else if (fa.visibility == View.INVISIBLE && position > 10)
            fa.visibility = View.VISIBLE
        /*获取数据*/
        val record = last[position]
        holder?.fr?.removeAllViews()
        holder?.run {
            /*如果已经存在就不做处理*/
            /*这里有个问题 fr是每一个都有的 viewholder数量是一定的
            * 故此如果要进行移除操作 是所有的都要移除*/
            /*清空上一个存在的数据或者加载*/
            /*加载数据*/
            if (isAll)
        itemView.setOnClickListener {
             val idComPresenter= IDComPresenter.getInstance()
             idComPresenter.connectInternet(GetIP.getIp(context,"idcommodity"),record.comId)
        }
            if (isAll)
            {
                ac.visibility=View.VISIBLE
                ac.text=record.comname
            }
            else
            ac.visibility=View.GONE
            Glide.with(context).load(record.headSculpture).apply(options).into(headSculpture)
            commentAmountbu.text = "(${record.commentAmount})"
            likeAmountButton.text = "(${record.likeAmount})"
            timeHHDD.text = record.timeHHMM
            if (record.likeStatus) {
                likeAmountButton.setTextColor(Color.RED)
                like.setTextColor(Color.RED)
            } else {
                likeAmountButton.setTextColor(Color.GRAY)
                like.setTextColor(Color.GRAY)
            }
            content.text = record.content
            nickname.text = record.nickname
            MyLog.i("TAG","记录："+record.timeYYD)
            if (record.isSure)
                if(record.timeYYD!="0:0")
                fr.addView(SuspendingView(context, "求回复", record.timeYYD))
            else
                    fr.addView(SuspendingView(context, "求回复", "2018-5-2"))
            /*设置监听*/
            comment?.setOnClickListener {
//                viewholder = holder
//                lastPosition = position
//                /*跳转活动 传递数据*/
//                val intent = Intent(context, CommentDetailActivity::class.java)
//                intent.putExtra("comment_user", record)
//                context.startActivity(intent)
            }
            like?.setOnClickListener {
                if (like.currentTextColor != Color.RED) {
                    like.setTextColor(Color.RED)
                    likeAmountButton.setTextColor(Color.RED)
                    likeAmountButton.text = "(${++record.likeAmount})"
                    /*记录数据*/
                    record.likeStatus = true
                } else {
                    /*确认取消“想问”吗？*/
                    AlertDialog.Builder(context)
                            .setMessage("你确定撤回操作吗？")
                            .setNegativeButton("我不想问了", DialogInterface.OnClickListener { which, it ->
                                like.setTextColor(Color.GRAY)
                                likeAmountButton.setTextColor(Color.GRAY)
                                likeAmountButton.text = "(${--record.likeAmount})"
                                /*记录数据*/
                                record.likeStatus = true
                                Toast.makeText(context, "撤销成功！", Toast.LENGTH_SHORT).show()
                            })
                            .setPositiveButton("操作失误", null)
                            .show()

                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.cd_commodity_comment_item, parent, false))
    }

    open inner class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        /*点击我也想问人的数量*/
        /*评论数*/
        val headSculpture = item.findViewById<CircleImageView>(R.id.head_sculpture)
        val nickname = item.findViewById<TextView>(R.id.nickname)
        val content = item.findViewById<TextView>(R.id.content)
        val fr = item.findViewById<FrameLayout>(R.id.cd_suspendingView_fl)
        val comment = item.findViewById<ImageView>(R.id.cd_comment_comment)
        val likeAmountButton = item.findViewById<TextView>(R.id.cd_comment_want_amount)
        val commentAmountbu = item.findViewById<TextView>(R.id.cd_comment_comment_amount)
        val like = item.findViewById<TextView>(R.id.cd_comment_wanaask)
        val timeHHDD = item.findViewById<TextView>(R.id.ac_id)
        val ac = item.findViewById<TextView>(R.id.aci_label)
        init {
            /*只要点击就进入详情页面*/
            if (!isAll)
            item.setOnClickListener {
//                val intent = Intent(context, CommentDetailActivity::class.java)
//                intent.putExtra("comment_user", last[adapterPosition])
//                context.startActivity(intent)
            }
        }
    }
}
