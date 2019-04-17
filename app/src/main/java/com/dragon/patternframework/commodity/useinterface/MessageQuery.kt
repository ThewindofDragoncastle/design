package com.dragon.patternframework.commodity.useinterface

import com.dragon.patternframework.javaBean.Message

interface MessageQuery {
    fun success(messages:List<Message>)
    fun fail(e:String)
}
interface MessageResult {
    fun success()
    fun fail(e:String)
}
interface MessageAmount{
    fun success(amount:Int)
    fun fail(e:String)
}