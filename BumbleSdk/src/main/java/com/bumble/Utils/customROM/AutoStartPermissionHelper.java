package com.bumble.Utils.customROM;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AutoStartPermissionHelper {

    /***
     * Xiaomi
     */
    private String PACKAGE_XIAOMI_MAIN = "com.miui.securitycenter";
    private String PACKAGE_XIAOMI_COMPONENT = "com.miui.permcenter.autostart.AutoStartManagementActivity";
    private List<String> XIAOMI_PACKAGES = Collections.singletonList(PACKAGE_XIAOMI_COMPONENT);

    /***
     * Letv
     */
    private String PACKAGE_LETV_MAIN = "com.letv.android.letvsafe";
    private String PACKAGE_LETV_COMPONENT = "com.letv.android.letvsafe.AutobootManageActivity";
    private List<String> LETV_PACKAGES = Collections.singletonList(PACKAGE_LETV_COMPONENT);

    /***
     * ASUS ROG
     */
    private String PACKAGE_ASUS_MAIN = "com.asus.mobilemanager";
    private String PACKAGE_ASUS_COMPONENT = "com.asus.mobilemanager.powersaver.PowerSaverSettings";
    private List<String> ASUS_PACKAGES = Collections.singletonList(PACKAGE_ASUS_COMPONENT);

    /***
     * Honor
     */
    private String PACKAGE_HONOR_MAIN = "com.huawei.systemmanager";
    private String PACKAGE_HONOR_COMPONENT = "com.huawei.systemmanager.optimize.process.ProtectActivity";
    private List<String> HONOR_PACKAGES = Collections.singletonList(PACKAGE_HONOR_COMPONENT);

    /***
     * Huawei
     */
    private String PACKAGE_HUAWEI_MAIN = "com.huawei.systemmanager";
    private String PACKAGE_HUAWEI_COMPONENT = "com.huawei.systemmanager.optimize.process.ProtectActivity";
    private List<String> HUAWEI_PACKAGES = Collections.singletonList(PACKAGE_HUAWEI_COMPONENT);

    /**
     * Oppo
     */
    private String PACKAGE_OPPO_MAIN = "com.coloros.safecenter";
    private String PACKAGE_OPPO_MAIN_FALLBACK = "com.oppo.safe";
    private String PACKAGE_OPPO_COMPONENT = "com.coloros.safecenter.permission.startup.StartupAppListActivity";
    private String PACKAGE_OPPO_COMPONENT_FALLBACK = "com.oppo.safe.permission.startup.StartupAppListActivity";
    private String PACKAGE_OPPO_COMPONENT_FALLBACK_A = "com.coloros.safecenter.startupapp.StartupAppListActivity";
    private List<String> OPPO_PACKAGES = Arrays.asList(PACKAGE_OPPO_COMPONENT,PACKAGE_OPPO_COMPONENT_FALLBACK,PACKAGE_OPPO_COMPONENT_FALLBACK_A);

    /**
     * Vivo
     */

    private String PACKAGE_VIVO_MAIN = "com.iqoo.secure";
    private String PACKAGE_VIVO_MAIN_FALLBACK = "com.vivo.permissionmanager";
    private String PACKAGE_VIVO_COMPONENT = "com.iqoo.secure.ui.phoneoptimize.AddWhiteListActivity";
    private String PACKAGE_VIVO_COMPONENT_FALLBACK = "com.vivo.permissionmanager.activity.BgStartUpManagerActivity";
    private String PACKAGE_VIVO_COMPONENT_FALLBACK_A = "com.iqoo.secure.ui.phoneoptimize.BgStartUpManager";
    private List<String> VIVO_PACKAGES = Arrays.asList(PACKAGE_VIVO_COMPONENT,PACKAGE_VIVO_COMPONENT_FALLBACK,PACKAGE_VIVO_COMPONENT_FALLBACK_A);

    /**
     * Nokia
     */
    private String PACKAGE_NOKIA_MAIN = "com.evenwell.powersaving.g3";
    private String PACKAGE_NOKIA_COMPONENT = "com.evenwell.powersaving.g3.exception.PowerSaverExceptionActivity";
    private List<String> NOKIA_PACKAGES = Collections.singletonList(PACKAGE_NOKIA_COMPONENT);

    /**
     * OnePlus
     */
    private String PACKAGE_ONEPLUS_MAIN = "com.oneplus.security";
    private String PACKAGE_ONEPLUS_COMPONENT = "com.oneplus.security.chainlaunch.view.ChainLaunchAppListAct‌​ivity";
    private List<String> ONEPLUS_PACKAGES = Collections.singletonList(PACKAGE_ONEPLUS_COMPONENT);

    /**
     * Packages and components mapper
     */
    Map<String, List<String>> PACKAGES  = new HashMap<String, List<String>>() {{
        put(PACKAGE_XIAOMI_MAIN, XIAOMI_PACKAGES);
        put(PACKAGE_LETV_MAIN, LETV_PACKAGES);
        put(PACKAGE_ASUS_MAIN, ASUS_PACKAGES);
        put(PACKAGE_HONOR_MAIN, HONOR_PACKAGES);
        put(PACKAGE_HUAWEI_MAIN, HUAWEI_PACKAGES);
        put(PACKAGE_OPPO_MAIN, OPPO_PACKAGES);
        put(PACKAGE_OPPO_MAIN_FALLBACK, OPPO_PACKAGES);
        put(PACKAGE_VIVO_MAIN, VIVO_PACKAGES);
        put(PACKAGE_VIVO_MAIN_FALLBACK, VIVO_PACKAGES);
        put(PACKAGE_NOKIA_MAIN, NOKIA_PACKAGES);
        put(PACKAGE_ONEPLUS_MAIN, ONEPLUS_PACKAGES);
    }};

    private static AutoStartPermissionHelper INSTANCE = null;

    private AutoStartPermissionHelper() {

    }

    public static AutoStartPermissionHelper getInstance() {
        if (INSTANCE == null)
            INSTANCE = new AutoStartPermissionHelper();
        return INSTANCE;
    }

    public boolean isAutoStartPermissionAvailable(Context context) {
        List<ApplicationInfo> packages = context.getPackageManager().getInstalledApplications(0);
        for (ApplicationInfo appInfo: packages) {
            if (PACKAGES.keySet().contains(appInfo.packageName))
                return true;
        }
        return false;
    }


    public void getAutoStartPermission(Context context) {
        List<ApplicationInfo> packages = context.getPackageManager().getInstalledApplications(0);
        for (ApplicationInfo appInfo: packages) {
            String mainPackage = appInfo.packageName;
            if (PACKAGES.keySet().contains(mainPackage)) {
                List<String> components = PACKAGES.get(mainPackage);
                if (components != null && startIntent(context, mainPackage,components))
                    return;
            }
        }
    }

    private boolean startIntent(Context context, String mainPackage, List<String> components) {
        Intent intent = new Intent();

        for (String component: components) {
            try {
                intent.setComponent(new ComponentName(mainPackage, component));
                context.startActivity(intent);
                return true;
            } catch (Exception ignored) {

            }
        }

        return false;
    }


    /**
     * Jump to the home page of the specified application
     */
    private void showActivity(Context context, @NonNull String packageName) {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        context.startActivity(intent);
    }

    /**
     * Jump to the specified page for the specified application
     */
    private void showActivity(Context context, @NonNull String packageName, @NonNull String activityDir) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(packageName, activityDir));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }



    public static boolean isOPPO() {
        return Build.BRAND != null && Build.BRAND.toLowerCase().equals("oppo");
    }



    private void goOPPOSetting(Context context) {
        try {
            showActivity(context, "com.coloros.phonemanager");
        } catch (Exception e1) {
            try {
                showActivity(context, "com.oppo.safe");
            } catch (Exception e2) {
                try {
                    showActivity(context, "com.coloros.oppoguardelf");
                } catch (Exception e3) {
                    showActivity(context, "com.coloros.safecenter");
                }
            }
        }
    }

    public static boolean isVIVO() {
        return Build.BRAND != null && Build.BRAND.toLowerCase().equals("vivo");
    }

    private void goVIVOSetting(Context context) {
        showActivity(context, "com.iqoo.secure");
    }

    public static boolean isSamsung() {
        return Build.BRAND != null && Build.BRAND.toLowerCase().equals("samsung");
    }

    private void goSamsungSetting(Context context) {
        try {
            showActivity(context, "com.samsung.android.sm_cn");
        } catch (Exception e) {
            showActivity(context, "com.samsung.android.sm");
        }
    }



    public static void requestIgnoreBatteryOptimization(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                Intent intent = new Intent();
                String packageName = context.getPackageName();
                PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
                if (pm.isIgnoringBatteryOptimizations(packageName))
                    intent.setAction(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS);
                else {
                    intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                    intent.setData(Uri.parse("package:" + packageName));
                }
                context.startActivity(intent);
            } catch (Exception e) {
                Log.d("TAG", "Could not set ignoring battery optimization");
            }
        }
    }



    private void addAutoStartupswitch(Context context) {

        try {
            Intent intent = new Intent();
            String manufacturer = Build.MANUFACTURER .toLowerCase();
            String model= Build.MODEL;
            Log.d("DeviceModel",model.toString());

            switch (manufacturer){
                case "xiaomi":
                    intent.setComponent(new ComponentName("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity"));
                    break;
                case "oppo":
                    intent.setComponent(new ComponentName("com.coloros.safecenter", "com.coloros.safecenter.permission.startup.StartupAppListActivity"));
                    break;
                case "vivo":
                    intent.setComponent(new ComponentName("com.vivo.permissionmanager", "com.vivo.permissionmanager.activity.BgStartUpManagerActivity"));
                    break;
                case "Letv":
                    intent.setComponent(new ComponentName("com.letv.android.letvsafe", "com.letv.android.letvsafe.AutobootManageActivity"));
                    break;
                case "Honor":
                    intent.setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.optimize.process.ProtectActivity"));
                    break;
                case "oneplus":
                    intent.setComponent(new ComponentName("com.oneplus.security", "com.oneplus.security.chainlaunch.view.ChainLaunchAppListAct‌​ivity"));
                    break;
            }

            List<ResolveInfo> list = context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
            if  (list.size() > 0) {
                context.startActivity(intent);
            }
        } catch (Exception e) {
            Log.e("exc" , String.valueOf(e));
        }

    }


}