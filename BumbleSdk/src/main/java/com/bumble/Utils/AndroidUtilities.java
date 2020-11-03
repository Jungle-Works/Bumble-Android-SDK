package com.bumble.Utils;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Environment;

import com.bumble.BumbleConfig;

import java.io.File;

public class AndroidUtilities {

    @SuppressLint("PrivateApi")
    public static String getSystemProperty(String key) {
        try {
            Class props = Class.forName("android.os.SystemProperties");
            return (String) props.getMethod("get", String.class).invoke(null, key);
        } catch (Exception ignore) {
        }
        return null;
    }


    public static boolean isInternalUri(Uri uri) {
        String pathString = uri.getPath();
        if (pathString == null) {
            return false;
        }
        // Allow sending VoIP logs from cache/voip_logs
//        if (pathString.matches(Pattern.quote(new File(ApplicationLoader.applicationContext.getCacheDir(), "voip_logs").getAbsolutePath()) + "/\\d+\\.log")) {
//            return false;
//        }
        int tries = 0;
        while (true) {
            if (pathString != null && pathString.length() > 4096) {
                return true;
            }
            String newPath;
            try {
                newPath = Utilities.readlink(pathString);
            } catch (Throwable e) {
                return true;
            }
            if (newPath == null || newPath.equals(pathString)) {
                break;
            }
            pathString = newPath;
            tries++;
            if (tries >= 10) {
                return true;
            }
        }
        if (pathString != null) {
            try {
                String path = new File(pathString).getCanonicalPath();
                if (path != null) {
                    pathString = path;
                }
            } catch (Exception e) {
                pathString.replace("/./", "/");
                //igonre
            }
        }
        if (pathString.endsWith(".attheme")) {
            return false;
        }
        return pathString != null && pathString.toLowerCase().contains("/data/data/" + BumbleConfig.getInstance().getContext().getPackageName());
    }

    public static void runOnUIThread(Runnable runnable) {
        runOnUIThread(runnable, 0);
    }

    public static void runOnUIThread(Runnable runnable, long delay) {
        if (delay == 0) {
            BumbleConfig.getInstance().applicationHandler.post(runnable);
        } else {
            BumbleConfig.getInstance().applicationHandler.postDelayed(runnable, delay);
        }
    }

    public void getFilePath() {
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

    }

    public static String getDownloadPath() throws RuntimeException {
        File file;
        if (true) {
            file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            return file.getParent();
        } else {
            throw new RuntimeException("Something went wrong!");
        }
        //return "";
    }

}
