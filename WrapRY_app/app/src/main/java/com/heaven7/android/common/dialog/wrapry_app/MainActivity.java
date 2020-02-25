package com.heaven7.android.common.dialog.wrapry_app;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.heaven7.android.im.ry.RongyunIm;
import com.heaven7.core.util.PermissionHelper;

import io.rong.callkit.RongCallKit;
import io.rong.callkit.RongCallModule;
import io.rong.calllib.IRongReceivedCallListener;
import io.rong.calllib.RongCallClient;
import io.rong.calllib.RongCallSession;

public class MainActivity extends AppCompatActivity implements PermissionHelper.ICallback{

    private static final boolean MINE = false;
    private static final String TARGET_UID = MINE ? "19981242180" : "18111646505";
    private final PermissionHelper mHelper = new PermissionHelper(this);
    private static final int CODE_AUDIO = 1;
    private static final int CODE_VIDEO = 2;

    //private final RongCallModule mModule = new RongCallModule();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RongCallClient.setReceivedCallListener(new IRongReceivedCallListener() {
            @Override
            public void onReceivedCall(RongCallSession rongCallSession) {
                System.out.println("onReceivedCall: " + rongCallSession.getCallerUserId());
                //RongCallClient.getInstance().acceptCall(rongCallSession.getCallId());
                Intent intent = RongCallModule.createVoIPIntent(MainActivity.this, rongCallSession, true);
                startActivity(intent);
            }
            @Override
            public void onCheckPermission(RongCallSession rongCallSession) {
                System.out.println("onCheckPermission >>> " + rongCallSession.getConversationType().getName());
                switch ( rongCallSession.getMediaType()){
                    case AUDIO:
                        mHelper.startRequestPermission(Manifest.permission.RECORD_AUDIO, CODE_AUDIO, MainActivity.this);
                        break;
                    case VIDEO:
                        String[] perss = {Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA};
                        mHelper.startRequestPermission(perss, new int[]{CODE_AUDIO, CODE_VIDEO}, MainActivity.this);
                        break;
                }
            }
        });
        //19981242180
        String token = MINE ? "mSv+FvEBRfo9LdohEarysmPnhnTjckiqfUwH1iECvick9n9PbY3M7cOag981vMNBbVCKLDL7RjyjRK6Abxu+0sUbmaXq5hq5" :
        "g2G8Kp3LmThwKSUPu2ahP1jNRHFuTk6X5Nqh0SMPlaJ48ALxAf9yBgahwh/s34iVmPxzuJzzMBs0oBdffc8KKVAtZGPEsQNl";
        RongyunIm.get().login(token);
    }

    public void onClickStartSingleAudio(View view) {
        RongCallKit.startSingleCall(this, TARGET_UID, RongCallKit.CallMediaType.CALL_MEDIA_TYPE_AUDIO);
    }

    public void onClickStartSingleVideo(View view) {
        RongCallKit.startSingleCall(this, TARGET_UID, RongCallKit.CallMediaType.CALL_MEDIA_TYPE_VIDEO);
    }

    @Override
    public void onRequestPermissionResult(String s, int i, boolean b) {
        System.out.println("onRequestPermissionResult >> " + s + " , state = " + b);
        if(b){
            RongCallClient.getInstance().onPermissionGranted();
        }else {
            RongCallClient.getInstance().onPermissionDenied();
        }
    }
    @Override
    public boolean handlePermissionHadRefused(String s, int i, Runnable runnable) {
        return false;
    }
}
