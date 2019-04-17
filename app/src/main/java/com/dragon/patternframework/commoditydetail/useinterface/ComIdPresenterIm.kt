package com.dragon.patternframework.commoditydetail.useinterface

import com.dragon.patternframework.javaBean.Commodity
import com.dragon.patternframework.javaBean.CommodityIntention

interface ComIdPresenterIm {
    fun success(commodityIntentions: List<CommodityIntention>)
    fun fail(s:String)
}
interface ComIdPresenterIm2 {
    fun success()
    fun fail(s:String)
}
interface ComIdPresenterIm3 {
    fun Im3Sucess(com:Commodity)
    fun fail(s:String)
}
interface SearchCom {
    fun Im3Sucess(com:List<Commodity>)
    fun fail(s:String)
}