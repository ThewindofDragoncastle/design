package com.dragon.patternframework.commoditydetail.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import java.util.*

/**
 * Created by 40774 on 2017/12/4.
 */

class FragmentAdapter(fm: FragmentManager, fragments: List<Fragment>, names: List<String>) : FragmentPagerAdapter(fm) {
    private val fragments: MutableList<Fragment>
    private val names: MutableList<String>

    init {
        this.names = ArrayList()
        this.fragments = ArrayList()
        this.fragments.addAll(fragments)
        this.names.addAll(names)
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getPageTitle(position: Int): CharSequence {
        return names[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }
}
