package com.dragon.patternframework.commoditydetail.enumclass

/**
 * Created by WYL on 2018/1/24.
 */
enum class ReviseLabel(val info: String) {
    /*商品每一类的标签*/
    PRODUCT_NAME("商品名称"),
    TAKE_WAY("取件方式"),
    /*商品标签 例如急售什么的*/
    SERIALNUMBER("标签"),
    PRICE("价格"),
    /*是否可议价*/
    NEGOTIABLE_PRICE("能否议价"),
    AMOUNT("出售数量"),
    DESCRIBE("描述"),
    /*拥有者*/
    USER("卖家"),
    CONTACT("联系方式"),
    QQ("QQ"),
    EMAIL("邮件"),
    PHONE("电话")
}