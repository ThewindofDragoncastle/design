package com.dragon.patternframework.commoditydetail.data

/**
 * Created by WYL on 2018/2/8.
 */
data class NeedContract(val id: String = "",
                        val commodityName: String = "",
        /*这条数据不显示*/
                        val commodityId: String = "",
                        /*应该有三个状态 0取消 1完成 2未完成 3待商家同意*/
                        var status:Int=0,
                        val commdityPrice: Float = 0f,
                        val shopOwner: String = "",
                        val buyer: String = "",
                        var startTime: String = "",
                        var endTime: String = "",
                        var promisePlace: String = "",
                        var note: String = "无"
)


