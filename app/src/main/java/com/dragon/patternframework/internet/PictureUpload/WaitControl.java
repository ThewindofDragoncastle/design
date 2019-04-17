package com.dragon.patternframework.internet.PictureUpload;

import com.dragon.patternframework.MyLog;

/**
 * Created by WYL on 2018/3/2.
 */

public class WaitControl {
    public void shouldWait() {
        try {
            synchronized (WaitControl.class) {
                MyLog.d("", "线程休眠"+Thread.currentThread().getName());
                WaitControl.class.wait();
                MyLog.d("", "线程唤醒");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void shouldWake() {
        synchronized (WaitControl.class) {
            WaitControl.class.notify();
        }
    }
}
