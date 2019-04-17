package com.dragon.patternframework.commoditydetail.data

import java.net.URL

/**
 * Created by WYL on 2018/1/31.
 */
data class CommentItemUser(
        var headSculpture: URL = URL("http://39.108.123.220/ADimageviewurl/牦牛肉.jpg"),
        var nickname: String = "",
        var content: String = "",
        var timeYYD: String = "2017-12-12",
        var timeHHMM: String = "23:59"
)
