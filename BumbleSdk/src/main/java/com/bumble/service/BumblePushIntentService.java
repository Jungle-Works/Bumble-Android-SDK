package com.bumble.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.bumble.BumbleConfig;
import com.bumble.activity.CampaignActivity;
import com.google.gson.Gson;


/**
 * Created by Bhavya Rattan on 26/05/17
 * Click Labs
 * bhavya.rattan@click-labs.com
 */

public class BumblePushIntentService extends IntentService {

    private static final String TAG = "BumblePushIntentService";

    public BumblePushIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Intent notificationIntent;
        boolean isPromotional = intent.getBooleanExtra("is_announcement_push", false);
        if (isPromotional) {
            if (BumbleConfig.getInstance() != null && !BumbleConfig.getInstance().isDataCleared()) {
                Intent broadcastIntent = new Intent(this, CampaignActivity.class);
                broadcastIntent.putExtra("is_promotional_push", true);
                broadcastIntent.putExtra("is_announcement_push", true);
                broadcastIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(broadcastIntent);
            } else {
                PackageManager pm = this.getPackageManager();
                notificationIntent = pm.getLaunchIntentForPackage(this.getPackageName());
                notificationIntent.putExtra("is_announcement_push", true);
                startActivity(notificationIntent);
            }
        }
    }
}
