package com.dragon.patternframework.startuse

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.dragon.patternframework.R
import com.dragon.patternframework.commodity.adapter.FragmentAdapter
import kotlinx.android.synthetic.main.start_use.*

class MainActivity : AppCompatActivity(), ViewPager.OnPageChangeListener {
private var last=0
    private val fragments= listOf(Start1Fragment(),Start2Fragment(),Start3Fragment())
    override fun onPageScrollStateChanged(state: Int) {}

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

    override fun onPageSelected(position: Int) {
            start_linearLayout.getChildAt(position).isSelected = true
            start_linearLayout.getChildAt(last).isSelected = false
             last=position
        if (position==2)
            start_linearLayout.visibility= View.GONE
        else
            start_linearLayout.visibility= View.VISIBLE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start_use)
        setPoint(3)
        start_user_pager.addOnPageChangeListener(this)
        start_user_pager.adapter = FragmentAdapter(supportFragmentManager,
            fragments , listOf("1","2","3"))
        start_user_pager.currentItem=0

    }
    private fun setPoint(account:Int) {
        start_linearLayout.removeAllViews()
        for (i in 0 until account) {
            val imageView = ImageView(this)
            imageView.setImageResource(R.drawable.start_point_select)
//            选中第一个
            imageView.isSelected = i == 0
//            绘制大小
            val size = resources.getDimensionPixelSize(R.dimen.normal_point_size)
            Log.d("大小：", size.toString() + "")
            val params = LinearLayout.LayoutParams(size, size)
//            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams();
            params.leftMargin = resources.getDimensionPixelSize(R.dimen.leftmargin_point_size)
            imageView.layoutParams = params
            // 添加到容器里
            start_linearLayout.addView(imageView)
            val j = i
            imageView.setOnClickListener { start_user_pager.currentItem = j }
        }
    }
}
