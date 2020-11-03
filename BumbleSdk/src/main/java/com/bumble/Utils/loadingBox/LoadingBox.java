package com.bumble.Utils.loadingBox;

import android.app.Activity;
import android.app.Dialog;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.bumble.Utils.BumbleLog;
import com.bumble.Utils.Restring;
import com.bumblesdk.R;


/**
 * Class that holds ready to use dialogs
 */
public class LoadingBox {

    private static Dialog progressDialog;
    private static TextView tvProgress;
//    private static TextView innerProgress;

    /**
     * Shows the progress dialog
     *
     * @param activity
     * @return the {@link TextView} on which progress has to be set
     */
    public static void showOn(Activity activity) {

        showOn(activity, activity.getString(R.string.fugu_loading));
    }

    /**
     * Method to show the progress dialog with a message
     *
     * @param activity
     * @param message
     * @return
     */
    public static void showOn(final Activity activity, final String message) {

        try {
            /* Check if the last instance is alive */
            if (progressDialog != null)
                if (progressDialog.isShowing()) {
//                    tvProgress.setText(message);
                    return;
                }

            /*  Ends Here   */

            progressDialog = new Dialog(activity, R.style.Theme_AppCompat_Translucent);

            progressDialog.setContentView(R.layout.fugu_dialog_progress);

            tvProgress = (TextView) progressDialog.findViewById(R.id.tvProgress);
            tvProgress.setText(Restring.getString(activity, R.string.fugu_loading));
//            innerProgress = (TextView) progressDialog.findViewById(R.id.progress);
////            tvProgress.setTypeface(Font.getRegular(context));
//            tvProgress.setText(message);
//            innerProgress.setText("");

            ((ProgressWheel) progressDialog.findViewById(R.id.progress_wheel)).spin();

            Window dialogWindow = progressDialog.getWindow();
            WindowManager.LayoutParams layoutParams = dialogWindow.getAttributes();
            layoutParams.dimAmount = 0.6f;
            dialogWindow.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);

            progressDialog.show();

        } catch (Exception e) {
            e.printStackTrace();
            BumbleLog.e("PROGRESS DIALOG", "EXCEPTION: " + e.getMessage());
        }
    }

//    public static void updateProgress(int percentage) {
//        innerProgress.setText(Integer.toString(percentage) + "%");
//    }

    /**
     * Dismisses the Progress Dialog
     */
    public static boolean hide() {

        BumbleLog.e("PROGRESS DIALOG", "DISMISSED");

        if (progressDialog != null)
            if (progressDialog.isShowing()) {

                try {
                    progressDialog.dismiss();
                } catch (Exception ex) {
                    BumbleLog.e("Dismiss Loading Dialog", ex.toString());
                }
                progressDialog = null;
//                tvProgress = null;
//                innerProgress = null;
                return true;
            }

        return false;
    }
}
