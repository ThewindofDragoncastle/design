package com.dragon.patternframework.commoditydetail.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.dragon.patternframework.GetIP
import com.dragon.patternframework.MyLog
import com.dragon.patternframework.R
import com.dragon.patternframework.commoditydetail.activity.CommodityDetailDisplay
import com.dragon.patternframework.commoditydetail.activity.PostNeedOrDeleteActivity
import com.dragon.patternframework.commoditydetail.adapter.CommodityDetailDisplayViewpager
import com.dragon.patternframework.commoditydetail.useinterface.DetailDisplaysetting
import com.dragon.patternframework.internet.commodity.RemovePresenter
import com.dragon.patternframework.javaBean.Commodity
import kotlinx.android.synthetic.main.cd_glideviewholder.*

/**
 * Created by WYL on 2018/1/19.
 */
class GlideAd : Fragment(), DetailDisplaysetting, ViewPager.OnPageChangeListener {
    private lateinit var commodity: Commodity
    /*最低也要3张图*/
/*因为要将最后的坐标减一 所以初始坐标是1*/
    var last_position: Int = 1
    /*将给定的图片列表增加2个 0位置是倒数第二个的图片 最后的图片是0位置 可以方便预览时模拟一种无限循环的感觉*/
    val data = mutableListOf<String>()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.cd_glideviewholder, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        /*获取活动的商品信息*/
        val ac=activity as CommodityDetailDisplay
        commodity=ac.commodity
        /*这里有个陷阱Viewpager移动时实际上会加载上下一个那么意思说当
        * 滑动到第三个页面时 第一个页面被从容器中清除了 因此我们必须做好处理防止重复添加
        * 最简单的做法设置一个boolean量如果加载过了就设为false
        * 然而并不能 因为：这是要系统重新加载的 所以我们前面的变量全部初始化就ok*/
        display_activity_commodity.removeAllViews()
        data.clear()
        last_position = 1
        /*数据待接入*/
        price.text = "共计￥${commodity.price}"
        setViewpager()
        /*倒数第一个不能要 是一个空*/
        data.removeAt(data.size-1)
        data.add("http://39.108.123.220/Customer/background/beijing.jpg")
        MyLog.d(tag, "滚动横幅碎片，活动创建时执行.${data.size}")
        setPoint(data.size)
        detail_viewpager.currentItem = 1
        detail_viewpager.addOnPageChangeListener(this)
        setButton()
    }

    private fun setButton() {
        toright_page.alpha = 0.5f
        toleft_page.alpha = 0.5f
        toleft_page.setOnClickListener {
            detail_viewpager.currentItem--
        }
        toright_page.setOnClickListener {
            detail_viewpager.currentItem++
//            startActivity(Intent().setClass(this,MainActivity::class.java))
        }
        val id=(activity as CommodityDetailDisplay).id
        if (id!=-1)
            glideAd_postNeed.text="确定下架"
        glideAd_postNeed.setOnClickListener {
           if(id!=-1)
           {
               /*确定下架*/
                val pre=RemovePresenter.getInstance()
                pre.setRemoveSupInterface(activity as CommodityDetailDisplay)
                pre.connectInternet(GetIP.getIp(context,"removeCommodity"),commodity.id.toInt())
           }
            else
           {
               val intent= Intent(context, PostNeedOrDeleteActivity::class.java)
               intent.putExtra("product",commodity)
               context.startActivity(intent)
           }
        }
    }

    override fun setViewpager() {
        commodity.images.split(";").map {
            data.add(it)
        }
        detail_viewpager.adapter = CommodityDetailDisplayViewpager(context, data)
    }

    override fun setPoint(i: Int) {
        for (it in 0 until i) {
            val imageView = ImageView(context)
            imageView.setImageResource(R.drawable.point_select)
/*设置图片大小*/
            val size = 15
            val param = LinearLayout.LayoutParams(size, size)
            param.leftMargin = 8
            imageView.layoutParams = param
            imageView.setOnClickListener {
                /*问题在于分页器 有七页 而点击的位置只能0到4 故此只能令指数加1 与viewpage 1到5有效值
                * 保持一致 移除的位置应该是上一个位置-1 也就是回到原点的正确计数      */
                o ->
                detail_viewpager.currentItem = it + 1
                display_activity_commodity.getChildAt(last_position - 1).isSelected = false
                o.isSelected = true
                /*点击监听设置*/
                last_position = it + 1
            }
            when (it) {
                0 -> imageView.isSelected = true
                else -> imageView.isSelected = false
            }
            display_activity_commodity.addView(imageView)
        }
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPageSelected(position: Int) {
        MyLog.i(" ", "上一个点的位置：" + last_position)
        /*position last_position存在一个问题就是last_position不会达到5 */
        when (position) {
        /*就是1到data.size的范围*/
            in 1 until data.size + 1 -> {
                display_activity_commodity.getChildAt(position - 1).isSelected = true
                display_activity_commodity.getChildAt(last_position - 1).isSelected = false
                last_position = position
            }
            0 -> {
                /*能到达0这个点 只有手动 而且只有一种可能就是从右边向左边滑动
                上一个位置就是1  然后将页面到达data.size-1的范围*/
                last_position = 1
                detail_viewpager.currentItem = data.size
            }
            data.size + 1 -> {
                /*能到达data.size+1这个点 只有手动 而且只有一种可能就是从左边向右边滑动*/
                last_position = data.size
                detail_viewpager.currentItem = 1
            }
        }
    }
}