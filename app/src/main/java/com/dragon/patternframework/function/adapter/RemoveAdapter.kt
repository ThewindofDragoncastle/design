package com.dragon.patternframework.function.adapter


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dragon.patternframework.GetIP
import com.dragon.patternframework.R
import com.dragon.patternframework.commoditydetail.activity.CommodityDetailDisplay
import com.dragon.patternframework.function.activity.CommodityReviseActivity
import com.dragon.patternframework.internet.commodity.RemovePresenter
import com.dragon.patternframework.javaBean.Commodity
import com.dragon.patternframework.person.useinterface.ImageUploadInterface


class RemoveAdapter(val isRemove:Boolean,var context: Context, private val commodities: List<Commodity>,val im:ImageUploadInterface) : RecyclerView.Adapter<RemoveAdapter.ViewHolder>() {
    val options = RequestOptions().placeholder(R.drawable.loading)
            .error(R.drawable.noload)
    override fun getItemCount(): Int {
        return commodities.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.run {
            val commodity=commodities[position]
            deal.text=commodity.deal
            time.text= commodity.time.toString()
            label.text=commodity.comLabel
            deal.text=commodity.deal
            describe.text=commodity.note
            /*加载第一张图片*/
            Glide.with(context).load(commodity.images.split((";").toRegex()).dropLastWhile(
                    { it.isEmpty() }).toTypedArray()[0]).apply(options).into(pic)
            more.setOnClickListener {
                if (isRemove)
                {
                    val intent= Intent(context, CommodityDetailDisplay::class.java)
                    intent.putExtra("commodity",commodity)
                    intent.putExtra("id",0)
                    context.startActivity(intent)
                }
            }
            sure.setOnClickListener {
                if (isRemove) {
                    /*确定下架*/
                    val pre = RemovePresenter.getInstance()
                    pre.setRemoveSupInterface(im)
                    pre.connectInternet(GetIP.getIp(context, "removeCommodity"), commodity.id.toInt())
                }
                else
                {
                    val intent= Intent(context,CommodityReviseActivity::class.java)
                    intent.putExtra("commodity",commodity)
                    intent.putExtra("id",0)
                    context.startActivity(intent)
                }
            }
            status.text =when (commodity.comStatus.toInt()){
                0->"交易中"
                1->"取消"
                2->"未完成"
                else->"不明"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.function_remove_item, parent, false))
    }

    inner class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val label= item.findViewById<TextView>(R.id.remove_comlabel)!!
        val deal=item.findViewById<TextView>(R.id.remove_deal)
        val describe= item.findViewById<TextView>(R.id.remove_describe)!!
        val status= item.findViewById<TextView>(R.id.remove_status)!!
        val time= item.findViewById<TextView>(R.id.remove_time)!!
        val pic= item.findViewById<ImageView>(R.id.remove_pic)!!
        val more= item.findViewById<Button>(R.id.remove_more)!!
        val sure= item.findViewById<Button>(R.id.remove_sure)!!
        init {
            if (!isRemove)
            {
                sure.text="修改"
            }
        }
    }
}
