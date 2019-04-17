package com.dragon.patternframework;

import android.content.Context;

import java.io.IOException;
import java.util.Properties;

public class GetIP {
    private static final GetIP ourInstance = new GetIP();

    public static String getIp(Context context, String filename) {
        Properties pro=new Properties();
        try {
            pro.load(context.getResources().getAssets().open("system.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String ip=pro.getProperty(filename);
        return ip;
    }

    private GetIP() {
    }
}
