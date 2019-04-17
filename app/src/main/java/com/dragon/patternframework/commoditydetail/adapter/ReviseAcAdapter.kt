package com.dragon.patternframework.commoditydetail.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.RadioButton
import android.widget.TextView
import com.dragon.patternframework.R
import com.dragon.patternframework.commoditydetail.enumclass.ReviseLabel
import com.dragon.patternframework.javaBean.Commodity

class ReviseAcAdapter(val context: Context,val pro:  Commodity,id:Int) : RecyclerView.Adapter<ReviseAcAdapter.ViewHolder>() {
    /*商品信息适配器*/
    val ss=context.resources.getStringArray(R.array.mutCheck)
    override fun getItemCount(): Int {
        return  ReviseLabel.values().size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val vPro =  ReviseLabel.values()[position]
        val label = vPro.info
        holder?.run {
            topCon.visibility=View.GONE
            bottomCon.visibility=View.VISIBLE
            top.visibility=View.GONE
            frameLayout.removeAllViews()

            val describe = when (vPro) {
                ReviseLabel.PRODUCT_NAME -> {
                    top.visibility=View.VISIBLE
                    pro.comLabel
                }
                ReviseLabel.TAKE_WAY -> {
                    val view=LayoutInflater.from(context).inflate(R.layout.revise_checkbox,frameLayout,false);
                    frameLayout.addView(view)
                    val yes=view.findViewById<RadioButton>(R.id.check_yes)
                    val no=view.findViewById<RadioButton>(R.id.check_no)
                    if (pro.isDispatch.toInt()!=0)
                       yes.isChecked=true
                    else
                        no.isChecked=true
                    ""
                }
                ReviseLabel.SERIALNUMBER -> {
                    val sb=StringBuffer()
                    pro.tab.split(";").map {
                        if (!it.isEmpty())
                            sb.append(ss[Integer.parseInt(it)]+";")
                    }
                    sb.toString()
                }
                ReviseLabel.PRICE -> {""+pro.price}
                ReviseLabel.NEGOTIABLE_PRICE -> {
                    val view=LayoutInflater.from(context).inflate(R.layout.revise_checkbox,frameLayout,false);
                    frameLayout.addView(view)
                    val yes=view.findViewById<RadioButton>(R.id.check_yes)
                    val no=view.findViewById<RadioButton>(R.id.check_no)
                    if (pro.isBargain.toInt()!=0)
                        yes.isChecked=true
                    else
                        no.isChecked=true
                    ""}
                ReviseLabel.AMOUNT -> {""+pro.amount}
                ReviseLabel.DESCRIBE -> {""+pro.note}
                ReviseLabel.USER -> {""+pro.user.nickname}
                ReviseLabel.CONTACT ->  {
                    bigTop.visibility=View.GONE
                    topCon.visibility=View.VISIBLE
                    bottomCon.visibility=View.GONE
                    ""
                }
                ReviseLabel.QQ -> {
                    top.visibility=View.VISIBLE
                    pro.user.qq
                }
                ReviseLabel.EMAIL -> {""+pro.user.email}
                ReviseLabel.PHONE -> {""+pro.user.phone}
            }
            if (describe!=null)
            {
                note.text=describe
            }
            this.label.text=vPro.info
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(
                LayoutInflater.from(context)
                        .inflate(R.layout.activity_revise_info_item, parent, false))
    }

    inner class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val frameLayout = item.findViewById<FrameLayout>(R.id.revise_framelayout)!!
        val label = item.findViewById<TextView>(R.id.revise_label)!!
        val biglabel = item.findViewById<TextView>(R.id.revise_textView3)!!
        val note = item.findViewById<TextView>(R.id.textView6)!!
        val top = item.findViewById<View>(R.id.view2)!!

        val bigTop = item.findViewById<View>(R.id.view11)!!
        val  bottomCon = item.findViewById<View>(R.id.info_item_con)!!
        val  topCon = item.findViewById<View>(R.id.constraintLayout6)!!
        init {
        }
    }
}