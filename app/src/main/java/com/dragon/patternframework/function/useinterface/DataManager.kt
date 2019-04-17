package com.dragon.patternframework.function.useinterface

import android.support.v4.view.ViewPager

/**
 * Created by WYL on 2018/2/19.
 */
interface DataManager {
    /*对商品上新各个碎片进行数据统一管理*/
    fun setPager(pager: ViewPager, pageNumber: Int, dataCallback: DataCallback)
}