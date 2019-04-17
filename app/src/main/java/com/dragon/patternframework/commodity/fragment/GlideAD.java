package com.dragon.patternframework.commodity.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dragon.patternframework.R;
import com.dragon.patternframework.commodity.adapter.GlideADViewAdapter;
import com.dragon.patternframework.commodity.useinterface.GlideAdSetting;
import com.dragon.patternframework.javaBean.Commodity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.support.v4.view.ViewPager.SCROLL_STATE_DRAGGING;
import static android.support.v4.view.ViewPager.SCROLL_STATE_IDLE;
import static android.support.v4.view.ViewPager.SCROLL_STATE_SETTLING;

/**
 * Created by 40774 on 2017/12/1.
 */

public class GlideAD extends Fragment implements ViewPager.OnPageChangeListener, GlideAdSetting {
    ViewPager pager;
    List<Commodity> products;
    Handler handler;
    //    终止循环
    private boolean stop = false;
    //    原点指示器布局
    LinearLayout linearLayout;
    //   图片数量
    private int account = 0;
    //    关联
    final private Context context = getContext();
    final Runnable runnable = new Runnable() {
        @Override
        public void run() {
//                因为设置的是无线大所以随便加
//                只要活动未停止将一直执行
            if (!stop) {
                if (account != 0)
                    pager.setCurrentItem((pager.getCurrentItem() + 1) % account);
                handler.postDelayed(this, 2000);
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.glidead, container, false);
//        添加小圆点
//        容器
        pager = (ViewPager) view.findViewById(R.id.view_pager);
        linearLayout = (LinearLayout) view.findViewById(R.id.point_group);
        products = new ArrayList<>();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//设置 对viewpager进行监听
        pager.addOnPageChangeListener(this);
        account = products.size();
        GlideADViewAdapter adapter = new GlideADViewAdapter(this.products, context);
        pager.setAdapter(adapter);
        pager.setCurrentItem(0);
        startRun();
    }

    public void startRun() {

        handler = new Handler();
        handler.postDelayed(runnable, 2000);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (positionOffset > 0) {
//    从左至右滑动
//                    如果当前已经到达最大 那就跳转至0
            if (position == account - 1) {
                Log.d(getTag(), "已经到循环结尾");
                pager.setCurrentItem(0, true);
            }
        }
    }

    int lastPosition;

    @Override
    public void onPageSelected(int position) {
        int newposition = position % account;
        // 页面被选中
        // 设置当前页面选中
        linearLayout.getChildAt(newposition).setSelected(true);
        // 设置前一页不选中
        linearLayout.getChildAt(lastPosition).setSelected(false);
        // 替换位置
        lastPosition = newposition;
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        switch (state) {
            // 静止状态
            case SCROLL_STATE_IDLE:
                stop = false;
                break;
            // 拖拽中
            case SCROLL_STATE_DRAGGING:
                stop = true;
                break;
            // 拖拽后松手，自动回到最终位置的过程
            case SCROLL_STATE_SETTLING:
                stop = true;
                break;
        }
    }

    @Override
    public void setImage(List<Commodity> products) {
        //        伪代码 图片地址应从网络获得
        this.products.clear();
        for (Commodity product : products)
            Collections.addAll(this.products, product);
        //        设置显示
        account = this.products.size();
//        有缺陷该如何设置都没有反应
        pager.setAdapter(new GlideADViewAdapter(products, getActivity()));
//         adpater.notifyDataSetChanged();
//        清空视图
        linearLayout.removeAllViews();
        for (int i = 0; i < account; i++) {
//            小原点
            ImageView imageView = new ImageView(getContext());
            imageView.setImageResource(R.drawable.point_select);
//            选中第一个
            if (i == 0)
                imageView.setSelected(true);
            imageView.setSelected(false);
//            绘制大小
            int size = getResources().getDimensionPixelSize(R.dimen.normal_point_size);
            Log.d("大小：", size + "");
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, size);
//            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams();
            params.leftMargin = getResources().getDimensionPixelSize(R.dimen.leftmargin_point_size);
            imageView.setLayoutParams(params);
            // 添加到容器里
            linearLayout.addView(imageView);
            final int j = i;
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pager.setCurrentItem(j);
                }
            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        stop = false;
        handler.postDelayed(runnable, 2000);
    }

    @Override
    public void onStop() {
        super.onStop();
        stop = true;
    }
}
