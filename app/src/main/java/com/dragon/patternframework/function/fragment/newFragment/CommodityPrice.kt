package com.dragon.patternframework.function.fragment.newFragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dragon.patternframework.R
import com.dragon.patternframework.function.useinterface.DataCallback
import com.dragon.patternframework.function.useinterface.DataManager
import kotlinx.android.synthetic.main.function_new_price.*

/**
 * Created by WYL on 2018/2/9.
 */
class CommodityPrice : Fragment(), DataManager {
    private lateinit var pager: ViewPager
    private var pageNumber = 0
    private lateinit var dataCallback: DataCallback
    override fun setPager(pager: ViewPager, pageNumber: Int, dataCallback: DataCallback) {
        this.pager = pager
        this.pageNumber = pageNumber
        this.dataCallback = dataCallback
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.function_new_price, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val left = new_price_left
        val right = new_amount
        left.maxValue = 10000
        left.minValue = 0
        right.minValue = 0
        right.maxValue = 99
        left.value = 100
        right.value = 0
        left.setOnValueChangedListener { numberPicker, old, new ->
            dataCallback.commodityPrice((left.value + right.value * 0.01).toFloat())
        }
        right.setOnValueChangedListener { numberPicker, old, new ->
            dataCallback.commodityPrice((left.value + right.value * 0.01).toFloat())
        }

    }

}