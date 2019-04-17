package com.dragon.patternframework;

import android.util.Log;

/**
 * Created by 40774 on 2017/8/25.
 */

public class MyLog {
    private final static int CurrentOrder=5;
    public static void v(String tag, String content)
    {
        if (CurrentOrder>=1)
       Log.v(tag,content);
    }
    public static void d(String tag, String content)
    {
        if (CurrentOrder>=2)
            Log.d(tag,content);
    }
    public static void i(String tag, String content)
    {
        if (CurrentOrder>=3)
            Log.i(tag,content);
    }
    public static void w(String tag, String content)
    {
        if (CurrentOrder>=4)
            Log.w(tag,content);
    }
    public static void e(String tag, String content)
    {
        if (CurrentOrder>=5)
            Log.e(tag,content);
    }
}
