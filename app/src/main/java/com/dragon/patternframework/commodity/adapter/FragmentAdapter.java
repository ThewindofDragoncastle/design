package com.dragon.patternframework.commodity.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 40774 on 2017/12/4.
 */

public class FragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    private List<String> names;

    public FragmentAdapter(FragmentManager fm
            , List<Fragment> fragments, List<String> names) {
        super(fm);
        this.names = new ArrayList<>();
        this.fragments = new ArrayList<>();
        this.fragments.addAll(fragments);
        this.names.addAll(names);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return names.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
