package com.dragon.patternframework.function.adapter

import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * Created by WYL on 2018/1/11.
 */

class MessageFragmentAdapter(private val fm: FragmentManager,
                             private val fragment_list: List<Fragment>, private val layout: TabLayout) : FragmentPagerAdapter(fm) {
    private val name_list: List<String>

    init {
        val count = layout.tabCount
        name_list = (0 until count).map { it -> layout.getTabAt(it)?.text.toString() }

    }

    /*主页标题栏 Tablayout ViewPager碎片适配器*/
    override fun getCount(): Int {
        return name_list.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return name_list[position]
    }

    override fun getItem(position: Int): Fragment {
        return fragment_list.get(position)
    }

}
