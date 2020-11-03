package com.bumble.apis;

import android.app.Activity;
import android.text.TextUtils;

import com.bumble.BumbleConfig;
import com.bumble.NotificationListener;
import com.bumble.constants.BumbleAppConstants;
import com.bumble.interfaces.OnClearNotificationListener;
import com.bumble.model.promotional.PromotionResponse;
import com.bumble.retrofit.APIError;
import com.bumble.retrofit.CommonParams;
import com.bumble.retrofit.CommonResponse;
import com.bumble.retrofit.ResponseResolver;
import com.bumble.retrofit.RestClient;

import java.util.ArrayList;

import static com.bumble.constants.BumbleAppConstants.USER_ID;

public class ApiGetMobileNotification {

    private Activity activity;
    private NotificationListener listener;
    private OnClearNotificationListener onClearNotificationListener;

    public ApiGetMobileNotification(Activity activity, NotificationListener listener) {
        this.activity = activity;
        this.listener = listener;
    }

    public ApiGetMobileNotification(Activity activity, OnClearNotificationListener onClearNotificationListener) {
        this.activity = activity;
        this.onClearNotificationListener = onClearNotificationListener;
    }

    public void getNotificationData(int startOffset, int endOffset) {

        if (TextUtils.isEmpty(BumbleConfig.getInstance().getAppKey()) ||
                TextUtils.isEmpty(BumbleConfig.getInstance().getUserData().getEnUserId()))
            return;
        CommonParams params = new CommonParams.Builder()
                .add(BumbleAppConstants.APP_SECRET_KEY, BumbleConfig.getInstance().getAppKey())
                .add("en_user_id", BumbleConfig.getInstance().getUserData().getEnUserId())
                .add("broadcast_type", 0)
                .add(USER_ID, BumbleConfig.getInstance().getUserData().getUserId())
//                .add("start_offset", startOffset)
//                .add("end_offset", endOffset)
                .build();

        params.getMap().remove(BumbleAppConstants.DEVICE_TYPE);
        params.getMap().remove(BumbleAppConstants.APP_VERSION);

        RestClient.getApiInterface().fetchMobilePush(params.getMap()).enqueue(new ResponseResolver<PromotionResponse>() {
            @Override
            public void success(PromotionResponse promotionResponse) {
                if (listener != null)
                    listener.onSucessListener(promotionResponse);
            }

            @Override
            public void failure(APIError error) {
                if (listener != null)
                    listener.onFailureListener();
            }
        });
    }

    //    delete_all_announcements
//    en_user_id: en_user_id,
//    app_secret_key: app_secret_key,
//    user_id: user_id,
//    obj.channel_ids = [channel_id]

    public void clearNotification(long channelId, final int position) {
        CommonParams.Builder builder = new CommonParams.Builder();
        builder.add(BumbleAppConstants.APP_SECRET_KEY, BumbleConfig.getInstance().getAppKey());
        builder.add("en_user_id", BumbleConfig.getInstance().getUserData().getEnUserId());
        builder.add(USER_ID, BumbleConfig.getInstance().getUserData().getUserId());


        if (channelId > 0) {
            ArrayList<Long> ids = new ArrayList<>();
            ids.add(channelId);
            builder.add("channel_ids", ids);
            builder.add("delete_all_announcements", 0);
        } else {
            builder.add("delete_all_announcements", 1);
        }

        CommonParams params = builder.build();

        params.getMap().remove("app_version");
        params.getMap().remove("device_type");

        RestClient.getApiInterface().clearMobilePush(params.getMap()).enqueue(new ResponseResolver<CommonResponse>(activity, true, true) {
            @Override
            public void success(CommonResponse promotionResponse) {
                if (onClearNotificationListener != null)
                    onClearNotificationListener.onSucessListener(position);
            }

            @Override
            public void failure(APIError error) {
                if (onClearNotificationListener != null)
                    onClearNotificationListener.onFailure();
            }
        });
    }

}

