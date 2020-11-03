package com.bumble.Utils.customROM;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;
import android.text.TextUtils;


import com.bumble.Utils.AndroidUtilities;

import java.lang.reflect.Method;

import com.bumblesdk.R;

// MIUI. Redefining Android.
// (not in the very best way I'd say)
public class XiaomiUtilities {

    // custom permissions
    public static final int OP_ACCESS_XIAOMI_ACCOUNT = 10015;
    public static final int OP_AUTO_START = 10008;
    public static final int OP_BLUETOOTH_CHANGE = 10002;
    public static final int OP_BOOT_COMPLETED = 10007;
    public static final int OP_DATA_CONNECT_CHANGE = 10003;
    public static final int OP_DELETE_CALL_LOG = 10013;
    public static final int OP_DELETE_CONTACTS = 10012;
    public static final int OP_DELETE_MMS = 10011;
    public static final int OP_DELETE_SMS = 10010;
    public static final int OP_EXACT_ALARM = 10014;
    public static final int OP_GET_INSTALLED_APPS = 10022;
    public static final int OP_GET_TASKS = 10019;
    public static final int OP_INSTALL_SHORTCUT = 10017;
    public static final int OP_NFC = 10016;
    public static final int OP_NFC_CHANGE = 10009;
    public static final int OP_READ_MMS = 10005;
    public static final int OP_READ_NOTIFICATION_SMS = 10018;
    public static final int OP_SEND_MMS = 10004;
    public static final int OP_SERVICE_FOREGROUND = 10023;
    public static final int OP_SHOW_WHEN_LOCKED = 10020;
    public static final int OP_BACKGROUND_START_ACTIVITY = 10021;
    public static final int OP_WIFI_CHANGE = 10001;
    public static final int OP_WRITE_MMS = 10006;

    public static void checkForDevicePermission(final Context context) {
        if (XiaomiUtilities.isMIUI()) {
            checkForMIDevicePermission(context);
            return;
        }
        if(AutoStartPermissionHelper.getInstance().isAutoStartPermissionAvailable(context)) {
            AutoStartPermissionHelper.getInstance().getAutoStartPermission(context);
            return;
        }
//        else if(isEligible(context)) {
//            onDozeReminder(context);
//        }
    }
    private static void checkForMIDevicePermission(final Context context) {
        if (Build.VERSION.SDK_INT >= 19
                && (!XiaomiUtilities.isCustomPermissionGranted(context, XiaomiUtilities.OP_SHOW_WHEN_LOCKED)
                || !XiaomiUtilities.isCustomPermissionGranted(context, XiaomiUtilities.OP_BACKGROUND_START_ACTIVITY))) {
            new AlertDialog.Builder(context)
                    .setTitle(R.string.app_name)
                    .setMessage(R.string.hippo_allow_permission)
                    .setPositiveButton(R.string.hippo_settings, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = XiaomiUtilities.getPermissionManagerIntent(context);
                            if (intent != null) {
                                try {
                                    context.startActivity(intent);
                                } catch (Exception x) {
                                    try {
                                        intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                        intent.setData(Uri.parse("package:" + context.getPackageName()));
                                        context.startActivity(intent);
                                    } catch (Exception xx) {
                                        xx.printStackTrace();
                                    }
                                }
                            }
                        }
                    })
                    .setNegativeButton(R.string.hippo_not_now, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .create().show();
        }
    }
    private static boolean isMIUI() {
        return !TextUtils.isEmpty(AndroidUtilities.getSystemProperty("ro.miui.ui.version.name"));
    }

    @SuppressWarnings("JavaReflectionMemberAccess")
    @TargetApi(19)
    private static boolean isCustomPermissionGranted(Context context, int permission) {
        try {
            AppOpsManager mgr = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            Method m = AppOpsManager.class.getMethod("checkOpNoThrow", int.class, int.class, String.class);
            int result = (int) m.invoke(mgr, permission, android.os.Process.myUid(), context.getPackageName());
            return result == AppOpsManager.MODE_ALLOWED;
        } catch (Exception x) {
            x.printStackTrace();
        }
        return true;
    }

    public static int getMIUIMajorVersion() {
        String prop = AndroidUtilities.getSystemProperty("ro.miui.ui.version.name");
        if (prop != null) {
            try {
                return Integer.parseInt(prop.replace("V", ""));
            } catch (NumberFormatException ignore) {
            }
        }
        return -1;
    }

    private static Intent getPermissionManagerIntent(Context context) {
        Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
        intent.putExtra("extra_package_uid", android.os.Process.myUid());
        intent.putExtra("extra_pkgname", context.getPackageName());
        return intent;
    }

    private static boolean isEligible(Context context) {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M          &&
                !((PowerManager)context.getSystemService(Context.POWER_SERVICE)).isIgnoringBatteryOptimizations(context.getPackageName());
    }

    private static void onDozeReminder(Context context) {
        if(isEligible(context)) {
            Intent intent = new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS,
                    Uri.parse("package:" + context.getPackageName()));
            context.startActivity(intent);
        }
    }


}