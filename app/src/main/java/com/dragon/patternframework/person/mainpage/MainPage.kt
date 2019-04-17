package com.dragon.patternframework.person.mainpage

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dragon.patternframework.R
import com.dragon.patternframework.UserRecord
import com.dragon.patternframework.function.activity.FunctionMainActivity
import com.dragon.patternframework.function.enumclass.FragmentIdentify
import com.dragon.patternframework.person.adapter.PersonMainAdapter
import kotlinx.android.synthetic.main.person_gird_part1.*
import kotlinx.android.synthetic.main.person_gird_part2.*
import kotlinx.android.synthetic.main.person_head_part.*
import kotlinx.android.synthetic.main.person_mainpage.*
import java.util.*


class MainPage : Fragment() {
    private val options = RequestOptions()
            .placeholder(R.drawable.loading)
            .error(R.drawable.noload)!!
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.person_mainpage, container, false)
        return view
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        val im=ImageView(context)
//        im.setImageResource(R.drawable.testba)
//        im.scaleType=ImageView.ScaleType.CENTER_CROP
//        person_con.background=im.drawable
        val user=UserRecord.getInstance()
        Glide.with(context).load(user.image).apply(options).into(person_head_portrait)
        person_head_name.text=user.nickname
        person_head_sign.text=  if (user.sign==null)
       "此人很懒，未更新签名。"
        else user.sign
        r1.adapter = PersonMainAdapter(context)
        val sta = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        r1.layoutManager = sta
        initTabs()
        setCountDown(countDown)
    }

    private fun setCountDown(chronometer2: TextView) {
        /*标准时间 为第二天的早上九点*/
        /*如果修改应该修改此处 第二个月*/
        val ca = Calendar.getInstance()
        ca.set(2018, 3, 1, 0, 0, 0)
        val standardTime = ca.timeInMillis
        val millis = System.currentTimeMillis()
        val count = standardTime - millis
        val v = Log.v("开始：CountDownTimerTest", "${millis}   ${standardTime} ${count}")
        object : CountDownTimer(count, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                /*时间分钟显示*/
                chronometer2.text = "离准备完成剩余${millisUntilFinished / 1000 / 3600 / 24}天${millisUntilFinished / 1000 / 60 / 60 % 24}时" +
                        "${millisUntilFinished / 1000 / 60 % 60}分${millisUntilFinished / 1000 % 60}秒"
                Log.v("CountDownTimerTest", "onTick millisUntilFinished = " + millisUntilFinished)
            }

            override fun onFinish() {
                Log.v("CountDownTimerTest", "onFinish")
            }
        }.start()
        chronometer2.text = ""
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    private fun initTabs() {
        val sale = context.resources.getStringArray(R.array.personSaleTab)
        val buy = context.resources.getStringArray(R.array.personBuyTab)
        buy_text1.text = buy[0]
        buy_text2.text = buy[1]
        buy_text3.text = buy[2]
        sale_text1.text = sale[0]
        sale_text2.text = sale[1]
        sale_text3.text = sale[2]
        val intent = Intent(context, FunctionMainActivity::class.java)
        buy_text1.setOnClickListener {
            intent.putExtra("type", FragmentIdentify.COMMODITY_SEARCH.label)
            context.startActivity(intent)
        }
        buy_text2.setOnClickListener {
            intent.putExtra("type", FragmentIdentify.BECOME_A_MERCHANT.label)
            context.startActivity(intent)
        }
        buy_text3.setOnClickListener {
            intent.putExtra("type", FragmentIdentify.PRODUCT_TOPPING.label)
            context.startActivity(intent)
        }
        /*如果不是商人设置为不可点击状态*/
      val isClickable=  if (UserRecord.getInstance().isSel.toInt()==0)
        {
            sale_text1.background=context.resources.getDrawable(R.drawable.grey)
            false
        }
        else
        {
            sale_text1.background=context.resources.getDrawable(R.drawable.blue_press_state)
            true
        }
        sale_text1.setOnClickListener {
            if (isClickable)
            {
                intent.putExtra("type", FragmentIdentify.NEW.label)
                context.startActivity(intent)
            }
            else
                Toast.makeText(context,"请先申请成为商家",Toast.LENGTH_SHORT).show()
        }
        sale_text2.setOnClickListener {
            intent.putExtra("type", FragmentIdentify.OFFLINE.label)
            context.startActivity(intent)
        }
        sale_text3.setOnClickListener {
            intent.putExtra("type", FragmentIdentify.INFORMATION_MODIFICATION.label)
            context.startActivity(intent)
        }
    }
}
