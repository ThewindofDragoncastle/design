package com.dragon.patternframework.person.adapter


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dragon.patternframework.R
import com.dragon.patternframework.commoditydetail.activity.PostNeedOrDeleteActivity
import com.dragon.patternframework.javaBean.OrderAndCommodity
import java.text.SimpleDateFormat

/**
 * Created by WYL on 2018/1/31.
 */
class OrderItemAdapter() : RecyclerView.Adapter<OrderItemAdapter.ViewHolder>() {
    /*评论细节的适配器*/
    private lateinit var context: Context
    private var identifyID = 0
    private lateinit var  orderAndCommodities: List<OrderAndCommodity>
    val options = RequestOptions().placeholder(R.drawable.loading)
            .error(R.drawable.noload)
    constructor(context: Context, IdentifyID: Int,orderAndCommodity: List<OrderAndCommodity>) : this() {
        this.context = context
        this.identifyID = IdentifyID
        this.orderAndCommodities=orderAndCommodity
    }

    override fun getItemCount(): Int {
        return orderAndCommodities.size
    }

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.run {
            val orderAndCommodity=orderAndCommodities[position]
            val order=orderAndCommodity.order
            val commodity=orderAndCommodity.commodity
            /*加载第一张图片*/
            Glide.with(context).load(commodity.images.split((";").toRegex()).dropLastWhile(
                    { it.isEmpty() }).toTypedArray()[0]).apply(options).into(orderPicture)
            name.text=commodity.comLabel
            status.text =when (order.status.toInt()){
                1->"被商家取消"
                0->"待审核"
                2->"完成"
                3->"交易中"
                else->"交易失败"
            }
            introduce.text = order.note
            seller.text = commodity.user.nickname
            val time1 = SimpleDateFormat("yyyy-MM-dd HH:mm")
            time.text =time1.format(order.startTime.time)
            price.text = "${commodity.price}"
            itemView.setOnClickListener {
                val intent = Intent(context, PostNeedOrDeleteActivity::class.java)
                intent.putExtra("type", identifyID)
                intent.putExtra("commodity",commodity)
                intent.putExtra("order",order)
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.order_item, parent, false))
    }

    inner class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        /*设置按键以及文本显示*/
        val name=item.findViewById<TextView>(R.id.order_item_name)
        val status=item.findViewById<TextView>(R.id.order_item_status)
        val introduce=item.findViewById<TextView>(R.id.order_item_introduce)
        val seller=item.findViewById<TextView>(R.id.order_item_seller)
        val price=item.findViewById<TextView>(R.id.order_item_price)
        val time=item.findViewById<TextView>(R.id.order_item_time)
        val orderPicture=item.findViewById<ImageView>(R.id.order_picture)

    }
}
