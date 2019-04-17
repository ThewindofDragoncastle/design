package com.dragon.patternframework.commodity.useinterface;


import com.dragon.patternframework.javaBean.Commodity;

import java.util.List;

/**
 * Created by 40774 on 2017/12/4.
 */

public interface TabSetting {
    //    给外部修改的借口
    void setTabName(List<String> tabNames);

    //    给外部修改的借口
    void CustomerSetTabColor(int text_color, int select_color);

    void CustomerSetTabBackgroundColor(int color);

    void CustomerSetIndicationColor(int color);

    //    给主活动设置数据借口
    void setProduct(List<Commodity> products);
}
