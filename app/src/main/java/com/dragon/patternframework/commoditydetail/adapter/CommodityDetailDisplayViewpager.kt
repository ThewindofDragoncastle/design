package com.dragon.patternframework.commoditydetail.adapter

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dragon.patternframework.R

/**
 * Created by WYL on 2018/1/12.
 */
class CommodityDetailDisplayViewpager(val context: Context, val urls: List<String>) : PagerAdapter() {
    /*Viewpager图片适配器*/
    override fun getCount(): Int {
        return urls.size + 2
    }

    /*展示图片设置*/
    override fun instantiateItem(container: ViewGroup?, position: Int): Any {
        val image = ImageView(context)
        image.scaleType = ImageView.ScaleType.CENTER_CROP
        container!!.addView(image)
        val options = RequestOptions().placeholder(R.drawable.loading).error(R.drawable.noload)
        /*加载的范围是0..4 0 1 2 3 4*/
        /*将最后的图片填充为原来第一个 第一个为原来最后一个*/
            val proUrl =  when (position) {
                0 -> urls.get(urls.size - 1)
                urls.size + 1 -> urls.get(0)
                else -> urls.get(position - 1)
            }
            Glide.with(context).load(proUrl)
                    .apply(options).into(image)
        return image
    }

    override fun isViewFromObject(view: View?, `object`: Any?): Boolean {
        return view == `object`
    }

    override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
        /*判断is as强制转换  然而大多数时候不需要强制转换*/
        if (`object` is View)
            container?.removeView(`object`)
    }
}