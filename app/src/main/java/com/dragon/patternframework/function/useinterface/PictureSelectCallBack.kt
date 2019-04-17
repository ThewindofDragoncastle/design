package com.dragon.patternframework.function.useinterface

import android.view.View
import android.widget.ImageView
import com.dragon.patternframework.function.adapter.PictureSelect

/**
 * Created by WYL on 2018/2/12.
 */
interface PictureSelectCallBack {
    fun delete(position: Int, lastHolder: PictureSelect.ViewHolder)
    fun imageSet(position: Int)
    fun addPhoto(view:View)
    fun loadImage(path: String,imageView: ImageView,isCallback:Boolean)
    fun takePhoto()
}