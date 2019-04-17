package com.dragon.patternframework.commodity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dragon.patternframework.R;
import com.dragon.patternframework.commodity.adapter.CommodityAdapter;
import com.dragon.patternframework.commodity.useinterface.CommoditySetting;
import com.dragon.patternframework.javaBean.Commodity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 40774 on 2017/12/4.
 */

public class CommodityDetails extends Fragment implements CommoditySetting {
    private RecyclerView recyclerView;
    private CommodityAdapter adapter;
    List<Commodity> products = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.commodity_details, container, false);
        recyclerView = view.findViewById(R.id.commodity_recyclerview);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new CommodityAdapter(getContext(), products);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setProduct(Commodity products) {
        if (adapter != null)
        {
            this.products.add(products);
            adapter.notifyDataSetChanged();
        }
        else {
//            先清空再运行
            this.products.add(products);
        }
    }
}
