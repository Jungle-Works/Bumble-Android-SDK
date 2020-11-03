package com.bumble.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.bumble.BumbleConfig;
import com.bumble.Utils.BumbleLog;


/**
 * Basic service to work in background.
 *
 * @author gurmail
 */
public class HippoService extends Service {

    private static final String TAG = HippoService.class.getSimpleName();
    private static HippoService instance;

    public static HippoService getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        BumbleLog.i(TAG, "onCreate");
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int result = super.onStartCommand(intent, flags, startId);
        BumbleLog.i(TAG, "onStartCommand");
        BumbleConfig.getInstance().onServiceStarted();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BumbleLog.i(TAG, "onDestroy");
        BumbleConfig.getInstance().onServiceDestroy();
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        BumbleLog.i(TAG, "onDestroy");
        BumbleConfig.getInstance().onServiceDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static Intent createIntent(Context context) {
        return new Intent(context, HippoService.class);
    }

}
