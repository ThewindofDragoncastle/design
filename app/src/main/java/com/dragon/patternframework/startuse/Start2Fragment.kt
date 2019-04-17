package com.dragon.patternframework.startuse

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dragon.patternframework.R
import kotlinx.android.synthetic.main.start_use_fragment.*

class Start2Fragment:Fragment() {
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
   fun setPosition()
   {
       textView5.text= "在这里我们可以自由的\n用闲置物品换取我们喜爱的东西。"
       val int= R.drawable.start2
       Glide.with(context).load(int).apply(options).into(imageView6)
   }
}