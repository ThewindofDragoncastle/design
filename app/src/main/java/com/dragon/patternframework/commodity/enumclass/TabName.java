package com.dragon.patternframework.commodity.enumclass;

/**
 * Created by 40774 on 2017/12/5.
 */

public enum TabName {
    //    可以通过网络进行设置
    URGENT_SALE("急售"), LATEST("最新上架"), FOOD("食品"), DIGITAL_PRODUCT("数码产品"), BEAUTY("魅力"), DAILY("生活用品"), AFFORDABLE("实惠"), VIRTUAL("校园宽带"), LARGE_ITEM("大件物品"), NOVEL("新奇好玩");
    private String value;

    TabName(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}