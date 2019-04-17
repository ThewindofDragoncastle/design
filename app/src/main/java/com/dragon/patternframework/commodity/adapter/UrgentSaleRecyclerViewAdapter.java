package com.dragon.patternframework.commodity.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dragon.patternframework.R;
import com.dragon.patternframework.commoditydetail.activity.CommodityDetailDisplay;
import com.dragon.patternframework.javaBean.Commodity;

import java.util.List;

/**
 * Created by WYL on 2018/1/8.
 */

public class UrgentSaleRecyclerViewAdapter extends RecyclerView.Adapter<UrgentSaleRecyclerViewAdapter.ViewHolder> {
    //    急售的类 暂未接入数据 只有测试
    private Context mContext;
    private List<Commodity> commodity;

    public UrgentSaleRecyclerViewAdapter(Context context,List<Commodity> commodity) {
        mContext = context;
        this.commodity=commodity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.urgent_sale_recycler_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//        测试数据
        Log.d(getClass().getName(), "当前位置：" + position);
        holder.sale_info.setText(commodity.get(position).getNote());
       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               mContext.startActivity(new Intent(mContext, CommodityDetailDisplay.class));
           }
       });
    }

    @Override
    public int getItemCount() {
        return commodity.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView sale_detail;
        TextView sale_info;

        public ViewHolder(View itemView) {
            super(itemView);
            sale_detail = itemView.findViewById(R.id.urgent_sale_detail);
            sale_info = itemView.findViewById(R.id.urgent_sale_info);
        }
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
    }
}
