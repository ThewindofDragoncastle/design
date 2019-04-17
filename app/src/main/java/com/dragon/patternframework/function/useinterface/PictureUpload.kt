package com.dragon.patternframework.function.useinterface

/**
 * Created by WYL on 2018/2/26.
 */
interface PictureUpload {
    /*图片上传*/
    fun hideDialog()

    fun showDialog()
    fun showInfoProgress(pro: Int)
    fun showDialogError(e: String)
    fun cancelUpload()
}