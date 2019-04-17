package com.dragon.patternframework.commodity.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dragon.patternframework.R;

import com.dragon.patternframework.commoditydetail.activity.CommodityDetailDisplay;
import com.dragon.patternframework.javaBean.Commodity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 40774 on 2017/12/1.
 */

public class GlideADViewAdapter extends PagerAdapter {
    private List<Commodity> products = new ArrayList<>();
    private int account = 0;
    private Context context;

    public GlideADViewAdapter(List<Commodity> productList, Context context) {
        Log.i(getClass().getName(), "url数量：" + productList.size());
        this.context = context;
        products.addAll(productList);
        account = productList.size();
    }

    @Override
    public int getCount() {
        return account;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final Commodity product = products.get(position);
//        取模的原因 我们将个数设置的无限大 那么取模就相当于循环
        View view = LayoutInflater.from(context).inflate(R.layout.glidead_item, container
                , false);
//      容器间距
        TextView textView = view.findViewById(R.id.ad_text);
        ImageView imageView0 = view.findViewById(R.id.ad_image);
        RequestOptions options = new RequestOptions().placeholder(R.drawable.loading)
                .error(R.drawable.noload);
        /*加载第一张图片*/
        Glide.with(context).load(product.getImages().split(";")[0]).apply(options).into(imageView0);
//            首行缩进
        textView.setText("\u3000\u3000" + product.getNote());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CommodityDetailDisplay.class);
                intent.putExtra("commodity", product);
                context.startActivity(intent);
            }
        });
        container.setPadding(10, 10, 10, 10);
        if (!container.equals(view)) {
            container.addView(view);
        }
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        为了保留以前的视图
        container.removeView((View) object);
    }
}
