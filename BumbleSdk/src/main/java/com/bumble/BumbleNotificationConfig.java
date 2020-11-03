package com.bumble;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.AudioAttributes;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.bumble.Utils.BumbleLog;
import com.bumble.activity.CampaignActivity;
import com.bumble.constants.BumbleAppConstants;
import com.bumble.database.CommonData;
import com.bumble.helper.P2pUnreadCount;
import com.bumble.helper.PushHandler;
import com.bumble.model.UnreadCountModel;
import com.bumble.service.BumblePushIntentService;
import com.bumblesdk.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;

import io.paperdb.Paper;

/**
 * Created by Bhavya Rattan on 19/05/17
 * Click Labs
 * bhavya.rattan@click-labs.com
 */

public class BumbleNotificationConfig implements BumbleAppConstants {

    private static final String TAG = BumbleNotificationConfig.class.getSimpleName();
    //    public static String fuguDeviceToken = "";
    public static final String CHANNEL_ONE_ID = "com.hippo.ONE";
    public static Long pushChannelId = -1L;
    public static Long pushLabelId = -1L;
    public static Long agentPushChannelId = -1L;
    public static boolean isChannelActivityOnPause = false;
    public static final String CHANNEL_ONE_NAME = "Default notification";

    public void setNotificationSoundEnabled(boolean notificationSoundEnabled) {
        this.notificationSoundEnabled = notificationSoundEnabled;
    }

    public void setSmallIcon(int smallIcon) {
        this.smallIcon = smallIcon;
    }

    public void setLargeIcon(int largeIcon) {
        this.largeIcon = largeIcon;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }


    private boolean notificationSoundEnabled = true;
    private int smallIcon = -1, largeIcon;
    private int priority;

    private static NotificationManager notificationManager;

    public boolean isHippoNotification(final Map<String, String> data) {
        if (data.containsKey("push_source") && data.get("push_source").equalsIgnoreCase("FUGU"))
            return true;
        else
            return false;
    }

    public boolean isHippoCallNotification(Context context, final Map<String, String> data) {
        try {
            Paper.init(context);
            JSONObject messageJson = new JSONObject(data.get("message"));
            try {
                if (isCallEnabled(context, messageJson.optString("call_type")) && messageJson.optInt("notification_type") == 14
                        && (messageJson.optString("video_call_type").equalsIgnoreCase("START_CALL")
                        || (messageJson.optString("video_call_type").equalsIgnoreCase("CALL_HUNG_UP")))) {
                    return true;
                } else if (messageJson.optInt("notification_type") == 14) {
                    return false;
                } else if (messageJson.optInt("notification_type") == 20) {
                    return true;
                } else if (messageJson.optInt("notification_type") == 25) {
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean isCallEnabled(Context context, String callType) {
        Paper.init(context);
        if (callType.equalsIgnoreCase("AUDIO")) {
            return CommonData.getAudioCallStatus();
        } else {
            if (CommonData.getVideoCallStatus()) {
                return true;
            }
        }
        return false;
    }

    public static void handleHippoPushNotification(final Context context, final Bundle bundle) {
        if (bundle != null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if ((bundle.containsKey("is_announcement_push") && bundle.getBoolean("is_announcement_push")) || (bundle.get("message")
                            != null && !bundle.get("message").toString().isEmpty())) {
                        Intent broadcastIntent = new Intent(context, CampaignActivity.class);
                        context.startActivity(broadcastIntent);
                    }
                }
            }, 1000);

        } else
            return;
    }

    public static void cancelNotification(Context ctx, int notifyId) {
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager nMgr = (NotificationManager) ctx.getSystemService(ns);
        nMgr.cancel(notifyId);
    }

    public void showNotification(final Context context, final Map<String, String> data) {
        try {
            Paper.init(context);
            BumbleLog.e("showing", "showing push");
            showUserNotification(context, data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Set notification priority as per API level of device
     */
    private int getPriority() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return NotificationManager.IMPORTANCE_MAX;
        } else {
            return Notification.PRIORITY_MAX;
        }
    }

    /**
     * @param data notification data
     */
    private void showUserNotification(final Context context, final Map<String, String> data) throws Exception {
        CommonData.setPushBoolean(true);

        try {
            JSONObject messageJson = new JSONObject(data.get("message"));

            try {
                if (CommonData.getVideoCallStatus() && messageJson.optInt("notification_type") == 14
                        && (messageJson.optString("video_call_type").equalsIgnoreCase("START_CALL")
                        || (messageJson.optString("video_call_type").equalsIgnoreCase("CALL_HUNG_UP")))) {
                    //videoCallPush(context, messageJson);

                } else if (messageJson.optInt("notification_type") == 14) {
                    return;
                } else if (messageJson.optInt("notification_type") == 23) {
                    new PushHandler().notificationMissedCall(context, messageJson, smallIcon == -1 ? R.drawable.hippo_default_notif_icon : smallIcon);
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ONE_ID,
                        CHANNEL_ONE_NAME, NotificationManager.IMPORTANCE_HIGH);

                if (messageJson.optInt("notification_type") == 1) {
                    notificationChannel.setSound(Settings.System.DEFAULT_NOTIFICATION_URI, getRingtoneAudioAttributes());
                }

                if (notificationManager == null) {
                    notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                }
                if (notificationManager != null) {
                    notificationManager.createNotificationChannel(notificationChannel);
                }
            }

            Intent mIntent = new Intent(NOTIFICATION_INTENT);
            Bundle dataBundle = new Bundle();
            for (String key : data.keySet()) {
                dataBundle.putString(key, data.get(key));
            }
            mIntent.putExtras(dataBundle);
            LocalBroadcastManager.getInstance(context).sendBroadcast(mIntent);
            Paper.init(context);

            if (messageJson.has("is_announcement_push") && messageJson.optInt("is_announcement_push", 0) == 1) {
                String title = messageJson.optString("title", "");
                String message = messageJson.optString("new_message", "");
                int disableReply = messageJson.optInt("disable_reply", 0);

                String removeLt = message.replaceAll("<", "&lt;");
                String removeGt = removeLt.replaceAll(">", "&gt;");
                String removeQuotes = removeGt.replaceAll("\"", "&quot;");
                message = removeQuotes.replaceAll("'", "&#39;");

                String deeplink = messageJson.optString("deep_link", "app://settings");

                Intent notificationIntent = new Intent(context, BumblePushIntentService.class);
                notificationIntent.putExtra("is_announcement_push", true);
                notificationIntent.putExtra("deeplink", deeplink);

                Bundle mBundle = new Bundle();
                for (String key : data.keySet()) {
                    mBundle.putString(key, data.get(key));
                }
                notificationIntent.putExtra("data", mBundle);
                if (CommonData.getPushFlags() != -1)
                    notificationIntent.setFlags(CommonData.getPushFlags());

                PendingIntent pi = PendingIntent.getService(context, (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE)
                        , notificationIntent, 0);

                String imageUrl = "";

                try {
                    imageUrl = messageJson.optJSONObject("custom_attributes").optJSONObject("image").optString("image_url", "");
                } catch (Exception e) {

                }

                if (!TextUtils.isEmpty(imageUrl)) {
                    bigImageNotifAsync(context, title, message, imageUrl, pi);
                } else {
                    showPromotionalPush(context, title, message, pi, null);
                }

                try {
                    if (messageJson.has("channel_id")) {
                        Long channelId = messageJson.optLong("channel_id", -1);
                        updateCount("" + channelId);
                    }
                } catch (Exception e) {

                }

            } else {
                long channelId = -1;
                long labelId = -1;
                int disableReply = messageJson.optInt("disable_reply", 0);
                String label = messageJson.optString("label", "");
                String title = messageJson.optString("title", "");
                String message = messageJson.optString("new_message", "");
                int channelType = messageJson.optInt("channel_type", 0);

                String removeLt = message.replaceAll("<", "&lt;");
                String removeGt = removeLt.replaceAll(">", "&gt;");
                String removeQuotes = removeGt.replaceAll("\"", "&quot;");
                message = removeQuotes.replaceAll("'", "&#39;");

                if (messageJson.has("channel_id") && channelType != 6)
                    channelId = messageJson.optLong("channel_id", -1);
                if (messageJson.has("label_id"))
                    labelId = messageJson.optLong("label_id", -1);

                if (pushChannelId != null && channelId > 0 && pushChannelId.compareTo(channelId) == 0) {
                    return;
                } else if (pushLabelId != null && labelId > 0 && pushLabelId.compareTo(labelId) == 0) {
                    return;
                }

                Intent notificationIntent = new Intent(context, BumblePushIntentService.class);
                notificationIntent.putExtra("channelId", channelId);
                notificationIntent.putExtra("en_user_id", CommonData.getUserDetails().getData().getEn_user_id());
                notificationIntent.putExtra("userId", CommonData.getUserDetails().getData().getUserId());
                notificationIntent.putExtra("labelId", labelId);
                notificationIntent.putExtra("label", label);
                notificationIntent.putExtra("disable_reply", disableReply);

                Bundle mBundle = new Bundle();
                for (String key : data.keySet()) {
                    mBundle.putString(key, data.get(key));
                }
                notificationIntent.putExtra("data", mBundle);
                if (CommonData.getPushFlags() != -1)
                    notificationIntent.setFlags(CommonData.getPushFlags());
                PendingIntent pi = PendingIntent.getService(context, (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE)
                        , notificationIntent, 0);

                int notificationDefaults = Notification.DEFAULT_ALL;
                if (!notificationSoundEnabled)
                    notificationDefaults = Notification.DEFAULT_LIGHTS;

                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ONE_ID)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(Html.fromHtml(message)))
                        .setSmallIcon(smallIcon == -1 ? R.drawable.hippo_default_notif_icon : smallIcon)
                        .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), largeIcon))
                        .setContentTitle(title)
                        .setContentText(Html.fromHtml(message))
                        .setContentIntent(pi)
                        .setPriority(priority)
                        .setAutoCancel(true);

                mBuilder.setDefaults(notificationDefaults);

                mBuilder.setChannelId(CHANNEL_ONE_ID);
                Notification notification = mBuilder.build();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    int smallIconViewId = context.getResources().getIdentifier("right_icon", "id", "android");
                    if (smallIconViewId != 0) {
                        if (notification.headsUpContentView != null)
                            notification.headsUpContentView.setViewVisibility(smallIconViewId, View.INVISIBLE);

                        if (notification.bigContentView != null)
                            notification.bigContentView.setViewVisibility(smallIconViewId, View.INVISIBLE);
                    }
                }

                if (notificationManager == null) {
                    notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                }
                notificationManager.notify((int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE), notification);

                try {
                    if (BumbleConfig.getInstance() != null && !BumbleConfig.getInstance().isChannelActivity()) {
                        if ((messageJson.has("channel_id") && BumbleNotificationConfig.pushChannelId.compareTo(messageJson.getLong("channel_id")) != 0)
                                || (messageJson.has("label_id") && BumbleNotificationConfig.pushLabelId.compareTo(messageJson.getLong("label_id")) != 0)) {
                            try {
                                final long channelid = channelId;
                                final long labelid = labelId;
                                addUnreadCount(channelid, labelid);
                            } catch (Exception e) {
                                //e.printStackTrace();
                            }
                        }

                    }
                } catch (Exception e) {
                    //e.printStackTrace();
                }

                updateChannelCount(messageJson);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void updateChannelCount(JSONObject messageJson) {
        try {
            String transactionId = messageJson.optString("chat_transaction_id", "");
            String muid = messageJson.optString("muid", "");
            long channelId = messageJson.optLong("channel_id", -1);
            if (!TextUtils.isEmpty(transactionId)) {
                if (messageJson.has("user_unique_key")) {
                    JSONArray jsonarray = messageJson.getJSONArray("user_unique_key");
                    String myUniqueId = CommonData.getAttributes().getCaptureUserData().getUserUniqueKey();
                    for (int i = 0; i < jsonarray.length(); i++) {
                        String jsonobject = jsonarray.getString(i);
                        if (!jsonobject.equalsIgnoreCase(myUniqueId) && P2pUnreadCount.INSTANCE.hasTransactionId(transactionId + "_" + jsonobject)) {
                            P2pUnreadCount.INSTANCE.updateChannelId(transactionId, channelId, muid);
                            break;
                        }
                    }
                } else {
                    P2pUnreadCount.INSTANCE.updateChannelId(transactionId, channelId, muid);
                }
            }
        } catch (Exception e) {

        }
    }

    private void updateCount(String channelId) {
        if (BumbleConfig.getInstance().getCallbackListener() == null) {
            return;
        }
        if (!BumbleConfig.getInstance().isAnnouncements()) {
            HashSet<String> count = CommonData.getAnnouncementCount();
            count.add(channelId);
            CommonData.setAnnouncementCount(count);
            if (BumbleConfig.getInstance().getCallbackListener() != null) {
                BumbleConfig.getInstance().getCallbackListener().unreadAnnouncementsCount(count.size());
            }
        }
    }

    private void addUnreadCount(final Long channelId, final Long labelId) {
        try {
            BumbleLog.e(TAG, "In count");
            if (BumbleConfig.getInstance().getCallbackListener() == null) {
                return;
            }
            if (!BumbleConfig.getInstance().isChannelActivity()) {
                ArrayList<UnreadCountModel> unreadCountModels;
                int index = -1;
                if (channelId > 0) {
                    index = CommonData.getUnreadCountModel().indexOf(new UnreadCountModel(channelId));
                } else if (labelId > 0) {
                    if (CommonData.getUnreadCountModel() != null) {
                        ArrayList<UnreadCountModel> unreadCountModel = CommonData.getUnreadCountModel();
                        for (int i = 0; i < unreadCountModel.size(); i++) {
                            if (unreadCountModel.get(i).getLabelId().compareTo(labelId) == 0) {
                                index = i;
                                break;
                            }
                        }
                    }
                }
                BumbleLog.v(TAG, "index = " + index);
                if (index > -1) {
                    unreadCountModels = CommonData.getUnreadCountModel();
                    BumbleLog.v(TAG, "unreadCountModels = " + unreadCountModels.size());
                    BumbleLog.v(TAG, "unreadCountModels.get(index).getCount() = " + unreadCountModels.get(index).getCount());
                    int channelCount = unreadCountModels.get(index).getCount() + 1;
                    BumbleLog.v(TAG, "channelCount = " + channelCount);
                    unreadCountModels.get(index).setCount(channelCount);
                    CommonData.setUnreadCount(new ArrayList<UnreadCountModel>());
                    CommonData.setUnreadCount(unreadCountModels);
                } else {
                    int channelCount = 1;
                    UnreadCountModel countModel = new UnreadCountModel(channelId, labelId, channelCount);
                    unreadCountModels = CommonData.getUnreadCountModel();
                    BumbleLog.v(TAG, "unreadCountModels = " + unreadCountModels.size());
                    unreadCountModels.add(countModel);
                    CommonData.setUnreadCount(unreadCountModels);
                }

                int count = 0;
                for (int i = 0; i < unreadCountModels.size(); i++) {
                    count = count + unreadCountModels.get(i).getCount();
                }

                BumbleLog.v(TAG, "count = " + count);

                if (BumbleConfig.getInstance().getCallbackListener() != null) {
                    BumbleConfig.getInstance().getCallbackListener().count(count);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Clears notification tray messages
    public static void clearNotifications(Context context, ArrayList<Integer> ids) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (ids != null && ids.size() > 0) {
            for (Integer integer : ids) {
                notificationManager.cancel(integer);
            }
        }
    }

    public static long getTimeMilliSec(String timeStamp) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        try {
            if (!TextUtils.isEmpty(timeStamp)) {
                Date date = format.parse(timeStamp);
                return date.getTime();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }


    private void bigImageNotifAsync(final Context context, final String title, final String message, String url, final PendingIntent pi) {
        try {
            Glide.with(context)
                    .asBitmap()
                    .load(url)
                    .into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            showPromotionalPush(context, title, message, pi, resource);
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {
                            showPromotionalPush(context, title, message, pi, null);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showPromotionalPush(Context context, String title, String message, PendingIntent pi, Bitmap bitmap) {
        int notificationDefaults = Notification.DEFAULT_ALL;
        if (!notificationSoundEnabled)
            notificationDefaults = Notification.DEFAULT_LIGHTS;

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ONE_ID)
                .setSmallIcon(smallIcon == -1 ? R.drawable.hippo_default_notif_icon : smallIcon)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), largeIcon))
                .setContentTitle(title)
                .setContentText(Html.fromHtml(message))
                .setContentIntent(pi)
                .setPriority(priority)
                .setAutoCancel(true);

        if (bitmap == null) {
            mBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText(Html.fromHtml(message)));
        } else {
            mBuilder.setLargeIcon(bitmap);
            mBuilder.setStyle(new NotificationCompat.BigPictureStyle()
                    .bigPicture(bitmap)
                    .bigLargeIcon(null).setBigContentTitle(title).setSummaryText(message));
        }

        mBuilder.setDefaults(notificationDefaults);
        mBuilder.setChannelId(CHANNEL_ONE_ID);
        Notification notification = mBuilder.build();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int smallIconViewId = context.getResources().getIdentifier("right_icon", "id", "android");
            if (smallIconViewId != 0) {
                if (notification.headsUpContentView != null)
                    notification.headsUpContentView.setViewVisibility(smallIconViewId, View.INVISIBLE);

                if (notification.bigContentView != null)
                    notification.bigContentView.setViewVisibility(smallIconViewId, View.INVISIBLE);
            }
        }

        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify((int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE), notification);
    }

    @TargetApi(21)
    private static AudioAttributes getRingtoneAudioAttributes() {
        return new AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build();
    }

    /*private JobScheduler mJobScheduler;

    public JobScheduler getJobScheduler(Context context) {
        if(mJobScheduler == null)
            mJobScheduler = (JobScheduler) context.getSystemService( Context.JOB_SCHEDULER_SERVICE );
        return mJobScheduler;
    }

    private void setJobScheduler(Context context) {
        JobScheduler mJobScheduler = getJobScheduler(context);
        JobInfo.Builder builder = new JobInfo.Builder( 1, new ComponentName( context.getPackageName(), JobSchedulerService.class.getName()));
        if( mJobScheduler.schedule( builder.build() ) <= 0 ) {
            //If something goes wrong
        }
    }*/
}
