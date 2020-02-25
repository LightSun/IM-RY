package com.heaven7.android.im.ry;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import io.rong.calllib.IRongReceivedCallListener;
import io.rong.calllib.RongCallClient;
import io.rong.calllib.RongCallCommon;
import io.rong.calllib.RongCallSession;
import io.rong.imlib.model.Conversation;
import io.rong.rongcall.StartCameraCallback;

public final class RongYunLibWrapper {

    /** for team chat. we need get the all member of group id*/
    public static void init(Context context, final IRongReceivedCallListener l){
        RongCallClient.setReceivedCallListener(new IRongReceivedCallListener() {
            /**
             * 来电回调
             * @param callSession 通话实体
             */
            @Override
            public void onReceivedCall(RongCallSession callSession) {
                //accept or hangup the call
                RongCallClient.getInstance().acceptCall(callSession.getCallId());
            }
            /**
             * targetSDKVersion>＝23时检查权限的回调。当targetSDKVersion<23的时候不需要实现。
             * 在这个回调里用户需要使用Android6.0新增的动态权限分配接口requestCallPermissions通知用户授权，
             * 然后在onRequestPermissionResult回调里根据用户授权或者不授权分别回调
             * RongCallClient.getInstance().onPermissionGranted()和
             * RongCallClient.getInstance().onPermissionDenied()来通知CallLib。
             * 其中audio call需要获取Manifest.permission.RECORD_AUDIO权限；
             * video call需要获取Manifest.permission.RECORD_AUDIO和Manifest.permission.CAMERA两项权限。
             * @param callSession 通话实体
             */
            @Override
            public void onCheckPermission(RongCallSession callSession) {
                l.onCheckPermission(callSession);
            }
        });
    }
    /**
     * 发起音视频听话
     * @param type 会话类型, 如果实现的是不基于群组的voip，那次参数必须传 {@linkConversation.ConversationType#NONE}
     * @param targetId  目标会话id, 如果实现的是不基于群组的voip，那此参数无意义，传null
     * @param userIds   邀请参与通话的用户ID列表
     * @param observerUserIds   观察者列表,无观察者可传 null;
     * @param mediaType 发起的通话媒体类型
     * @param extra 附加信息
     * @return  呼叫id
     */
    public String startCall(Conversation.ConversationType type, String targetId,
                            List<String> userIds,
                            List<String> observerUserIds, RongCallCommon.CallMediaType mediaType, String extra){
        return RongCallClient.getInstance().startCall(type, targetId , userIds, observerUserIds, mediaType, extra);
    }

    public void acceptCall(String callId){
        RongCallClient.getInstance().acceptCall(callId);
    }
    public void acceptCall(String callId, int cameraId, boolean mirror, StartCameraCallback callback){
        RongCallClient.getInstance().acceptCall(callId, cameraId, mirror, callback);
    }
    public void hangUp(String callId){
        RongCallClient.getInstance().hangUpCall(callId);
    }
    public RongCallSession getCallSession(){
        return RongCallClient.getInstance().getCallSession();
    }
    /**
     * 邀请用户加入当前通话（仅限讨论组和群组）
     *
     * @param callId  通话id
     * @param userIds 邀请的用户id列表
     * @param observerUserIds 邀请的观察者列表,没有观察者可以传 null
     */
    public void addParticipants(String callId, ArrayList<String> userIds,ArrayList<String> observerUserIds){
        RongCallClient.getInstance().addParticipants(callId, userIds, observerUserIds);
    }
}
