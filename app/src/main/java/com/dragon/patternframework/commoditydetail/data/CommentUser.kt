package com.dragon.patternframework.commoditydetail.data

import java.io.Serializable
import java.net.URL

/**
 * Created by WYL on 2018/1/31.
 */
/*评论的子项布局的类 */
data class CommentUser(
        var headSculpture: URL = URL("http://39.108.123.220/ADimageviewurl/牦牛肉.jpg"),
        var nickname: String = "",
        var content: String = "",
        var comId:Int=0,
        var likeStatus: Boolean = false,
        /*是否盖章*/
        var comname:String="",
        var isSure: Boolean = false,
        var likeAmount: Int = 0,
        var commentAmount: Int = 0,
        //        时间为字符串更好 我们获取到时间自己进行转换
        var timeYYD: String = "2017-12-12",
        var timeHHMM: String = "23:59"
) : Serializable
