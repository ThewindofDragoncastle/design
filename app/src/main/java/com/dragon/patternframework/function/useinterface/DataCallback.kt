package com.dragon.patternframework.function.useinterface

import com.dragon.patternframework.javaBean.Commodity


/**
 * Created by WYL on 2018/2/19.
 */
interface DataCallback {
    /*将数据返回给上新
    * 这里商品时间自己生成*/
    fun commodityName(name: String)
    fun commodityImage(content: String)
    fun commodityPrice(price: Float)
    fun commodityDescribe(describe: String)
    fun commodityDelivery(isSellerDelivery: Boolean)
    fun commodityContactQQ(qq: String)
    fun commodityContactEmail(email: String)
    fun commodityContactPhone(phone: String)
    fun sellerTrading(content: String)
    fun sellerNotice(content: String)
    fun commodityAmount(amount: Int)
    fun commodityNegotiablePrice(can: Boolean)
    fun getNewProduct(): Commodity
    /*修改上架信息*/
    fun modifyInfo()
}