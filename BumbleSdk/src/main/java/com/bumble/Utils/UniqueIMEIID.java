package com.bumble.Utils;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;

import com.bumble.BumbleConfig;
import com.bumble.database.CommonData;

import java.util.UUID;

public class UniqueIMEIID {
    public static String getUniqueIMEIId(Context activity) {
        String android_id = "";
        try {
            try {
                android_id = Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID);
            } catch (Exception e) {
                if(BumbleConfig.DEBUG)
                    e.printStackTrace();
            }
            android_id = TextUtils.isEmpty(android_id) ? Build.SERIAL : android_id;
        } catch (Exception e) {
            if(BumbleConfig.DEBUG)
                e.printStackTrace();
        }

        android_id = TextUtils.isEmpty(android_id) ? "12345" : android_id;

        try {
            android_id = android_id + CommonData.getPackageName(activity);
        } catch (Exception e) {

        }
        return android_id;
    }

    public static String getUniqueId(Context activity) {
        String android_id = "";
        try {
            try {
                android_id = Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID);
            } catch (Exception e) {
                if(BumbleConfig.DEBUG)
                    e.printStackTrace();
            }
            android_id = TextUtils.isEmpty(android_id) ? Build.SERIAL : android_id;
        } catch (Exception e) {
            if(BumbleConfig.DEBUG)
                e.printStackTrace();
        }

        if(TextUtils.isEmpty(android_id)) {
            String uniqueID = Prefs.with(activity).getString("device_uuid", "");
            if(TextUtils.isEmpty(uniqueID)) {
                uniqueID = UUID.randomUUID().toString();
                Prefs.with(activity).save("device_uuid", uniqueID);
            }
        }

        android_id = TextUtils.isEmpty(android_id) ? "12345" : android_id;

        /*try {
            android_id = android_id;
        } catch (Exception e) {

        }*/
        return android_id;
    }


}
