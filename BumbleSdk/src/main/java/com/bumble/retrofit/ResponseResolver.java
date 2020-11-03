package com.bumble.retrofit;

import android.app.Activity;
import android.app.AlertDialog;
import android.text.TextUtils;
import android.widget.Toast;

import com.bumble.Utils.BumbleLog;
import com.bumble.Utils.loadingBox.LoadingBox;
import com.bumble.constants.BumbleAppConstants;
import com.google.gson.JsonSyntaxException;


import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Custom Retrofit ResponseResolver
 *
 * @param <T>
 */
public abstract class ResponseResolver<T> implements Callback<T> {
    private WeakReference<Activity> weakActivity = null;
    private Boolean showLoading = false;
    private Boolean showError = false;

    private final String NO_INTERNET_MESSAGE = "No internet connection. Please try again later.";
    private final String REMOTE_SERVER_FAILED_MESSAGE = "Application server could not respond. Please try later.";
    public final static String UNEXPECTED_ERROR_OCCURRED = "Something went wrong. Please try again later";
    private final static String PARSING_ERROR = "Parsing error";

    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog alertDialog;

    /**
     * @param activity
     */
    public ResponseResolver(Activity activity) {
        this.weakActivity = new WeakReference<>(activity);
        /*try {
            if(HippoConfig.getInstance().getOnApiCallback() != null) {
                HippoConfig.getInstance().getOnApiCallback().onProcessing();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }


    /**
     * @param activity
     * @param showLoading
     */
    public ResponseResolver(Activity activity, Boolean showLoading) {
        this.weakActivity = new WeakReference<>(activity);
        this.showLoading = showLoading;
        if (showLoading)
            LoadingBox.showOn(activity);
        /*try {
            if(HippoConfig.getInstance().getOnApiCallback() != null) {
                HippoConfig.getInstance().getOnApiCallback().onProcessing();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }


    /**
     * @param activity
     * @param showLoading
     * @param showError
     */
    public ResponseResolver(Activity activity, Boolean showLoading, Boolean showError) {
        this.weakActivity = new WeakReference<>(activity);
        this.showLoading = showLoading;
        this.showError = showError;
        if (showLoading)
            LoadingBox.showOn(activity);
        /*try {
            if(HippoConfig.getInstance().getOnApiCallback() != null) {
                HippoConfig.getInstance().getOnApiCallback().onProcessing();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/

    }

    public ResponseResolver() {
        /*try {
            if(HippoConfig.getInstance().getOnApiCallback() != null) {
                HippoConfig.getInstance().getOnApiCallback().onProcessing();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    public abstract void success(T t);

    public abstract void failure(APIError error);

    @Override
    public void onResponse(Call<T> t, Response<T> tResponse) {
        JSONObject jObjError = null;
        int type = 0;
        String message = "";
        try {
            if (tResponse.errorBody() != null) {
                jObjError = new JSONObject(tResponse.errorBody().string());
                message = jObjError.getString("message");
                type = Integer.parseInt(jObjError.getString("type"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        LoadingBox.hide();
        if (tResponse.isSuccessful()) {
            /*try {
                if(HippoConfig.getInstance().getOnApiCallback() != null) {
                    HippoConfig.getInstance().getOnApiCallback().onSucess();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }*/
            success(tResponse.body());
        } else {
            if(TextUtils.isEmpty(message)) {
                fireError(ErrorUtils.parseError(tResponse, type));
            } else {
                fireError(new APIError(900, message, 0));
            }
        }
    }

    @Override
    public void onFailure(Call<T> t, Throwable throwable) {
        LoadingBox.hide();
        fireError(new APIError(900, resolveNetworkError(throwable), 0));
    }


    /**
     * @param apiError
     */
    public void fireError(APIError apiError) {

        /*try {
            if(HippoConfig.getInstance().getOnApiCallback() != null) {
                HippoConfig.getInstance().getOnApiCallback().onFailure(apiError.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        if (showError) {
            if (weakActivity.get() != null) {
                if (checkAuthorizationError(apiError)) {
                    return;
                }
            }

        }

        failure(apiError);
    }

    /**
     * @param apiError
     * @return
     */
    public Boolean checkAuthorizationError(APIError apiError) {

        if (apiError.getStatusCode() == BumbleAppConstants.SESSION_EXPIRE) {
            /**
             * if authorization error than clear paper db and go back to splash screen on ok press
             * else show the error message
             */
            Toast.makeText(weakActivity.get(), apiError.getMessage(), Toast.LENGTH_SHORT).show();
            weakActivity.get().finish();

            return false;
        } else {
            if (showError) {

                Toast.makeText(weakActivity.get(), apiError.getMessage(), Toast.LENGTH_SHORT).show();
                //weakActivity.get().finish();
            }
        }

        return true;
    }

    /**
     * Method resolve network errors
     *
     * @param cause
     * @return
     */

    private String resolveNetworkError(Throwable cause) {
        BumbleLog.e("resolveNetworkError =", String.valueOf(cause.toString()));

        if (cause instanceof UnknownHostException)
            return NO_INTERNET_MESSAGE;
        else if (cause instanceof SocketTimeoutException)
            return NO_INTERNET_MESSAGE;
        else if (cause instanceof ConnectException)
            return NO_INTERNET_MESSAGE;
        else if (cause instanceof JsonSyntaxException)
            return PARSING_ERROR;
        return UNEXPECTED_ERROR_OCCURRED;
    }

}
