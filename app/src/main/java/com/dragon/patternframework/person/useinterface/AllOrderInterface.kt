package com.dragon.patternframework.person.useinterface

import com.dragon.patternframework.javaBean.OrderAndCommodity

interface AllOrderInterface {
    fun fail(e:String)
    fun success(orderAndCommodity:List<OrderAndCommodity>)
}