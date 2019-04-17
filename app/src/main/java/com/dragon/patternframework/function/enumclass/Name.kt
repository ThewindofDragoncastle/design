package com.dragon.patternframework.function.enumclass

/**
 * Created by WYL on 2018/2/24.
 */
enum class Name(val id: String) {
    NAME("商品名称"),
    AMOUNT("商品数量"),
    PRICE("商品价格"),
    NEGOTIABLE("能否议价"),
    DELIVERY("取件方式"),
    PIC("选择图片"),
    DESCRIBE("填写商品描述"),
    NOTICE("商家公示信息"),
    ITEM("商家交易条款"),
    INFO("填写联系方式"),
    GATHER("核实商品信息")
}