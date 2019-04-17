package com.dragon.patternframework.function.enumclass

import android.support.v4.app.Fragment
import com.dragon.patternframework.function.fragment.*
import com.dragon.patternframework.function.fragment.info.InfoAmend
import com.dragon.patternframework.function.fragment.info.InfoRevise
import com.dragon.patternframework.function.fragment.message.Message
import com.dragon.patternframework.function.fragment.message.MessageSeller
import com.dragon.patternframework.function.fragment.message.MessageSystem
import com.dragon.patternframework.function.fragment.newFragment.New
import com.dragon.patternframework.function.fragment.order.FinishOrder
import com.dragon.patternframework.function.fragment.order.ToSettleOrder

/**
 * Created by WYL on 2018/2/9.
 */
enum class FragmentIdentify(val label: String, val fragment: Fragment? = null) {
    NEW("上新", New()), OFFLINE("下架", Remove()), INFORMATION_MODIFICATION(" 信息修改", InfoRevise()),
    COMMODITY_SEARCH("商品搜索", Search()),
    /*增加一个从点击首页消息查询进入的界面*/ALL_MESSAGE("所有消息", Message()),
    MERCHANT_MESSAGE("商家消息", MessageSeller()), SYSTEM_MESSAGE("系统消息", MessageSystem()), INFORMATION_PERFECT("用户信息完善", InfoAmend()),
    COMPLETED_ORDER("已完成订单", FinishOrder()), INCOMPLETE_ORDER("未完成订单", ToSettleOrder()), MY_COMMENT("我的评论",MyComment()),
    BECOME_A_MERCHANT("成为商家"), PRODUCT_TOPPING("商品置顶",ToTop()),
    SYSTEMS_MANAGEMENT("系统管理"), HELP("帮助", Help()), FEEDBACK("意见反馈",Feedback()),
    ABOUT_THIS_PRODUCT("关于本产品",About());
}
