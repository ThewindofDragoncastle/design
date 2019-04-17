package com.dragon.patternframework.commoditydetail.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import com.dragon.patternframework.R
import com.dragon.patternframework.commoditydetail.enumclass.ProductLabel
import com.dragon.patternframework.javaBean.Commodity

/**
 * Created by WYL on 2018/1/23.
 */
class CommodityInfoReclyerViewAdapter() : RecyclerView.Adapter<CommodityInfoReclyerViewAdapter.ViewHolder>() {
    /*商品信息适配器*/
    lateinit var context: Context
    lateinit var pro: Commodity
     var id:Int=-1

    constructor(context: Context, pro:  Commodity,id:Int) : this() {
        this.context = context
        this.pro = pro
        this.id=id
    }

    override fun getItemCount(): Int {
        return ProductLabel.values().size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val vPro = ProductLabel.values()[position]
        val label = vPro.info
        holder?.run {
            top.visibility = View.GONE
            topCon.visibility=View.GONE
            bottomCon.visibility=View.VISIBLE
            val describe = when (vPro) {
                ProductLabel.CONTACT ->{
                    topCon.visibility=View.VISIBLE
                    bottomCon.visibility=View.GONE
                    ""
                }
                ProductLabel.DESCRIBE -> pro.note
                ProductLabel.ID -> "" + pro.corder
                ProductLabel.NEGOTIABLE_PRICE ->
                    if (pro.isBargain==0.toLong()) "卖家说可以"
                    else "卖家说坚决不行"
                ProductLabel.PRICE -> "" + pro.price
                ProductLabel.PRODUCT_NAME -> {
                    top.visibility = View.VISIBLE
                    "" + pro.comLabel
                }
                ProductLabel.SERIALNUMBER -> pro.tab
                ProductLabel.USER -> if(pro.user!=null) "" + pro.user.nickname else "未能正确加载"
                ProductLabel.TIME -> pro.time
                ProductLabel.TAKE_WAY -> if (pro.isDispatch==0.toLong()) "卖家配送"
                else "自取"
                ProductLabel.AMOUNT -> "${pro.amount}"
                ProductLabel.QQ ->{
                    top.visibility = View.VISIBLE
                    pro.qq
                }
                ProductLabel.EMAIL -> pro.email
                ProductLabel.PHONE -> pro.phone
            }
                labelText.text=vPro.info
                textName.text = label
            /*首行缩进一个字符*/
             textDescribe.setText("\u3000$describe")
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.cd_commodity_info_item, parent, false))
    }

    inner class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val textName = item.findViewById<TextView>(R.id.cd_info_textview)!!
        val labelText = item.findViewById<TextView>(R.id.textView3)!!
        val textDescribe = item.findViewById<EditText>(R.id.info_edit2)!!
        val top = item.findViewById<View>(R.id. view2)!!
        val  bottomCon = item.findViewById<View>(R.id.info_item_con)!!
        val  topCon = item.findViewById<View>(R.id.constraintLayout6)!!
        init {
            if (id==-1)
            textDescribe.isEnabled=false
        }
    }
}