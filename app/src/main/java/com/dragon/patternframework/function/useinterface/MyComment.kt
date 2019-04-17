package com.dragon.patternframework.function.useinterface

import com.dragon.patternframework.javaBean.CommodityIntention

/**
 * Created by WYL on 2018/2/26.
 */
interface MyComment {
    fun fail(e:String)
    fun success(commodityIntention: List<CommodityIntention>)
}