package com.heaven7.android.im.ry;

import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;

import io.rong.callkit.RongCallKit;
import io.rong.callkit.RongCallProxy;
import io.rong.calllib.IRongCallListener;
import io.rong.imlib.model.Conversation;

public final class RongYunKitWrapper {

    /** for team chat. we need get the all member of group id*/
    public static void init(Context context){
        RongCallKit.setGroupMemberProvider(new RongCallKit.GroupMembersProvider() {
            @Override
            public ArrayList<String> getMemberList(String groupId, final RongCallKit.OnGroupMembersResult result) {
                //todo 返回群组成员userId的集合
                return new ArrayList<String>(Arrays.asList("19981242180", "18111646505"));
            }
        });
    }
    /**
     * 发起单人通话。
     *
     * @param context   上下文
     * @param targetId  会话 id
     * @param mediaType 会话媒体类型
     */
    public void startCall(Context context, String targetId, RongCallKit.CallMediaType mediaType){
        RongCallKit.startSingleCall(context, targetId, mediaType);
    }
    /**
     * 发起多人通话
     *
     * @param context          上下文
     * @param type 会话类型
     * @param targetId         会话 id
     * @param mediaType        会话媒体类型
     * @param userIds          参与者 id 列表
     */
    public void startCallMulti(Context context, Conversation.ConversationType type,
                               String targetId, RongCallKit.CallMediaType mediaType, ArrayList<String> userIds){
        RongCallKit.startMultiCall(context, type, targetId, mediaType, userIds);
    }

    public void setAppCallListener(IRongCallListener l){
       // RongCallKit.setRongCallMissedListener();
        RongCallProxy.getInstance().setCallListener(l);
    }

}
