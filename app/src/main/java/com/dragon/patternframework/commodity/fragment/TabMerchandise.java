package com.dragon.patternframework.commodity.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dragon.patternframework.MyLog;
import com.dragon.patternframework.R;
import com.dragon.patternframework.commodity.adapter.FragmentAdapter;
import com.dragon.patternframework.commodity.enumclass.TabName;
import com.dragon.patternframework.commodity.support.BufferData;
import com.dragon.patternframework.commodity.useinterface.TabSetting;
import com.dragon.patternframework.javaBean.Commodity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 40774 on 2017/12/2.
 */

public class TabMerchandise extends Fragment implements TabSetting {
    //    货物tab
    private TabLayout mlayout;
    private List<CommodityDetails> mfragments;
    //    暂存
    List<Commodity> products = new ArrayList<>();
    //    分页器
    private ViewPager mpager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.malltablayout, container, false);
        mlayout = view.findViewById(R.id.mall_tablayout);
        mpager = view.findViewById(R.id.commodity_viewpager);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        List<String> tab_names = new ArrayList<>();
        mfragments = new ArrayList<>();
        for (TabName tabName : TabName.values()) {
//           利用枚举类来封住名字
            tab_names.add(tabName.getValue());
        }
        setTabName(tab_names);
        for (int i = 0; i < tab_names.size(); i++) {
            CommodityDetails commodityDetails = new CommodityDetails();
            mfragments.add(commodityDetails);
        }
//        暂留
        List<Fragment> mfragments1 = new ArrayList<>();
        mfragments1.addAll(mfragments);
        mpager.setAdapter(new FragmentAdapter(getFragmentManager(), mfragments1, tab_names));
        mlayout.setupWithViewPager(mpager);
        setProduct(products);
//        设置初始化tab颜色
        //        将接口传入BufferData
        BufferData bufferData = BufferData.getInstance();
        bufferData.setTabSettingInterface(this);
        //        设置主题 根据用户设定 没有就默认
        String color =
                PreferenceManager.getDefaultSharedPreferences(getContext()).getString("custom_setting_theme", null);
        if (color != null) {
            bufferData.modifyTabBackgroundColor(Color.WHITE);
            bufferData.modifyTabColor(Color.BLACK, Color.parseColor(color));
            bufferData.modifyTabIndicationColor(Color.parseColor(color));
        }
    }

    @Override
    public void setTabName(List<String> tabNames) {
        mlayout.removeAllTabs();
        for (String name : tabNames) {
            mlayout.addTab(mlayout.newTab().setText(name));
        }
    }

    @Override
    public void CustomerSetTabColor(int text_color, int select_color) {
        mlayout.setTabTextColors(text_color, select_color);
    }


    @Override
    public void CustomerSetTabBackgroundColor(int color) {
        mlayout.setBackgroundColor(color);
    }

    @Override
    public void CustomerSetIndicationColor(int color) {
        mlayout.setSelectedTabIndicatorColor(color);
    }

    @Override
    public void setProduct(List<Commodity> products) {
        /*index其实还是数字*/
        String[] tab=getResources().getStringArray(R.array.mutCheck);
        if (mfragments == null) {
            this.products.clear();
            this.products.addAll(products);
        } else
//           数据分发
            for (Commodity product : products) {
//               逻辑复杂 大致是这样的 获得许多产品之后 对产品进行分拣
//               根据产品的标签分发到对应的tab下
//               但这样做增加了运算次数但是 降低了耦合性 对碎片进行标识也能做到 还简单
                /*获取标签中文*/
                String[] indexs=product.getTab().split(";");
                for (String ii:indexs)
                {
                    for (int i = 0; i < mlayout.getTabCount(); i++) {
                        String tabname=tab[Integer.parseInt(ii)];
                        if (mlayout.getTabAt(i).getText().equals(tabname))
                        {
                            MyLog.i("TAG","当前商品的商标为："+tabname);
                            mfragments.get(i).setProduct(product);
                        }
                    }
                }
            }
    }
}
