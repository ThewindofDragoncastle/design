package com.dragon.patternframework.loadingview;

import android.app.ProgressDialog;
import android.content.Context;

import com.dragon.patternframework.R;


/**
 * Created by 40774 on 2017/12/20.
 */

public class MyLoading extends ProgressDialog {
    public MyLoading(Context context) {
        super(context);
    }

    @Override
    public void show() {
        super.show();
        setContentView(R.layout.progressdialog);
    }
}
