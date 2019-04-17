package com.dragon.patternframework.startuse

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dragon.patternframework.R
import com.dragon.patternframework.login.activity.MainPage
import kotlinx.android.synthetic.main.start_use_fragment.*

class Start3Fragment:Fragment() {
    val options = RequestOptions().placeholder(R.drawable.loading)
            .error(R.drawable.noload)
    /*加载第一张图片*/
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view=inflater?.inflate(R.layout.start_use_fragment,container,false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setPosition()
    }
   private fun setPosition()
   {
         button4.visibility=View.VISIBLE
       button4.setOnClickListener {
           context.startActivity(Intent(context,MainPage::class.java))
       }
       textView5.text= "让我们来立刻开始吧。"
       val int= R.drawable.start3
       Glide.with(context).load(int).apply(options).into(imageView6)
   }
}