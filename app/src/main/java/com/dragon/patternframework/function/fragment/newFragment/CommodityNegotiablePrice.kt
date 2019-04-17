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
import kotlinx.android.synthetic.main.function_new_negotiable.*

/**
 * Created by WYL on 2018/2/9.
 */
class CommodityNegotiablePrice : Fragment(), DataManager {
    private lateinit var pager: ViewPager
    private var pageNumber = 0
    private lateinit var dataCallback: DataCallback
    override fun setPager(pager: ViewPager, pageNumber: Int, dataCallback: DataCallback) {
        this.pager = pager
        this.pageNumber = pageNumber
        this.dataCallback = dataCallback
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.function_new_negotiable, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        /*默认选择不可议价*/
        no.hint = "不可议价"
        no.isChecked = true
        can.setOnClickListener {
            /*暂停0.5s的原因 不要让用户感觉很突兀*/
            dataCallback.commodityNegotiablePrice(true)
            Thread(Runnable {
                Thread.sleep(500)
                activity.runOnUiThread(Runnable {
                    pager.currentItem = pageNumber + 1
                })
            }).start()

        }
        no.setOnClickListener {
            /*暂停0.5s的原因 不要让用户感觉很突兀*/
            dataCallback.commodityNegotiablePrice(false)
            Thread(Runnable {
                Thread.sleep(500)
                activity.runOnUiThread(Runnable {
                    pager.currentItem = pageNumber + 1
                })
            }).start()
        }
    }
}