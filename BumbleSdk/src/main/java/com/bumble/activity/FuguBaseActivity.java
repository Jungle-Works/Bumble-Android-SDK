package com.bumble.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bumble.BumbleConfig;
import com.bumble.Utils.BumbleLog;
import com.bumble.constants.BumbleAppConstants;
import com.bumble.database.CommonData;
import com.bumble.retrofit.CommonParams;
import com.bumblesdk.R;

import org.apache.http.BuildConfig;
import org.json.JSONException;
import org.json.JSONObject;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class FuguBaseActivity extends AppCompatActivity implements BumbleAppConstants {
    private static final String TAG = FuguBaseActivity.class.getSimpleName();

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setStatusBarColor();
        }
        uncaughtExceptionError();
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            try {
                registerReceiver(new FuguNetworkStateReceiver(),
                        new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
            } catch (Exception e) {
                HippoLog.e(TAG, "Error in broadcasting");
            }
        }*/
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setStatusBarColor() {
        Window window = getWindow();


        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        // finally change the color
        window.setStatusBarColor(CommonData.getColorConfig().getHippoStatusBar());

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorAccentDark_light, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(CommonData.getColorConfig().getHippoStatusBar());
        }*/
    }



    protected boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }


    /**
     * Uncaught Exception encountered
     */
    private void uncaughtExceptionError() {
        Thread.setDefaultUncaughtExceptionHandler(
                new Thread.UncaughtExceptionHandler() {
                    @Override
                    public void uncaughtException(Thread paramThread, Throwable paramThrowable) {
                        //Do your own error handling here
                        BumbleLog.e("unCaughtException paramThread", "---> " + paramThread.toString());
                        BumbleLog.e("unCaughtException paramThrowable", "---> " + paramThrowable.toString());
                        StringWriter stackTrace = new StringWriter();
                        paramThrowable.printStackTrace(new PrintWriter(stackTrace));
                        BumbleLog.e("unCaughtException stackTrace", "---> " + stackTrace);
                        System.err.println(stackTrace);
                        if(!BumbleConfig.DEBUG)
                            apiSendError(stackTrace.toString());
                    }
                });
    }

    /**
     * APi to send error messages to server
     *
     * @param logs log to be sent
     */
    public void apiSendError(String logs) {
        if (isNetworkAvailable()) {
            PackageInfo pInfo = null;
            try {
                pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            JSONObject error = new JSONObject();
            try {
                error.put("log", logs);
                if (pInfo != null) {
                    error.put("version", pInfo.versionCode);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            HashMap<String, Object> params = new HashMap<>();
            params.put(BumbleAppConstants.APP_SECRET_KEY, BumbleConfig.getInstance().getAppKey());
            params.put(BumbleAppConstants.DEVICE_TYPE, ANDROID_USER);
            params.put(APP_VERSION, BuildConfig.VERSION_CODE);
            params.put(BumbleAppConstants.DEVICE_DETAILS, CommonData.deviceDetails(FuguBaseActivity.this));
            params.put(BumbleAppConstants.ERROR, error.toString());

            CommonParams commonParams = new CommonParams.Builder()
                    .putMap(params)
                    .build();

//            RestClient.getApiInterface().sendError(commonParams.getMap())
//                    .enqueue(new ResponseResolver<CommonResponse>(FuguBaseActivity.this, false, true) {
//                        @Override
//                        public void success(CommonResponse commonResponse) {
//                            HippoLog.v("success", commonResponse.toString());
//                        }
//
//                        @Override
//                        public void failure(APIError error) {
//                            HippoLog.v("failure", error.toString());
//                        }
//                    });
        }
    }

    /**
     * Check Network Connection
     *
     * @return boolean
     */
    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (cm != null) {
            networkInfo = cm.getActiveNetworkInfo();
        }
        return networkInfo != null && networkInfo.isConnected();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            // close this context and return to preview context (if there is any)
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Set toolbar data
     *
     * @param toolbar  toolbar instance
     * @param title    title to be displayed
     * @param subTitle subtitle to be displayed
     * @return action bar
     */
    public ActionBar setToolbar(Toolbar toolbar, String title, String subTitle) {

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setBackgroundDrawable(new ColorDrawable(CommonData.getColorConfig().getHippoActionBarBg()));
            toolbar.setTitleTextColor(CommonData.getColorConfig().getHippoActionBarText());
            toolbar.setSubtitleTextColor(CommonData.getColorConfig().getHippoActionBarText());
            ab.setHomeAsUpIndicator(R.drawable.hippo_ic_arrow_back);
            if (BumbleConfig.getInstance().getHomeUpIndicatorDrawableId() != -1)
                ab.setHomeAsUpIndicator(BumbleConfig.getInstance().getHomeUpIndicatorDrawableId());

            ab.setTitle(title);
            ab.setSubtitle(subTitle);

        }
        return getSupportActionBar();
    }

    /**
     * Hide softkeyboard of opened
     * @param activity
     */
    protected void hideKeyboard(Activity activity) {
        try {
            View view = activity.getCurrentFocus();
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

        /**
         * Set toolbar data
         *
         * @param toolbar toolbar instance
         * @param title   title to be displayed
         * @return action bar
         */
    public ActionBar setToolbar(Toolbar toolbar, String title) {

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setBackgroundDrawable(new ColorDrawable(CommonData.getColorConfig().getHippoActionBarBg()));
            ab.setHomeAsUpIndicator(R.drawable.hippo_ic_arrow_back);

            if (BumbleConfig.getInstance().getHomeUpIndicatorDrawableId() != -1)
                ab.setHomeAsUpIndicator(BumbleConfig.getInstance().getHomeUpIndicatorDrawableId());

            ab.setTitle("");

            toolbar.setTitleTextColor(CommonData.getColorConfig().getHippoActionBarText());

            ((TextView) toolbar.findViewById(R.id.tv_toolbar_name)).setText(title);
            ((TextView) toolbar.findViewById(R.id.tv_toolbar_name)).setTextColor(CommonData.getColorConfig().getHippoActionBarText());
        }
        return getSupportActionBar();
    }

    public ActionBar setToolbar(Toolbar toolbar, String title, boolean hideback) {

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setBackgroundDrawable(new ColorDrawable(CommonData.getColorConfig().getHippoActionBarBg()));
            ab.setHomeAsUpIndicator(R.drawable.hippo_ic_arrow_back);

            ab.setDisplayHomeAsUpEnabled(false);
            ab.setHomeButtonEnabled(false);

            if (BumbleConfig.getInstance().getHomeUpIndicatorDrawableId() != -1)
                ab.setHomeAsUpIndicator(BumbleConfig.getInstance().getHomeUpIndicatorDrawableId());

            ab.setTitle("");

            toolbar.setTitleTextColor(CommonData.getColorConfig().getHippoActionBarText());

            titleView = (TextView) toolbar.findViewById(R.id.tv_toolbar_name);
            titleView.setText(title);
            titleView.setTextColor(CommonData.getColorConfig().getHippoActionBarText());
        }
        return getSupportActionBar();
    }

    public ActionBar setToolbarNew(Toolbar toolbar, String title) {
        ActionBar actionBar = setToolbarNew(toolbar, title, true);
        return actionBar;
    }
    public ActionBar setToolbarNew(Toolbar toolbar, String title, boolean flag) {

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(flag);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ((TextView) toolbar.findViewById(R.id.tv_toolbar_name)).setText(title);

        return getSupportActionBar();
    }

    private TextView titleView;
    public void updateTitle(Toolbar toolbar, String title) {
        if(titleView == null)
            titleView = (TextView) toolbar.findViewById(R.id.tv_toolbar_name);

        titleView.setText(title);
    }

    public void showErrorMessage(final String errorMessage, final String positiveButtonText) {
        showErrorMessage(errorMessage, positiveButtonText, false);
    }
    public void showErrorMessage(final String errorMessage, final String positiveButtonText, final boolean isFinish) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(FuguBaseActivity.this)
                        .setMessage(errorMessage)
                        .setPositiveButton(positiveButtonText, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog, final int which) {
                                if(isFinish)
                                    finish();
                            }
                        })
                        .setCancelable(false)
                        .show();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(BumbleConfig.getInstance().getLifeCyclerListener() != null) {
            BumbleConfig.getInstance().getLifeCyclerListener().onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(BumbleConfig.getInstance().getLifeCyclerListener() != null) {
            BumbleConfig.getInstance().getLifeCyclerListener().onResume();
        }
    }

}
