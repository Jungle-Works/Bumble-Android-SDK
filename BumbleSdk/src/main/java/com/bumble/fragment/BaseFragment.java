package com.bumble.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.bumble.HippoColorConfig;
import com.bumble.Utils.BumbleLog;
import com.bumble.database.CommonData;

import com.bumblesdk.R;

public abstract class BaseFragment extends Fragment {

    void  setTitle(String title){
        BumbleLog.d("Text", "text = "+title);

    };


    /**
     * Set toolbar text and color according to user requirement
     * @param toolbar
     * @param title
     * @return
     */
    public ActionBar setToolbar(Toolbar toolbar, String title) {

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ActionBar ab = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (ab != null) {
            //ab.setDisplayHomeAsUpEnabled(false);
            ab.setBackgroundDrawable(new ColorDrawable(CommonData.getColorConfig().getHippoActionBarBg()));

            ab.setHomeAsUpIndicator(R.drawable.hippo_ic_arrow_back);
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setHomeButtonEnabled(false);
            ab.setTitle("");

            toolbar.setTitleTextColor(CommonData.getColorConfig().getHippoActionBarText());

            ((TextView) toolbar.findViewById(R.id.tv_toolbar_name)).setText(title);
            ((TextView) toolbar.findViewById(R.id.tv_toolbar_name)).setTextColor(CommonData.getColorConfig().getHippoActionBarText());
        }
        return ((AppCompatActivity) getActivity()).getSupportActionBar();

    }

    /**
     * Set powered by text
     *
     * @param tvPoweredBy TextView
     */
//    protected void setPoweredByText(TextView tvPoweredBy) {
//        try {
//            FuguPutUserDetailsResponse.Data userData = CommonData.getUserDetails().getData();
//            if(userData == null)
//                return;
//
//            if (!userData.getWhiteLabel()) {
//                HippoColorConfig hippoColorConfig = CommonData.getColorConfig();
//
//                try {
//                    doStyleSpanForSecondString(Restring.getString(getActivity(), R.string.fugu_powered_by), getString(R.string.hippo_text), tvPoweredBy, hippoColorConfig);
//                } catch (Exception e) {
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//
//                        String text = "<font color="
//                                + String.format("#%06X",
//                                (0xFFFFFF & hippoColorConfig.getHippoTextColorPrimary())) + ">"
//                                + getString(R.string.fugu_powered_by)
//                                + "<font color=" + String.format("#%06X",
//                                (0xFFFFFF & hippoColorConfig.getHippoThemeColorPrimary())) + "> "
//                                + getString(R.string.hippo_text) + "</font>";
//                        //noinspection deprecation
//                        tvPoweredBy.setText(Html.fromHtml(text));
//                    } else {
//                        String text = "<font color="
//                                + String.format("#%06X",
//                                (0xFFFFFF & hippoColorConfig.getHippoTextColorPrimary())) + ">"
//                                + getString(R.string.fugu_powered_by)
//                                + "<font color=" + String.format("#%06X",
//                                (0xFFFFFF & hippoColorConfig.getHippoThemeColorPrimary())) + "> "
//                                + getString(R.string.hippo_text) + "</font>";
//                        tvPoweredBy.setText(Html.fromHtml(text));
//                    }
//                }
//
//                tvPoweredBy.setVisibility(View.VISIBLE);
//                tvPoweredBy.setBackgroundDrawable(HippoColorConfig.makeSelector(hippoColorConfig.getHippoChannelItemBg(),
//                        hippoColorConfig.getHippoChannelItemBgPressed()));
//            } else {
//                tvPoweredBy.setVisibility(View.GONE);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            tvPoweredBy.setVisibility(View.GONE);
//        }
//    }

    /**
     * Make string with spannable string
     * @param firstString
     * @param lastString
     * @param txtSpan
     * @param hippoColorConfig
     */
    private void doStyleSpanForSecondString(String firstString, String lastString, TextView txtSpan,
                                            HippoColorConfig hippoColorConfig) throws Exception {
        String changeString = (lastString != null ? lastString : "");
        String totalString = firstString +" "+ changeString;
        Spannable spanText = new SpannableString(totalString);
        spanText.setSpan(new StyleSpan(Typeface.BOLD), String.valueOf(firstString).length(), totalString.length(), 0);
        spanText.setSpan(new ForegroundColorSpan(hippoColorConfig.getHippoActionBarBg()), String.valueOf(firstString).length(), totalString.length(), 0);
        spanText.setSpan(new RelativeSizeSpan(0.8f), 0, String.valueOf(firstString).length(), 0);

        txtSpan.setText(spanText);
    }

    /**
     * Used to hide keyboard if outside touched.
     *
     * @param view
     */
    public void setupUI(View view) throws Exception {
        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    try {
                        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                    } catch (Exception e) {
                        //        e.printStackTrace();
                    }
                    return false;
                }

            });
        }
        // If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }

    /**
     * Check Network Connection
     *
     * @return boolean
     */
    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (cm != null) {
            networkInfo = cm.getActiveNetworkInfo();
        }
        return networkInfo != null && networkInfo.isConnected();
    }

    /*public void showDialog(final Activity context) {
        new AlertDialog.Builder(context)
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        Prefs.with(context).remove("access_token");
                        Prefs.with(context).remove("user_unique_key");
                        HippoConfig.clearHippoData(context);
                        context.finish();
                        LibApp.getInstance().openMainScreen();
                    }
                })
                .setNegativeButton("N0", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setCancelable(false)
                .show();
    }*/

}