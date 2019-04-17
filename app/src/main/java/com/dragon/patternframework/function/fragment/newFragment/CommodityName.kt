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
import kotlinx.android.synthetic.main.function_new_name.*

/**
 * Created by WYL on 2018/2/9.
 */
class CommodityName : Fragment(), DataManager {
    private lateinit var pager: ViewPager
    private var pageNumber = 0
    private lateinit var dataCallback: DataCallback
    override fun setPager(pager: ViewPager, pageNumber: Int, dataCallback: DataCallback) {
        this.pager = pager
        this.pageNumber = pageNumber
        this.dataCallback = dataCallback
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.function_new_name, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        commodity_name.setOnFocusChangeListener { view, focus ->
            if (!focus)
                dataCallback.commodityName(commodity_name.text.toString())
        }
    }
}