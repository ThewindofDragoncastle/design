package com.dragon.patternframework.commodity.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
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

import java.util.List;

/**
 * Created by 40774 on 2017/12/4.
 */

public class CommodityAdapter extends RecyclerView.Adapter<CommodityAdapter.ViewHolder> {
    private Context context;
    //    商品列表
    private List<Commodity> productList;

    public CommodityAdapter(Context context, List<Commodity> productList) {
        this.context = context;
        Log.i(getClass().getName(), "长度：" + productList.size());
        this.productList=productList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.commodity_recyclerview_item
                , parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Commodity product = productList.get(position);
        String commodity = "\u3000商品：" + product.getComLabel() + "\n";
        String price = "\u3000售价：¥" + product.getPrice() + "\n";
        String seller = "\u3000商家：" + product.getUser().getNickname() + "\n";
        holder.seller.setText(seller);
        holder.commodity.setText(commodity);
        holder.price.setText(price);
        RequestOptions options = new RequestOptions().placeholder(R.drawable.loading)
                .error(R.drawable.noload);
        /*加载第一张图片*/
        Glide.with(context).load( product.getImages().split(";")[0]).apply(options).into(holder.imageView);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CommodityDetailDisplay.class);
                intent.putExtra("commodity", product);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        ImageView imageView;
        TextView seller;
        TextView price;
        TextView commodity;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            imageView = itemView.findViewById(R.id.product_image);
            price = itemView.findViewById(R.id.product_price);
            seller = itemView.findViewById(R.id.product_seller);
            commodity = itemView.findViewById(R.id.product_commodity);
        }
    }
}
