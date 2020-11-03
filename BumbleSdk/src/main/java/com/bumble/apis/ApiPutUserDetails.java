package com.bumble.apis;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.text.TextUtils;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.bumble.Utils.Prefs;
import com.bumble.Utils.UniqueIMEIID;
import com.bumble.Utils.getIPAddress;
import com.bumble.constants.BumbleAppConstants;
import com.bumble.BumbleConfig;
import com.bumble.BumbleConfigAttributes;
import com.bumble.CaptureUserDataBumble;
import com.bumble.Utils.BumbleLog;
import com.bumble.Utils.ToastUtil;
import com.bumble.database.CommonData;
import com.bumble.model.FuguDeviceDetails;
import com.bumble.model.FuguPutUserDetailsResponse;
import com.bumble.model.UserInfoModel;
import com.bumble.retrofit.APIError;
import com.bumble.retrofit.ApiInterface;
import com.bumble.retrofit.CommonParams;
import com.bumble.retrofit.IpResponse;
import com.bumble.retrofit.ResponseResolver;
import com.bumble.retrofit.RestClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.HashSet;

import io.paperdb.BuildConfig;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiPutUserDetails implements BumbleAppConstants {

    public Activity activity;
    private Callback callback;
    private CaptureUserDataBumble userData;
    private boolean fetchAllList = false;

    public ApiPutUserDetails(Activity activity, Callback callback) {
        this.activity = activity;
        this.callback = callback;
        fetchAllList = false;
    }

    public void sendUserDetails(int referenceId) {
        sendUserDetails(referenceId, BumbleConfig.progressLoader);
    }

    public void sendUserDetails(int referenceId, boolean showLoading, boolean fetchAllList) {
        this.fetchAllList = fetchAllList;
        sendUserDetails(referenceId, showLoading);
    }

    public void sendUserDetails(int referenceId, final boolean showLoading) {
        BumbleLog.v("inside sendUserDetails", "inside sendUserDetails");
        Gson gson = new GsonBuilder().create();
        JsonObject deviceDetailsJson = null;
        try {
            deviceDetailsJson = gson.toJsonTree(new FuguDeviceDetails(getAppVersion()).getDeviceDetails()).getAsJsonObject();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        BumbleConfigAttributes attributes = CommonData.getAttributes();

        final HashMap<String, Object> commonParamsMAp = new HashMap<>();

        if (TextUtils.isEmpty(BumbleConfig.getInstance().getAppKey()) && !TextUtils.isEmpty(attributes.getAppKey()))
            BumbleConfig.getInstance().appKey = attributes.getAppKey();
        if (TextUtils.isEmpty(BumbleConfig.getInstance().getAppKey())) {
            if (BumbleConfig.getInstance().getOnApiCallback() != null) {
                BumbleConfig.getInstance().getOnApiCallback().onFailure("App secret key can't be empty");
            }
            try {
                if (BuildConfig.DEBUG) {
                    ToastUtil.getInstance(activity).showToast("App secret key can't be empty");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }
        commonParamsMAp.put(APP_SECRET_KEY, BumbleConfig.getInstance().getAppKey());

        commonParamsMAp.put(DEVICE_ID, UniqueIMEIID.getUniqueIMEIId(activity));
        commonParamsMAp.put(APP_TYPE, BumbleConfig.getInstance().getAppType());
        commonParamsMAp.put(DEVICE_TYPE, ANDROID_USER);
        commonParamsMAp.put(APP_VERSION, BuildConfig.VERSION_NAME);
        commonParamsMAp.put(APP_VERSION_CODE, BuildConfig.VERSION_CODE);
        commonParamsMAp.put(DEVICE_DETAILS, deviceDetailsJson);

        userData = BumbleConfig.getInstance().getUserData(false);

        CommonData.saveRequiredLang("");
        if (userData != null) {
            if (!TextUtils.isEmpty(userData.getUserUniqueKey()))
                commonParamsMAp.put(USER_UNIQUE_KEY, userData.getUserUniqueKey());

            if (!TextUtils.isEmpty(userData.getFullName()))
                commonParamsMAp.put(FULL_NAME, userData.getFullName());

            if (!TextUtils.isEmpty(userData.getEmail()))
                commonParamsMAp.put(EMAIL, userData.getEmail());

            if (!TextUtils.isEmpty(userData.getPhoneNumber())) {
                final String contact = userData.getPhoneNumber();//.replaceAll("[^\\d.]", "");
                /*if (!Utils.isValidPhoneNumber(contact)) {
                    ToastUtil.getInstance(activity).showToast("Invalid phone number");
                    return;
                }*/
                //commonParamsMAp.put(PHONE_NUMBER, contact);
                if (!BumbleConfig.getInstance().isSetSkipNumber()) {
//                        || (CommonData.getUserDetails() != null && CommonData.getUserDetails().getData() != null
//                && CommonData.getUpdatedDetails().getData().getCustomerInitialFormInfo() == null)) {
                    commonParamsMAp.put(PHONE_NUMBER, contact);
                } else {
                    Prefs.with(activity).save("PHONE_NUMBER", contact);
                }
            }

            try {
                commonParamsMAp.put(CUSTOM_ATTRIBUTES, new JSONObject(userData.getCustom_attributes()));
            }catch (Exception e){
                if (BumbleConfig.DEBUG)
                    e.printStackTrace();
            }


            if (!TextUtils.isEmpty(CommonData.getImagePath()))
                commonParamsMAp.put(HIPPO_USER_IMAGE_PATH, CommonData.getImagePath());


            if (userData.isFetchBusinessLang()) {
                commonParamsMAp.put("fetch_business_lang", 1);
            }

            if (!TextUtils.isEmpty(userData.getLang()))
                CommonData.saveRequiredLang(userData.getLang());
        }

        String deviceToken = null;
        try {
            deviceToken = CommonData.getDeviceToken();
        } catch (Exception e) {
            if (BumbleConfig.DEBUG)
                e.printStackTrace();
        }
        if (!TextUtils.isEmpty(deviceToken)) {
            commonParamsMAp.put(DEVICE_TOKEN, deviceToken);
        }


        BumbleLog.e("Fugu Config sendUserDetails maps", "==" + commonParamsMAp.toString());
        CommonData.savePutUserParams(commonParamsMAp);

        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);


        if (ContextCompat.checkSelfPermission(
                activity, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(activity, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                try {
                                    JSONObject attr = new JSONObject();

                                    attr.put("lat_long", location.getLatitude() + "," + location.getLongitude());
                                    commonParamsMAp.put(ATTRIBUTES, attr);


                                    getIpAddress(commonParamsMAp, showLoading);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                    });
        }  else {
            // You can directly ask for the permission.
            getIpAddress(commonParamsMAp, showLoading);

        }



    }


    private void getIpAddress(final HashMap<String, Object> commonParamsMAp, final boolean showLoading){

        Call<IpResponse> call = RestClient.getApiIpInterface().showTheStations("https://api.ipify.org/?format=json");
        call.enqueue(new  ResponseResolver<IpResponse>() {
            @Override
            public void success(IpResponse ipResponse) {
                Log.e("ipresponse",ipResponse.getIp());

                try {
                    JSONObject attr = new JSONObject();

                    attr.put(IP_ADDRESS, getIPAddress.getIPAddress(true));
                    commonParamsMAp.put(ATTRIBUTES, attr);
                    apiPutUserDetail(commonParamsMAp, showLoading);



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void failure(APIError error) {

            }


        });
    }

    public void updateUserData(HashMap<String, Object> commonParamsMap) {

        apiPutUserDetail(commonParamsMap, BumbleConfig.progressLoader, true);
    }

    private void apiPutUserDetail(HashMap<String, Object> commonParams, boolean showLoading) {
        apiPutUserDetail(commonParams, showLoading, false);
    }

    private void apiPutUserDetail(HashMap<String, Object> commonParams, boolean showLoading, boolean showError) {
        try {
            if (BumbleConfig.getInstance().getOnApiCallback() != null) {
                BumbleConfig.getInstance().getOnApiCallback().onProcessing();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String requiredLang = CommonData.getRequiredLanguage();
        if (TextUtils.isEmpty(requiredLang))
            requiredLang = CommonData.getCurrentLanguage();
        if (TextUtils.isEmpty(requiredLang))
            requiredLang = "en";

        commonParams.put(SOURCE_KEY, 7);

        commonParams.remove(APP_VERSION_CODE);
        commonParams.remove(APP_VERSION);
        commonParams.remove(APP_TYPE);
        commonParams.remove(USER_IMAGE);
        commonParams.remove("fetch_business_lang");
        commonParams.remove(DEVICE_DETAILS);
        commonParams.remove(APP_SOURCE_TYPE);


        CommonParams params = new CommonParams.Builder()
                .putMap(commonParams)
                .build(requiredLang);


        RestClient.getApiInterface().putUserDetails(params.getMap())
                .enqueue(new ResponseResolver<FuguPutUserDetailsResponse>(activity, showLoading, showError) {
                    @Override
                    public void success(FuguPutUserDetailsResponse fuguPutUserDetailsResponse) {
                        try {
                            if (fuguPutUserDetailsResponse.getData().getFullName().equalsIgnoreCase("Visitor")) {
                                if (!TextUtils.isEmpty(Prefs.with(activity).getString("form_full_name", ""))) {
                                    fuguPutUserDetailsResponse.getData().setFullName(Prefs.with(activity).getString("form_full_name", ""));
                                    Prefs.with(activity).save("form_full_name", "");
                                }
                            }
                        } catch (Exception e) {

                        }

                        CommonData.setUserDetails(fuguPutUserDetailsResponse);





                        /*if(TextUtils.isEmpty(CommonData.getCurrentLanguage()) && fuguPutUserDetailsResponse.getData().getBusinessLanguages() != null) {
                            for(BusinessLanguages languages : fuguPutUserDetailsResponse.getData().getBusinessLanguages()) {
                                if(languages.isDefaultLnag()) {
                                    HippoConfig.getInstance().updateLanguage(languages.getLangCode());
                                    CommonData.saveCurrentLang(languages.getLangCode());
                                    break;
                                }
                            }

                        }*/

                        BumbleConfig.getInstance().getUserData().setUserId(fuguPutUserDetailsResponse.getData().getUserId());
                        BumbleConfig.getInstance().getUserData().setEnUserId(fuguPutUserDetailsResponse.getData().getEn_user_id());
                        CommonData.saveUserData(BumbleConfig.getInstance().getUserData());
                        BumbleLog.e("en_user_id", fuguPutUserDetailsResponse.getData().getEn_user_id());

                        if (activity != null) {
                            Prefs.with(activity).save("en_user_id", fuguPutUserDetailsResponse.getData().getEn_user_id());
                            Prefs.with(activity).save("user_id", fuguPutUserDetailsResponse.getData().getUserId());
                            Prefs.with(activity).save("full_name", fuguPutUserDetailsResponse.getData().getFullName());
                            Prefs.with(activity).save("email", fuguPutUserDetailsResponse.getData().getEmail());
                        }

                        try {
                            if (BumbleConfig.getInstance().getOnApiCallback() != null) {
                                BumbleConfig.getInstance().getOnApiCallback().onSucess();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        callback.onSuccess();


//                        try {
//                            if(fuguPutUserDetailsResponse.getData().isAskPaymentAllowed())
//                                fetchAllGateways();
//                        } catch (Exception e) {
//
//                        }

                        try {
                            if (fuguPutUserDetailsResponse.getData().getUnreadChannels() != null) {


                                CommonData.setAnnouncementCount(fuguPutUserDetailsResponse.getData().getUnreadChannels());
                            } else {
                                CommonData.setAnnouncementCount(new HashSet<String>());
                            }

                        } catch (Exception e) {

                        }
                    }

                    @Override
                    public void failure(APIError error) {
                        try {
                            if (BuildConfig.DEBUG) {
                                ToastUtil.getInstance(activity).showToast(error.getMessage());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            if (BumbleConfig.getInstance().getOnApiCallback() != null) {
                                BumbleConfig.getInstance().getOnApiCallback().onFailure(error.getMessage());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        callback.onFailure();
                    }
                });
    }

    private void apiPutUserDetailReseller(HashMap<String, Object> commonParams) {
        apiPutUserDetailReseller(commonParams, false, false);
    }

    private void apiPutUserDetailReseller(HashMap<String, Object> commonParams, boolean showLoading, boolean showError) {
        try {
            if (BumbleConfig.getInstance().getOnApiCallback() != null) {
                BumbleConfig.getInstance().getOnApiCallback().onProcessing();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String requiredLang = CommonData.getRequiredLanguage();
        if (TextUtils.isEmpty(requiredLang))
            requiredLang = CommonData.getCurrentLanguage();
        if (TextUtils.isEmpty(requiredLang))
            requiredLang = "en";
        CommonParams params = new CommonParams.Builder()
                .putMap(commonParams)
                .build(requiredLang);
        RestClient.getApiInterface().putUserDetailsReseller(params.getMap())
                .enqueue(new ResponseResolver<FuguPutUserDetailsResponse>(activity, false, false) {
                    @Override
                    public void success(FuguPutUserDetailsResponse fuguPutUserDetailsResponse) {
                        CommonData.setUserDetails(fuguPutUserDetailsResponse);

                        BumbleConfig.getInstance().getUserData().setUserId(fuguPutUserDetailsResponse.getData().getUserId());
                        BumbleConfig.getInstance().getUserData().setEnUserId(fuguPutUserDetailsResponse.getData().getEn_user_id());
                        CommonData.saveUserData(BumbleConfig.getInstance().getUserData());
                        BumbleLog.e("en_user_id", fuguPutUserDetailsResponse.getData().getEn_user_id());
                        if (fuguPutUserDetailsResponse.getData().getAppSecretKey() != null &&
                                !TextUtils.isEmpty(fuguPutUserDetailsResponse.getData().getAppSecretKey())) {
                            BumbleConfig.getInstance().appKey = fuguPutUserDetailsResponse.getData().getAppSecretKey();
                            CommonData.setAppSecretKey(fuguPutUserDetailsResponse.getData().getAppSecretKey());
                        }

                        if (activity != null) {
                            Prefs.with(activity).save("en_user_id", fuguPutUserDetailsResponse.getData().getEn_user_id());
                            Prefs.with(activity).save("user_id", fuguPutUserDetailsResponse.getData().getUserId());
                            Prefs.with(activity).save("full_name", fuguPutUserDetailsResponse.getData().getFullName());
                            Prefs.with(activity).save("email", fuguPutUserDetailsResponse.getData().getEmail());
                        }


                        try {
                            if (BumbleConfig.getInstance().getOnApiCallback() != null) {
                                BumbleConfig.getInstance().getOnApiCallback().onSucess();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        callback.onSuccess();


                    }

                    @Override
                    public void failure(APIError error) {
                        try {
                            if (BuildConfig.DEBUG) {
                                ToastUtil.getInstance(activity).showToast(error.getMessage());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            if (BumbleConfig.getInstance().getOnApiCallback() != null) {
                                BumbleConfig.getInstance().getOnApiCallback().onFailure(error.getMessage());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        callback.onFailure();
                    }
                });
    }

    private int getAppVersion() {
        try {
            return activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void getUserContryInfo(final BumbleConfigAttributes attributes, final UserCallback callback) {
        try {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://ip.tookanapp.com:8000")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            ApiInterface gerritAPI = retrofit.create(ApiInterface.class);

            Call<UserInfoModel> call = gerritAPI.getUserInfo();
            call.enqueue(new ResponseResolver<UserInfoModel>() {
                @Override
                public void success(UserInfoModel userInfoModel) {
                    try {
                        CommonData.setUserContCode(userInfoModel.getData().getContinentCode());
                    } catch (Exception e) {

                    }
                    if (callback != null) {
                        callback.onSuccess(userInfoModel, attributes);
                    }
                }

                @Override
                public void failure(APIError error) {
                    if (callback != null) {
                        callback.onSuccess(null, attributes);
                    }
                }
            });
        } catch (Exception e) {
            if (callback != null) {
                callback.onSuccess(null, attributes);
            }
        }
    }


    public interface Callback {
        void onSuccess();

        void onFailure();
    }

    public interface UserCallback {
        void onSuccess(UserInfoModel userInfoModel, BumbleConfigAttributes attributes);
    }


}
