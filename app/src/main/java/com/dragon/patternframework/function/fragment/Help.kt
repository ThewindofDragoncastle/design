package com.dragon.patternframework.function.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dragon.patternframework.R

/**
 * Created by WYL on 2018/2/9.
 */
class Help : Fragment() {
    /*商品下架*/
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.function_help, container, false)
    }
}