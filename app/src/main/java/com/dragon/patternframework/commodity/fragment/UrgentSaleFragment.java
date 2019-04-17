package com.dragon.patternframework.commodity.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dragon.patternframework.MyLog;
import com.dragon.patternframework.R;
import com.dragon.patternframework.commodity.adapter.UrgentSaleRecyclerViewAdapter;
import com.dragon.patternframework.commodity.useinterface.ChangeUrgent;
import com.dragon.patternframework.internet.urgentCom.Presenter;
import com.dragon.patternframework.javaBean.Commodity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by 40774 on 2018/1/7.
 */

public class UrgentSaleFragment extends Fragment implements ChangeUrgent{
    private List<Commodity> commodities=new ArrayList<>();
    private RecyclerView mRecyclerView;
    private Context mContext;
    private boolean IsReserve = false;
    //    退出程序时中断线程
    private volatile boolean IsStop = false;
    private Runnable Auto_Scroll;
    //    强制让线程进行休眠
    private volatile boolean IsPause = false;
    private int last_position = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.urgent_sale, container, false);
        mRecyclerView = view.findViewById(R.id.urgent_sale_recycler_view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getContext();
        mRecyclerView.setAdapter(new UrgentSaleRecyclerViewAdapter(mContext,commodities));
        final LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(manager);
/*逻辑：跳转位置就ok了*/
        Auto_Scroll = new Runnable() {
            @Override
            public void run() {
                while (!IsPause) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    last_position++;
                    MyLog.d(getClass().getName(), "last_position=" + last_position % 5 + " 线程：" + Thread.currentThread().getName());
                    if (!IsPause)
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mRecyclerView.scrollToPosition(last_position % 5);
                            }
                        });

                }
            }
        };
        new Thread(Auto_Scroll).start();
        Properties pro=new Properties();
        try {
            pro.load(getResources().getAssets().open("system.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String ip=pro.getProperty("allcommodity");
        Presenter presenter=Presenter.getInstance();
        presenter.setChangeUrgentInterface(this);
        presenter.connectInternet(ip);
    }

    @Override
    public void onResume() {
        super.onResume();
        IsPause = false;
        new Thread(Auto_Scroll).start();
    }

    @Override
    public void onDestroy() {
        IsPause = true;
        super.onDestroy();
    }

    @Override
    public void onStop() {
        IsPause = true;
        super.onStop();
    }

    @Override
    public void onDetach() {
        IsPause = true;
        super.onDetach();
    }

    @Override
    public void setProduct(List<Commodity> commodities) {
        this.commodities.clear();
        this.commodities.addAll(commodities);
    }
}
//        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
//            /*            暂时未完成 ？
//            目标：滑动时暂停*/
//            @Override
//            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
//                switch (e.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        if (IsPause)
//                            IsPause = false;
//                        else
//                            IsPause = true;
//                        if (!IsPause)
//                            synchronized (UrgentSaleFragment.class) {
//                                UrgentSaleFragment.class.notify();
//                            }
//                        break;
//                           /*
//*无需进行阻断*/
//                    case MotionEvent.ACTION_MOVE:
//
//                }
//                // onInterceptTouchEvent是ViewGroup提供的方法，默认返回false，返回true表示拦截。
//                return false;
//            }
//
//            @Override
//            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
//
//            }
//
//
//            @Override
//            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
//                //父层的View截获touch事件 是否向上分发 true表示转发
//                mRecyclerView.requestDisallowInterceptTouchEvent(true);
//            }
//        });

/*逻辑说明：
开辟子线程运行,首先进行是否进行原子级判断，以方便活动终止时，线程停止。
判断是向下还是向上滑动，如果是向下进行判断
mRecyclerView.canScrollVertically(1)是否能向下滑动
如果能则进行滑动 反之。将中断标志改为反方向 进行同样操作
如果希望有个反转的缓冲时间 可以在时间反转设置个休眠
同时设置了一个刷新时间设定 暂时定为20ms
*/
/*
runnable= new Runnable() {
            @Override
            public void run(){
                //                1代表向下滑动
                while (!IsStop) {
//                    休眠标志
                    while (IsPause)
                        try {
                            synchronized (UrgentSaleFragment.class)
                            {
                                MyLog.d(getTag(),"滚动暂停");
                                UrgentSaleFragment.class.wait();
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();

                    }
                    MyLog.d("是否反转：", "IsReserve=" + IsReserve);
                    if(!IsReserve) {
                        if (mRecyclerView.canScrollVertically(1)) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mRecyclerView.scrollBy(0, 1);
                                }
                            });
                        }
                        else
                        {
                            IsReserve=true;
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }else  {
                        if (mRecyclerView.canScrollVertically(-1)) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mRecyclerView.scrollBy(0, -1);
                                }
                            });
                        }else
                        {
                            IsReserve=false;
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };


       Auto_Scroll= new Thread(runnable);
      Auto_Scroll.start();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        IsStop=true;
    }

    @Override
    public void onStop() {
        super.onStop();
        IsPause=false;
        IsStop=true;
    }

    @Override
    public void onResume() {
//        当线程位于栈顶而且不为空 或者活跃 重新开启线程
        super.onResume();
        IsStop=false;
        if(Auto_Scroll!=null)
        if(!Auto_Scroll.isAlive()) {
            Auto_Scroll=new Thread(runnable);
//            Auto_Scroll.start();
        }
        IsPause=false;
//        如果在栈顶 动画运行
        if(!IsPause)
            synchronized (UrgentSaleFragment.class)
            {
                UrgentSaleFragment.class.notify();
            }
    }
    */

