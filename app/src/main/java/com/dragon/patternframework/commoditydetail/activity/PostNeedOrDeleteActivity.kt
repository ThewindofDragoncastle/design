package com.dragon.patternframework.commoditydetail.activity

import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.dragon.patternframework.GetIP
import com.dragon.patternframework.R
import com.dragon.patternframework.UserRecord
import com.dragon.patternframework.commoditydetail.useinterface.NeedPost
import com.dragon.patternframework.internet.ordershow.OrderPostPre
import com.dragon.patternframework.javaBean.Commodity
import com.dragon.patternframework.javaBean.Order
import kotlinx.android.synthetic.main.activity_post_need.*
import java.sql.Timestamp
import java.util.*


class PostNeedOrDeleteActivity : AppCompatActivity(),NeedPost {
    override fun success() {
        Toast.makeText(this,"订单已提交给商家，请等待商家接受~~",Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun fail(s: String) {
        Toast.makeText(this, "上传失败！$s",Toast.LENGTH_SHORT).show()
    }

    /*订单提交或者删除*/
    /*订单提交 商家确定后 联系方式公布 给予*/
    private lateinit var dialog: AlertDialog.Builder
    private lateinit var pro:Commodity
    lateinit var datePicker: DatePicker
    private lateinit var timePicker: TimePicker
    lateinit var title: TextView
    lateinit var input: EditText
    private lateinit var need: Order
    private var isGrey = true
    private lateinit var presenter: OrderPostPre
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_need)
        /*从订单这里进来的时候--这里做的不好暂时这样*/
        /*在查询的时候 标准过程是获取商品id联网查询商品 填入信息*/
       val id= intent.getIntExtra("id",0)
       if (id!=0)
           need.status=3
        when {
            intent.getIntExtra("type", 0) == 2 -> {
                /*如果是待商家同意状态 */
               val text= when(need.status)
                {
                    3.toLong()->   "下架"
                   else -> "撤销"
               }
                postQuest.text ="确认$text"
                postQuest.setOnClickListener {
                    Toast.makeText(this, "$text 成功！", Toast.LENGTH_SHORT).show()
                }
                setUIShow(need,false)
            }
            intent.getIntExtra("type", 0) == 1 -> {
                postQuest.visibility = View.GONE
                val order=intent.getSerializableExtra("order") as Order
                pro=intent.getSerializableExtra("commodity") as Commodity
                setUIShow(order,false)
            }
            else ->
            {
                pro=intent.getSerializableExtra("product") as Commodity
                presenter= OrderPostPre.getInstance()
                presenter.setSignControllInterface(this)
                need=Order()
                initUI()
            }
        }
    }

    private fun setUIShow(need: Order,isQuery:Boolean) {
        /*id不应该显示*/
        if (isQuery)
        ac_id.text =need.id.toString()
        else
            ac_id.text ="待上传"
        name.text = pro.comLabel
        owner.text = pro.user.nickname
        /*这里应该从网上获取*/
        status.text =when (need.status.toInt()){
            1->"被商家取消"
            0->"待审核"
            2->"完成"
            3->"交易中"
            else->"交易失败"
        }
        customer.text = UserRecord.getInstance().nickname
        price.text = "${pro.price}"
        introduce.text = need.note
        start_time.text = need.startTime.toString()
        end_time.text = if (need.endTime==null) "等待中" else need.endTime.toString()
        place.text = need.place
    }

    private fun initUI() {
        val view = LayoutInflater.from(this).inflate(R.layout.time_select, LinearLayout(this), false)
        dialog = AlertDialog.Builder(this)
        dialog.setView(view)
        timePicker = view.findViewById(R.id.tpPicker)
        timePicker.setIs24HourView(true)
        datePicker = view.findViewById(R.id.dpPicker)
        title = view.findViewById(R.id.timeSelect_title)
        input = view.findViewById(R.id.input)
        showDialog(view)
        postQuest.setOnClickListener {
            /*如果按钮是灰色的就重新展示 对话框 否则进入提交*/
            if (isGrey)
                showDialog(view)
            else {
                val map= HashMap<String,String>()
                map["fromId"]=pro.useId.toString()
                map["useId"]=UserRecord.getInstance().id.toString()
                map["comId"] = "${pro.id}"
                map["note"]= need.note
                map["place"]= need.place
                map["startTime"]= need.startTime.time.toString()
       //         map["startTime"]= need.startTime.time.toString()
                presenter.connectInternet(GetIP.getIp(this,"postOrderQuery"),map)
            }
        }
    }

    private fun showDialog(view: View) {
        if (view.parent != null) {
            val pa = view.parent as ViewGroup
            pa.removeView(view)
        }
        timePicker.visibility = View.GONE
        datePicker.visibility = View.VISIBLE
        input.visibility = View.GONE
        title.text = "选择日期"
        dialog.setPositiveButton("选择时分", DialogInterface.OnClickListener { dialog, which ->
            dialog.cancel()
            showTwo(view)
        })
        dialog.setNegativeButton("取消", null)
        dialog.show()
    }

    private fun showTwo(view: View) {
        if (view.parent != null) {
            val pa = view.parent as ViewGroup
            pa.removeView(view)
        }
        datePicker.visibility = View.GONE
        timePicker.visibility = View.VISIBLE
        input.visibility = View.GONE
        title.text = "选择时分"
        dialog.setNegativeButton("上一步", DialogInterface.OnClickListener { dialog, which ->
            dialog.dismiss()
            showDialog(view)
        })
        dialog.setPositiveButton("下一步", DialogInterface.OnClickListener { dialog, which ->
            dialog.dismiss()
            need.startTime= Timestamp(
                    Date(datePicker.year,datePicker.month,datePicker.dayOfMonth
                            ,timePicker.currentHour,timePicker.currentMinute
                            ,0).time
            )
            showThree(view)
        })
        dialog.show()
    }

    private fun showThree(view: View) {

        if (view.parent != null) {
            val pa = view.parent as ViewGroup
            pa.removeView(view)
        }
        datePicker.visibility = View.GONE
        timePicker.visibility = View.GONE
        input.visibility = View.VISIBLE
        title.text = "填写约定地点"
        input.hint = "详细相见地址（必填，不少于两个字）"
        dialog.setNegativeButton("上一步", DialogInterface.OnClickListener { dialog, which ->
            dialog.dismiss()
            showTwo(view)
        })
        dialog.setPositiveButton("下一步", DialogInterface.OnClickListener { dialog, which ->
            dialog.dismiss()
            if (input.text.toString().length >= 2) {
                need.place = input.text.toString()
                input.text.clear()
                showFour(view)
            } else {
                showThree(view)
                Toast.makeText(this, "输入格式不合法", Toast.LENGTH_SHORT).show()
            }
        })
        dialog.show()
    }

    private fun showFour(view: View) {
        if (view.parent != null) {
            val pa = view.parent as ViewGroup
            pa.removeView(view)
        }
        datePicker.visibility = View.GONE
        timePicker.visibility = View.GONE
        input.visibility = View.VISIBLE
        title.text = "交易说明"
        input.hint = "（选填）"
        dialog.run {
            setNegativeButton("上一步", { dialog, which ->
                dialog.dismiss()
                showThree(view)
            })
            setPositiveButton("完成", { dialog, which ->
                dialog.dismiss()
                need.note = input.text.toString()
                input.text.clear()
                isGrey = false
                setUIShow(need,false)
                postQuest.setBackgroundColor(resources.getColor(R.color.good_red))
            })
            show()
        }
    }
}
