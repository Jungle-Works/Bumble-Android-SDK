package com.bumble.Utils;

import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Environment;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Patterns;

import androidx.appcompat.app.AlertDialog;


import com.bumble.BumbleConfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.regex.Pattern;

/**
 * Created by gurmail on 08/05/18.
 *
 * @author gurmail
 */

public class Utils {

    /**
     * Method to check whether a String is number
     *
     * @param value
     */
    public static boolean isNumeric(String value) {

        if (value == null)
            return false;

        return Pattern.matches("-?\\d+(\\.\\d+)?", value);
    }

    /**
     * Validates the character sequence with email format
     *
     * @param email
     * @return true, if the string entered by user is syntactically correct as
     * email, false otherwise
     */
    public static boolean isEmailValid(String email) {

        // Check whether the Email is valid
        if (email == null) return false;

        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    public final static boolean isValidPhoneNumber(CharSequence target) {
        if (target == null) {
            return false;
        } else {

            if (target.length() < 6 || target.length() > 17) {
                return false;
            } else {
                return android.util.Patterns.PHONE.matcher(target).matches();
            }
        }
    }

    public static boolean isValidNumber(CharSequence sequence) {
        if(sequence.length() < 9 && sequence.length() > 13)
            return false;
        return Patterns.PHONE.matcher(sequence).matches();
    }

    public static boolean containsNumber(String number) {
        return Pattern.compile("(\\+[0-9]+[\\- \\.]*)?"        // +<digits><sdd>*
                + "(\\([0-9]+\\)[\\- \\.]*)?"   // (<digits>)<sdd>*
                + "([0-9][0-9\\- \\.]+[0-9])").matcher(number).find();
    }


    private static final int    MULTIPLE_CLICK_THRESHOLD       = 2500; //in milli seconds
    // variable to track event time
    private static long mLastClickTime = 0;

    /**
     * Method to prevent multiple clicks
     */
    public static boolean preventMultipleClicks() {
        if (SystemClock.elapsedRealtime() - mLastClickTime < MULTIPLE_CLICK_THRESHOLD) {
            return false;
        }
        mLastClickTime = SystemClock.elapsedRealtime();
        return true;
    }

    public static AudioManager getAudioManager(Context context) {
        return (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
    }

    public static Bitmap getCircleBitmap(Bitmap bitmap) {
        try{
            final Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                    bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            final Canvas canvas = new Canvas(output);

            final int color = Color.RED;
            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            final RectF rectF = new RectF(rect);

            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(color);
            canvas.drawOval(rectF, paint);

            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bitmap, rect, rect, paint);

            bitmap.recycle();
            return output;

        }catch (Exception e){
            return bitmap;
        }

    }

    public static void saveFile(String fullPath, final String name, final String mime) {
        if (fullPath == null) {
            return;
        }

        File file = null;
        if (!TextUtils.isEmpty(fullPath)) {
            file = new File(fullPath);
            if (!file.exists() || AndroidUtilities.isInternalUri(Uri.fromFile(file))) {
                file = null;
            }
        }

        if (file == null) {
            return;
        }

        final File sourceFile = file;
        final boolean[] cancelled = new boolean[] {false};
        if (sourceFile.exists()) {
            /*AlertDialog progressDialog = null;
            if (context != null) {
                try {
                    progressDialog = new AlertDialog(HippoConfig.getInstance().getContext());
                    progressDialog.setMessage(Restring.getString(context, R.string.fugu_loading));
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.setCancelable(true);
                    //progressDialog.setOnCancelListener(dialog -> cancelled[0] = true);
                    progressDialog.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            final AlertDialog finalProgress = progressDialog;*/

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        File destFile;
                        {
                            File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                            dir.mkdir();
                            destFile = new File(dir, name);
                            if (destFile.exists()) {
                                int idx = name.lastIndexOf('.');
                                for (int a = 0; a < 10; a++) {
                                    String newName;
                                    if (idx != -1) {
                                        newName = name.substring(0, idx) + "(" + (a + 1) + ")" + name.substring(idx);
                                    } else {
                                        newName = name + "(" + (a + 1) + ")";
                                    }
                                    destFile = new File(dir, newName);
                                    if (!destFile.exists()) {
                                        break;
                                    }
                                }
                            }
                        }
                        if (!destFile.exists()) {
                            destFile.createNewFile();
                        }
                        boolean result = true;
                        long lastProgress = System.currentTimeMillis() - 500;
                        try (FileChannel source = new FileInputStream(sourceFile).getChannel(); FileChannel destination = new FileOutputStream(destFile).getChannel()) {
                            long size = source.size();
                            for (long a = 0; a < size; a += 4096) {
                                if (cancelled[0]) {
                                    break;
                                }
                                destination.transferFrom(source, a, Math.min(4096, size - a));
                                /*if (finalProgress != null) {
                                    if (lastProgress <= System.currentTimeMillis() - 500) {
                                        lastProgress = System.currentTimeMillis();
                                        final int progress = (int) ((float) a / (float) size * 100);
                                        AndroidUtilities.runOnUIThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    finalProgress.setProgress(progress);
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        });
                                        AndroidUtilities.runOnUIThread(() -> {
                                        try {
                                            finalProgress.setProgress(progress);
                                        } catch (Exception e) {
                                            FileLog.e(e);
                                        }
                                    });
                                    }
                                }*/
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            result = false;
                        }
                        if (cancelled[0]) {
                            destFile.delete();
                            result = false;
                        }

                        if (result) {
                            DownloadManager downloadManager = (DownloadManager) BumbleConfig.getInstance().getContext().getSystemService(Context.DOWNLOAD_SERVICE);
                            downloadManager.addCompletedDownload(destFile.getName(), destFile.getName(), false, mime, destFile.getAbsolutePath(), destFile.length(), true);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    /*if (finalProgress != null) {
                        AndroidUtilities.runOnUIThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    finalProgress.dismiss();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }*/
                }
            }).start();
        }
    }

}
