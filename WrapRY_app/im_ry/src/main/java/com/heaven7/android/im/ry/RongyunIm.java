package com.heaven7.android.im.ry;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

public class RongyunIm {

    private static RongyunIm sINSTANCE = new RongyunIm();
    private Application mContext;
    private String mUserId;

    public static void init(Context context){
        RongIM.init(context, "c9kqb3rdc4fmj");
        sINSTANCE.mContext = (Application) context.getApplicationContext();
        //RongCallModule.
    }
    public static RongyunIm get(){
        return sINSTANCE;
    }
    public void login(String cacheToken){
        if (mContext.getApplicationInfo().packageName.equals(getCurProcessName(mContext))) {
            RongIM.connect(cacheToken, new RongIMClient.ConnectCallback() {
                @Override
                public void onTokenIncorrect() {

                }
                @Override
                public void onSuccess(String s) {
                    Log.e("LoginActivity", "--onSuccess" + s);
                    Toast.makeText(mContext, "成功", Toast.LENGTH_LONG).show();
                    mUserId = s;
                }
                @Override
                public void onError(RongIMClient.ErrorCode e) {

                }
            });
        }
    }
    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }
}
