package com.dragon.patternframework.internet.PictureUpload

/**
 * Created by WYL on 2018/2/26.
 */
interface PresenterCallBack {
    /*连接*/
    fun cancel()
    fun complete()
    fun fail()
    fun progressChange(order:Int,progress: Int)
}