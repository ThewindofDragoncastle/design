package com.dragon.patternframework.commoditydetail.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.dragon.patternframework.R
import com.dragon.patternframework.commoditydetail.adapter.FragmentAdapter
import com.dragon.patternframework.commoditydetail.fragment.Comment
import com.dragon.patternframework.commoditydetail.fragment.GlideAd
import com.dragon.patternframework.commoditydetail.fragment.Trading
import com.dragon.patternframework.javaBean.Commodity
import com.dragon.patternframework.person.useinterface.ImageUploadInterface
import kotlinx.android.synthetic.main.activity_commodity_detail_display.*
import kotlinx.android.synthetic.main.commodity_toolbar.*

class CommodityDetailDisplay : AppCompatActivity() , ImageUploadInterface {
    override fun fail(e: String) {
        Toast.makeText(this,"撤销失败！$e",Toast.LENGTH_SHORT).show()
    }

    override fun success() {
        Toast.makeText(this,"撤销成功！",Toast.LENGTH_SHORT).show()
    }

    lateinit var commodity: Commodity
     var id=-1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_commodity_detail_display)
        /*添加返回前一个页面监听*/
        apptab_back.setOnClickListener { finish() }
        this.commodity= intent.getSerializableExtra("commodity") as Commodity
        id= intent.getIntExtra("id",-1)
        /*遍历标签名称*/
        val lsName = (0 until cd_tabLayout.tabCount).map { it -> cd_tabLayout.getTabAt(it)?.text as String }
        val lsFragment = listOf(GlideAd(), Trading(), Comment())
        cd_tabLayout.setupWithViewPager(cd_viewpager)
        cd_viewpager.adapter = FragmentAdapter(supportFragmentManager, lsFragment, lsName)
    }
}
