package com.dragon.patternframework.function.useinterface

import com.dragon.patternframework.javaBean.Commodity

/**
 * Created by WYL on 2018/2/26.
 */
interface RemoveSup {
    fun fail(e:String)
    fun success(commodities: List<Commodity>)
}