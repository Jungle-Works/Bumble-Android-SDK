package com.bumble;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;

import com.bumble.Utils.CustomAlertDialog;
import com.bumble.Utils.BumbleLog;
import com.bumble.Utils.Prefs;
import com.bumble.Utils.customROM.XiaomiUtilities;
import com.bumble.activity.CampaignActivity;
import com.bumble.apis.ApiPutUserDetails;
import com.bumble.constants.BumbleAppConstants;
import com.bumble.database.CommonData;
import com.bumble.model.FuguPutUserDetailsResponse;
import com.bumble.retrofit.APIError;
import com.bumble.retrofit.CommonParams;
import com.bumble.retrofit.CommonResponse;
import com.bumble.retrofit.ResponseResolver;
import com.bumble.retrofit.RestClient;
import com.bumblesdk.R;

import java.util.ArrayList;
import java.util.HashMap;

import io.paperdb.Paper;

public class BumbleConfig implements BumbleAppConstants {

    private static final String TAG = BumbleConfig.class.getSimpleName();
    private static BumbleConfig bumbleConfig;
    public static volatile Handler applicationHandler;
    private CaptureUserDataBumble userData;
    private static BumbleConfigAttributes configAttributes;

    private String serverUrl = "";

    public int getHomeUpIndicatorDrawableId() {
        return homeUpIndicatorDrawableId;
    }

    public void setHomeUpIndicatorDrawableId(int homeUpIndicatorDrawableId) {
        this.homeUpIndicatorDrawableId = homeUpIndicatorDrawableId;
    }

    public int getChatScreenBg() {
        return chatScreenBg;
    }

    /*
     * used to set bg for chat screen
     * */
    public void setChatScreenBg(int chatScreenBg) {
        this.chatScreenBg = chatScreenBg;
    }

    //Drawable
    private int homeUpIndicatorDrawableId = -1;//R.drawable.hippo_ic_arrow_back;
    private int videoCallNotificationDrawable = R.drawable.hippo_default_notif_icon;
    private int videoCallDrawableId = -1;//R.drawable.hippo_ic_info;
    private int audioCallDrawableId = -1;
    private int chatInfoDrawable = -1;
    private int homeIconDrawable = -1;
    private int broadcastDrawable = -1;
    private int icSend = -1;
    private int chatScreenBg = -1;

    public int getIcSend() {
        return icSend;
    }

    public void setIcSend(int icSend) {
        this.icSend = icSend;
    }

    public String appKey = "";
    private String appType = "1";
    private BumbleConfigAttributes attributes;

    private static String mResellerToken;
    private static int mReferenceId = -1;

    protected Context context;
    private Activity activity;
    private long lastClickTime = 0;

    private boolean isDataCleared = true;
    public static boolean DEBUG = false;
    private boolean isChannelActivity;
    private boolean isAnnouncement;
    private static boolean isUnreadRequired;

    public static boolean progressLoader = true;
    private boolean setSkipNumber;
    private UnreadCountFor countCallback;


    private BumbleConfig() {

    }

    public static long getMaxSize() {
        try {
            Long maxFileSize = 26214400l;
            maxFileSize = CommonData.getUserDetails().getData().getMaxFileSize();
            return maxFileSize;
        } catch (Exception e) {
            if (BumbleConfig.DEBUG)
                e.printStackTrace();
        }
        return 26214400;
    }

    public Context getContext() {
        return context;
    }

    public static BumbleConfig getInstance() {
        if (bumbleConfig == null) {
            bumbleConfig = new BumbleConfig();
        }
        return bumbleConfig;
    }

    private UnreadCount callbackListener;

    public UnreadCount getCallbackListener() {
        return callbackListener;
    }

    public void setCallbackListener(UnreadCount callbackListener) {
        this.callbackListener = callbackListener;
    }

    private BumbleConfigAttributes getAttributes() {
        if (attributes == null) {
            attributes = CommonData.getAttributes();
        }
        return attributes;
    }

    private BumbleInitCallback initCallback;
//    private HippoAdditionalListener hippoAdditionalListener;

//    public HippoAdditionalListener getHippoAdditionalListener() {
//        return hippoAdditionalListener;
//    }
//
//    public void setHippoAdditionalListener(HippoAdditionalListener hippoAdditionalListener) {
//        this.hippoAdditionalListener = hippoAdditionalListener;
//    }

    public BumbleInitCallback getInitCallback() {
        return initCallback;
    }

    private void setInitCallback(BumbleInitCallback initCallback) {
        this.initCallback = initCallback;
    }

    public boolean isSetSkipNumber() {
        return setSkipNumber;
    }

    public void setSetSkipNumber(boolean setSkipNumber) {
        this.setSkipNumber = setSkipNumber;
    }

    public static BumbleConfig initBumbleConfig(Activity activity, BumbleConfigAttributes attributes) {
        return initBumbleConfig(activity, attributes, null);
    }

    public static BumbleConfig initBumbleConfig(Activity activity, BumbleConfigAttributes attributes, BumbleInitCallback callback) {
        count = 0;
        bumbleConfig = getInstance();
        bumbleConfig.setInitCallback(callback);
        Paper.init(activity);

        applicationHandler = new Handler(activity.getMainLooper());

        DEBUG = attributes.isShowLog();
        BumbleConfig.getInstance().activity = activity;

        if (TextUtils.isEmpty(attributes.getProvider())) {
            new CustomAlertDialog.Builder(activity)
                    .setMessage("Provider cannot be null")
                    .setPositiveButton("Ok", new CustomAlertDialog.CustomDialogInterface.OnClickListener() {
                        @Override
                        public void onClick() {
                            BumbleConfig.getInstance().activity.finish();
                        }
                    })
                    .show();
        } else {
            CommonData.setProvider(attributes.getProvider());
        }
        if (attributes.getColorConfig() != null) {
            CommonData.setColorConfig(attributes.getColorConfig());
        }

//        if (CommonData.getUpdatedDetails() != null && CommonData.getUpdatedDetails().getData() != null) {
//            if (callback != null) {
//                ConnectionManager.INSTANCE.initFayeConnection();
//                //ConnectionManager.INSTANCE.subScribeChannel("/"+CommonData.getUpdatedDetails().getData().getUserChannel());
//
//                callback.hasData();
//                progressLoader = false;
//                boolean fetchPayment = true;
//                if (attributes != null && attributes.getAdditionalInfo() != null && attributes.getAdditionalInfo().isFetchPaymentMethod()) {
//                    fetchPayment = false;
//                }
//                if (fetchPayment)
//                    GetPaymentGateway.INSTANCE.getPaymentGatewaysList(null);
//            }
//        }

        if (!TextUtils.isEmpty(attributes.getImagePath()))
            CommonData.setUserImagePath(attributes.getImagePath());

        try {
            CommonData.saveDeviceToken(attributes.getDeviceToken());
        } catch (Exception e) {
            if (BumbleConfig.DEBUG)
                e.printStackTrace();
        }
        CommonData.saveFuguConfigAttribute(attributes);
        BumbleLog.v("inside initBumbleConfig", "inside initBumbleConfig");
        bumbleConfig.setFuguConfig(activity, attributes);


//        try {
//            AndroidLoggingHandler.reset(new AndroidLoggingHandler());
////            java.util.logging.Logger.getLogger(Socket.class.getName()).setLevel(Level.FINE);
////            java.util.logging.Logger.getLogger(io.socket.parser.IOParser.class.getName()).setLevel(Level.FINE);
////            java.util.logging.Logger.getLogger(io.socket.engineio.client.Socket.class.getName()).setLevel(Level.FINE);
////            java.util.logging.Logger.getLogger(Manager.class.getName()).setLevel(Level.FINE);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


        return bumbleConfig;
    }

//    private void initDownloader() {
//        PRDownloaderConfig config = PRDownloaderConfig.newBuilder()
//                .setDatabaseEnabled(true)
//                .setReadTimeout(30_000)
//                .setConnectTimeout(30_000)
//                .build();
//        PRDownloader.initialize(activity, config);
//    }

    private void setFuguConfig(final Activity activity, BumbleConfigAttributes attributes) {
        String environment = TextUtils.isEmpty(attributes.getEnvironment()) ? "live" : attributes.getEnvironment();

        if (environment != null && environment.equalsIgnoreCase("live")) {
            BumbleConfig.getInstance().serverUrl = LIVE_SERVER; // live server
            CommonData.setServerUrl(LIVE_SERVER);
            CommonData.setSocketServerUrl(LIVE_SOCKEY_SERVER);
        } else if (environment != null && environment.equalsIgnoreCase("test")) {
            BumbleConfig.getInstance().serverUrl = TEST_SERVER;
            CommonData.setServerUrl(TEST_SERVER);
            CommonData.setSocketServerUrl(TEST_SERVER);
        } else if (environment != null && environment.equalsIgnoreCase("dev")) {
            BumbleConfig.getInstance().serverUrl = DEV_SERVER;
            CommonData.setServerUrl(DEV_SERVER);
            CommonData.setSocketServerUrl(DEV_SERVER);
        } else if (environment != null && environment.equalsIgnoreCase("dev3004")) {
            BumbleConfig.getInstance().serverUrl = DEV_SERVER_3004;
            CommonData.setServerUrl(DEV_SERVER_3004);
            CommonData.setSocketServerUrl(DEV_SERVER_3004);
        } else if (environment != null && environment.equalsIgnoreCase("dev3003")) {
            BumbleConfig.getInstance().serverUrl = DEV_SERVER_3003;
            CommonData.setServerUrl(DEV_SERVER_3003);
            CommonData.setSocketServerUrl(DEV_SERVER_3003);
        } else if (environment != null && environment.equalsIgnoreCase("beta-live")) {
            BumbleConfig.getInstance().serverUrl = BETA_LIVE_SERVER; //test server
            CommonData.setServerUrl(BETA_LIVE_SERVER);
            CommonData.setSocketServerUrl(BETA_LIVE_SOCKEY_SERVER);
        } else {
            BumbleConfig.getInstance().serverUrl = LIVE_SERVER; // live server
            CommonData.setServerUrl(LIVE_SERVER);
            CommonData.setSocketServerUrl(LIVE_SOCKEY_SERVER);
        }
        this.attributes = attributes;
//        registerNetworkListener(activity);

//        if (!TextUtils.isEmpty(CommonData.getUserCountryCode())) {
        initHippoCustomer(activity, attributes);
//        } else {
//            new ApiPutUserDetails(activity, null).getUserContryInfo(attributes, new ApiPutUserDetails.UserCallback() {
//                @Override
//                public void onSuccess(UserInfoModel userInfoModel, BumbleConfigAttributes attributes) {
//                    initHippoCustomer(activity, attributes);
//                }
//            });
//        }

    }

    private void initHippoCustomer(Activity activity, BumbleConfigAttributes attributes) {
        BumbleConfig.getInstance().appKey = attributes.getAppKey();
        BumbleConfig.getInstance().appType = attributes.getAppType();
        if (BumbleConfig.getInstance().appKey != null)
            CommonData.setAppSecretKey(BumbleConfig.getInstance().appKey);
        CommonData.setAppType(BumbleConfig.getInstance().appType);
        CommonData.clearLeftTimeInSec();
        updateUserDetails(activity, attributes);
    }


    private void updateUserDetails(Activity activity, final BumbleConfigAttributes attributes) {
        BumbleConfig.getInstance().isDataCleared = false;
        CommonData.setIsDataCleared(false);
        BumbleConfig.getInstance().activity = activity;
        BumbleConfig.getInstance().context = activity;
        BumbleConfig.getInstance().appType = attributes.getAppType();
        BumbleConfig.getInstance().userData = attributes.getCaptureUserData() == null ? new CaptureUserDataBumble()
                : attributes.getCaptureUserData();

        CommonData.saveUserData(BumbleConfig.getInstance().userData);

        new ApiPutUserDetails(activity, new ApiPutUserDetails.Callback() {
            @Override
            public void onSuccess() {
                if (getInitCallback() != null) {
                    getInitCallback().onPutUserResponse();
                }

                try {
                    if (BumbleConfig.getInstance().getAttributes().getAdditionalInfo() != null &&
                            BumbleConfig.getInstance().getAttributes().getAdditionalInfo().needDeviceOptimization()) {
                        XiaomiUtilities.checkForDevicePermission(BumbleConfig.getInstance().activity);
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure() {
                if (getInitCallback() != null) {
                    getInitCallback().onErrorResponse();
                }
                try {
                    if (BumbleConfig.getInstance().getAttributes().getAdditionalInfo() != null &&
                            BumbleConfig.getInstance().getAttributes().getAdditionalInfo().needDeviceOptimization()) {
                        XiaomiUtilities.checkForDevicePermission(BumbleConfig.getInstance().activity);
                    }
                } catch (Exception e) {

                }
                //XiaomiUtilities.checkForDevicePermission(BumbleConfig.getInstance().activity);
            }
        }).sendUserDetails(attributes.getReferenceId());
    }

    public static void clearHippoData(Activity activity) {
        try {
            logOutUser(activity);
        } catch (Exception e) {

        }
        try {
            bumbleConfig.clearLocalData();
            Prefs.with(activity).removeAll();
        } catch (Exception e) {

        }
    }

    private void clearLocalData() {
        BumbleConfig.getInstance().isDataCleared = true;
        CommonData.setIsDataCleared(true);
        try {
            CommonData.clearData();
            userData = null;
        } catch (Exception e) {

        }
    }

    public String getAppKey() {
        if (TextUtils.isEmpty(appKey))
            appKey = CommonData.getAppSecretKey();
        return appKey;
    }

    public static String getmResellerToken() {
        if (TextUtils.isEmpty(mResellerToken))
            mResellerToken = CommonData.getResellerToken();
        return mResellerToken;
    }

    public static int getmReferenceId() {
        if (mReferenceId == -1)
            mReferenceId = CommonData.getReferenceId();
        return mReferenceId;
    }

    public String getAppType() {
        return BumbleConfig.getInstance().appType;
    }

    public boolean isDataCleared() {
        return isDataCleared;
    }

    public boolean isChannelActivity() {
        return isChannelActivity;
    }

    public void setChannelActivity(boolean channelActivity) {
        isChannelActivity = channelActivity;
    }

    public boolean isAnnouncements() {
        return isAnnouncement;
    }

    public void setAnnouncementActivity(boolean isAnnouncement) {
        this.isAnnouncement = isAnnouncement;
    }

    public void setRideTime(long timeInSecs) {
        if (timeInSecs > 0) {
            CommonData.setLeftTimeInSec(timeInSecs);
        } else {
            CommonData.clearLeftTimeInSec();
        }
    }

//    public void stopOnlineStatus(Activity activity) {
//        CommonData.clearLeftTimeInSec();
//        new ApiPutUserDetails(activity, new ApiPutUserDetails.Callback() {
//            @Override
//            public void onSuccess() {
//
//            }
//
//            @Override
//            public void onFailure() {
//
//            }
//        }).stopRideStatus();
//    }

    public void discartEstimatedTime() {
        CommonData.clearLeftTimeInSec();
    }

    public void setTitle(String title) {
        CommonData.setChatTitle(title);
    }


//    private void openSupportScreen(final String categoryId, final String transactionId) {
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                openFuguSupportActivity(categoryId, transactionId);
//            }
//        }, 100);
//    }


    private static void logOutUser(Activity activity) throws Exception {
        if (BumbleConfig.getInstance().getUserData() != null && BumbleConfig.getInstance().getUserData().getEnUserId() != null) {
            CommonParams commonParams = new CommonParams.Builder()
                    .add(APP_SECRET_KEY, BumbleConfig.getInstance().getAppKey())
                    .add(EN_USER_ID, BumbleConfig.getInstance().getUserData().getEnUserId())
                    .add(USER_ID, BumbleConfig.getInstance().getUserData().getUserId())
//                    .add(APP_VERSION, BuildConfig.VERSION_NAME)
//                    .add("device_id", UniqueIMEIID.getUniqueIMEIId(activity))
//                    .add(DEVICE_TYPE, 1)
                    .build();
            RestClient.getApiInterface().logOut(commonParams.getMap())
                    .enqueue(new ResponseResolver<CommonResponse>(activity, false, false) {
                        @Override
                        public void success(CommonResponse commonResponse) {

                        }

                        @Override
                        public void failure(APIError error) {

                        }
                    });
        }
    }

//    private void openUserInitForm() {
//        Intent intent = new Intent(activity.getApplicationContext(), CustomerInitalActivity.class);
//        activity.startActivity(intent);
//    }

//    private void caseOne(String title) {
//        HippoLog.e("Case 1", "case 1");
//        if (CommonData.getUpdatedDetails().getData().getCustomerInitialFormInfo() != null) {
//            openUserInitForm();
//        } else {
//            Intent chatIntent = new Intent(activity.getApplicationContext(), FuguChatActivity.class);
//            FuguConversation conversation = new FuguConversation();
//            conversation.setBusinessName(title);
//            conversation.setOpenChat(true);
//            conversation.setUserName(StringUtil.toCamelCase(BumbleConfig.getInstance().getUserData().getFullName()));
//            conversation.setUserId(BumbleConfig.getInstance().getUserData().getUserId());
//            conversation.setEnUserId(BumbleConfig.getInstance().getUserData().getEnUserId());
//            chatIntent.putExtra(FuguAppConstant.CONVERSATION, new Gson().toJson(conversation, FuguConversation.class));
//            activity.startActivity(chatIntent);
//        }
//    }

//    private void caseElse(String title) {
//        HippoLog.e("Case else", "case else");
//        if (CommonData.getUpdatedDetails().getData().getCustomerInitialFormInfo() != null) {
//            openUserInitForm();
//        } else {
//            try {
//                Intent conversationsIntent = new Intent(activity.getApplicationContext(), ChannelActivity.class);
//                conversationsIntent.putExtra("title", title);
//                conversationsIntent.putExtra("hasPager", getAttributes().getAdditionalInfo().isHasChannelPager());
//                conversationsIntent.putExtra("appVersion", getAppVersion());
//                activity.startActivity(conversationsIntent);
//
//                /*if(getAttributes().getConversationalData().isHasPager()) {
//                    Intent conversationsIntent = new Intent(activity.getApplicationContext(), ChannelActivity.class);
//                    conversationsIntent.putExtra("title", title);
//                    conversationsIntent.putExtra("hasPager", getAttributes().getConversationalData().isHasPager());
//                    conversationsIntent.putExtra("appVersion", getAppVersion());
//                    activity.startActivity(conversationsIntent);
//                } else{
//                    Intent conversationsIntent = new Intent(activity.getApplicationContext(), FuguChannelsActivity.class);
//                    conversationsIntent.putExtra("title", title);
//                    conversationsIntent.putExtra("appVersion", getAppVersion());
//                    activity.startActivity(conversationsIntent);
//                }*/
//            } catch (Exception e) {
//                Intent conversationsIntent = new Intent(activity.getApplicationContext(), ChannelActivity.class);
//                conversationsIntent.putExtra("title", title);
//                conversationsIntent.putExtra("hasPager", "false");
//                conversationsIntent.putExtra("appVersion", getAppVersion());
//                activity.startActivity(conversationsIntent);
//                /*Intent conversationsIntent = new Intent(activity.getApplicationContext(), FuguChannelsActivity.class);
//                conversationsIntent.putExtra("title", title);
//                conversationsIntent.putExtra("appVersion", getAppVersion());
//                activity.startActivity(conversationsIntent);*/
//            }
//        }
//    }

//    private void openFuguSupportActivity(String faqName, String transactionId) {
//        Intent intent = new Intent(activity.getApplicationContext(), HippoSupportActivity.class);
//        intent.putExtra(FuguAppConstant.SUPPORT_ID, faqName);
//        intent.putExtra(FuguAppConstant.SUPPORT_TRANSACTION_ID, transactionId);
//        //intent.putExtra("userData", getUserData());
//        activity.startActivity(intent);
//    }


    private int getAppVersion() {
        try {
            if (activity != null) {
                return BumbleConfig.getInstance().activity.getPackageManager().getPackageInfo(BumbleConfig.getInstance()
                        .activity.getPackageName(), 0).versionCode;
            }
            return 205;
        } catch (Exception e) {
            e.printStackTrace();
            return 205;
        }
    }

    public CaptureUserDataBumble getUserData() {
        return getUserData(true);
    }

    public CaptureUserDataBumble getUserData(boolean fetchSavedData) {
        if (userData == null)
            userData = CommonData.getUserData();

        if (fetchSavedData) {
            try {
                if (TextUtils.isEmpty(userData.getEnUserId())) {
                    FuguPutUserDetailsResponse response = CommonData.getUpdatedDetails();
                    if (response != null && response.getData() != null && !TextUtils.isEmpty(response.getData().getEn_user_id())) {
                        userData.setEnUserId(response.getData().getEn_user_id());
                        userData.setUserId(response.getData().getUserId());
                        userData.setFullName(response.getData().getFullName());
                        userData.setEmail(response.getData().getEmail());
                    } else if (context != null) {
                        userData.setEnUserId(Prefs.with(context).getString("en_user_id", ""));
                        userData.setUserId(Prefs.with(context).getLong("user_id", -1l));
                        userData.setFullName(Prefs.with(context).getString("full_name", ""));
                        userData.setEmail(Prefs.with(context).getString("email", ""));
                    }
                }
            } catch (Exception e) {
                if (BumbleConfig.DEBUG)
                    e.printStackTrace();
                try {
                    if (context != null) {
                        userData.setEnUserId(Prefs.with(context).getString("en_user_id", ""));
                        userData.setUserId(Prefs.with(context).getLong("user_id", -1l));
                        userData.setFullName(Prefs.with(context).getString("full_name", ""));
                        userData.setEmail(Prefs.with(context).getString("email", ""));
                    }
                } catch (Exception e1) {

                }
            }
        }

        return userData;
    }


    // For permission

    public int getTargetSDKVersion() {
        return targetSDKVersion;
    }

    private int targetSDKVersion = 0;

    /**
     * Method to check whether the Permission is Granted by the User
     * <p/>
     * permission type: DANGEROUS
     *
     * @param activity
     * @param permission
     * @return
     */
    public boolean isPermissionGranted(Context activity, String permission) {

        PackageManager pm = activity.getPackageManager();
        try {
            ApplicationInfo applicationInfo = pm.getApplicationInfo(BumbleConfig.getInstance().activity.getPackageName(), 0);
            if (applicationInfo != null) {
                targetSDKVersion = applicationInfo.targetSdkVersion;
            }
        } catch (Exception e) {

        }

        if (targetSDKVersion > 22) {
            return ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED;
        } else {
            return PermissionChecker.checkSelfPermission(activity, permission) == PermissionChecker.PERMISSION_GRANTED;
        }
    }

    /**
     * Method to check whether the Permission is Granted by the User
     * <p/>
     * permission type: DANGEROUS
     *
     * @param activity
     * @param permission
     * @return
     */
    public boolean askUserToGrantPermission(Activity activity, String permission, String explanation, int code) {
        BumbleLog.e(TAG, "permissions" + permission);
        return askUserToGrantPermission(activity, new String[]{permission}, explanation, code);
    }

    /**
     * Method to check whether the Permission is Granted by the User
     * <p/>
     * permission type: DANGEROUS
     *
     * @param activity
     * @param permissions
     * @param explanation
     * @param requestCode
     * @return
     */
    public boolean askUserToGrantPermission(Activity activity, String[] permissions, String explanation, int requestCode) {
        String permissionRequired = null;

        for (String permission : permissions)
            if (!isPermissionGranted(activity, permission)) {
                permissionRequired = permission;
                break;
            }

        // Check if the Permission is ALREADY GRANTED
        if (permissionRequired == null) return true;

        // Check if there is a need to show the PERMISSION DIALOG
        boolean explanationRequired = ActivityCompat.shouldShowRequestPermissionRationale(activity, permissionRequired);

        // Convey the EXPLANATION if required
        if (explanationRequired) {

            if (explanation == null) explanation = "Please grant permission";
            Toast.makeText(activity, explanation, Toast.LENGTH_SHORT).show();
        } else {

            // We can request the permission, if no EXPLANATIONS required
            ActivityCompat.requestPermissions(activity, permissions, requestCode);
        }

        return false;
    }

    public void updateCount(String transactionId, int countTotal) {
        if (countCallback != null)
            countCallback.unreadCountFor(transactionId, countTotal);
    }


    //**************************** For Agent SDK *************************


//    private void registerNetworkListener(Context context) {
//        try {
//            context.registerReceiver(new FuguNetworkStateReceiver(),
//                    new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }


    private BumbleLifeCycle lifeCycle;


    public void setLifeCyclerListener(BumbleLifeCycle lifeCycle) {
        this.lifeCycle = lifeCycle;
    }

    public BumbleLifeCycle getLifeCyclerListener() {
        return lifeCycle;
    }


    /**
     * @param context             Activity context of the class
     * @param callType            1 for video call, 2 for audio call
     * @param transactionId       unique Id
     * @param otherUserName       End user name
     * @param userUniqueKey       User unique key
     * @param otherUserUniqueKeys Other user unique key
     */
    public OnApiCallback onApiCallback;

    public OnApiCallback getOnApiCallback() {
        return onApiCallback;
    }

    public void setApiListener(OnApiCallback onApiCallback) {
        this.onApiCallback = onApiCallback;
    }

    private MobileCampaignBuilderBumble campaignBuilder;

    public MobileCampaignBuilderBumble getMobileCampaignBuilder() {
        if (campaignBuilder == null) {
            campaignBuilder = CommonData.getCampaignBuilder();
        }
        return campaignBuilder;
    }

    public void setCampaignBuilder(MobileCampaignBuilderBumble campaignBuilder) {
        this.campaignBuilder = campaignBuilder;
    }

    public void openMobileCampaigns(Activity activity, MobileCampaignBuilderBumble campaignBuilder) {
        this.campaignBuilder = campaignBuilder;
//        if(campaignBuilder != null)
//            CommonData.setMobileCampaignBuilder(campaignBuilder);
        Intent intent = new Intent(activity, CampaignActivity.class);
        activity.startActivity(intent);
    }


    private HashMap<String, Integer> questions = new HashMap<>();
    private HashMap<Integer, String> suggestions = new HashMap<>();
    private HashMap<Integer, ArrayList<Integer>> mapping = new HashMap<>();

    public HashMap<String, Integer> getQuestions() {
        return questions;
    }

    public void setQuestions(HashMap<String, Integer> questions) {
        this.questions = questions;
    }

    public HashMap<Integer, String> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(HashMap<Integer, String> suggestions) {
        this.suggestions = suggestions;
    }

    public HashMap<Integer, ArrayList<Integer>> getMapping() {
        return mapping;
    }

    public void setMapping(HashMap<Integer, ArrayList<Integer>> mapping) {
        this.mapping = mapping;
    }

    private String agentEmail;
    private boolean isSingleChannelTransactionId;

    public void setAgentEmail(String agentEmail) {
        this.agentEmail = agentEmail;
    }

    public String getAgentEmail() {
        return agentEmail;
    }

    public boolean getSingleChannelTransactionId() {
        return isSingleChannelTransactionId;
    }

    public void setSingleChannelTransactionId(boolean singleChannelTransactionId) {
        isSingleChannelTransactionId = singleChannelTransactionId;
    }


    public void isChatScreenBackBtnRequired(boolean flag) {
        CommonData.setNewBackBtn(flag);
    }

    static int count = 0;


    private int getVersion(Activity context) {
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pInfo.versionCode;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 102;
        }
    }

    private String getBundle(Activity context) {
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pInfo.packageName;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "com.buddy.customer";
        }
    }


//    public void checkAutoUpdate(final Activity activity) {
//        count = count + 1;
//        if (count > 1)
//            return;
//
//        CommonParams commonParams = new CommonParams.Builder()
//                .add(BumbleAppConstants.APP_SECRET_KEY, bumbleConfig.getAppKey())
//                .add("bundle_id", getBundle(activity))
//                .add("app_type", 1)
//                .add(BumbleAppConstants.APP_VERSION, getVersion(activity))
//                .add("device_id", UniqueIMEIID.getUniqueIMEIId(activity))
//                .add(BumbleAppConstants.DEVICE_TYPE, 1)
//                .add(APP_SOURCE_TYPE, 7)
//                .build();
//
//        RestClient.getApiInterface().updateApp(commonParams.getMap()).enqueue(new ResponseResolver<AppUpdateModel>() {
//            @Override
//            public void success(AppUpdateModel updateModel) {
//                CommonData.saveVersionInfo(updateModel);
//                Prefs.with(activity).save("force_update_version", false);
//                HippoLog.e("version", "version = " + getVersion(activity));
//
//                if (updateModel.getData().getCriticalVersion() > getVersion(activity)) {
//                    Prefs.with(activity).save("force_update_version", true);
//                    SingleBtnUpdateWindow(activity, updateModel);
//                } else if (isAvailable(activity, updateModel)) {
//                    showUpdatePopup(activity);
//                }
//            }
//
//            @Override
//            public void failure(APIError error) {
//
//            }
//        });
//    }

//    private void SingleBtnUpdateWindow(final Activity context, final AppUpdateModel updateModel) {
//        try {
//
//            SingleBtnDialog.with(context).setMessage(updateModel.getData().getMessaage())
//                    .hideHeading().setCancelableOnTouchOutside(false).setCancelable(false).setCallback(new SingleBtnDialog.OnActionPerformed() {
//                @Override
//                public void positive() {
//                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(updateModel.getData().getDownloadLink()));
//                    context.startActivity(browserIntent);
//                    context.finish();
//                }
//            }).show();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    private boolean isAvailable(Activity activity, AppUpdateModel updateModel) {
//        int last_displayed_version = Prefs.with(activity).getInt("last_displayed_version", 0);
//        long last_update_displayed = Prefs.with(activity).getLong("last_update_displayed", 0);
//        boolean diff = System.currentTimeMillis() - last_update_displayed > updateModel.getData().getSoftUpdateRetry() * 60 * 1000;
//        if (getVersion(activity) < updateModel.getData().getLatestVersion()) {
//            if (last_displayed_version < updateModel.getData().getLatestVersion() || diff) {
//                //true
//                Prefs.with(activity).save("last_displayed_version", updateModel.getData().getLatestVersion());
//                Prefs.with(activity).save("last_update_displayed", System.currentTimeMillis());
//                return true;
//            }
//        }
//        return false;
//    }


//    public void showUpdatePopup(final Context context) {
//        AppUpdateModel update = CommonData.getVersionInfo();
//        if (update == null)
//            return;
//
//        String message = update.getData().getMessaage();
//        final String link = update.getData().getDownloadLink();
//
//        new androidx.appcompat.app.AlertDialog.Builder(context)
//                .setMessage(message)
//                .setPositiveButton(Restring.getString(context, R.string.fugu_ok), new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(final DialogInterface dialog, final int which) {
//                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
//                        context.startActivity(browserIntent);
//                    }
//                })
//                .show();
//
//    }

    public void setAppName(String appName) {
        CommonData.setAppName(appName);
    }

    /**
     * Where data load was requested.
     */
    private boolean serviceStarted;
    private int WAIT_TIME = 2000;
    /**
     * Whether {@link #onServiceDestroy()} has been called.
     */
    private boolean closed;

//    public void onCheckActity(final Activity activity) {
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                HippoLog.v(TAG, "onActivityCreated " + activity.getClass().getSimpleName());
//                startHippoService(activity);
//            }
//        }, WAIT_TIME);
//    }

    /**
     * Starts data loading in background if not started yet.
     */
    public void onServiceStarted() {
        if (serviceStarted) {
            return;
        }
        //serviceStarted = true;
        closed = false;
        Log.i(TAG, "onServiceStarted in BumbleConfig");

        onInitialized();
    }

    /**
     * Service have been destroyed.
     */
    public void onServiceDestroy() {
        Log.i(TAG, "onServiceDestroy");

        if (closed) {
            Log.i(TAG, "onServiceDestroy closed");
            return;
        }
        onClose();
    }

    private void onInitialized() {
        /*for (OnInitializedListener listener : getManagers(OnInitializedListener.class)) {
            Log.i(TAG, "OnInitializedListener onInitialized " + listener);
            listener.onInitialized();
        }*/
    }

    private void onClose() {
        Log.i(TAG, "onClose1");
        /*for (Object manager : registeredManagers) {
            if (manager instanceof OnCloseListener) {
                ((OnCloseListener) manager).onClose();
            }
        }*/
        closed = true;
        serviceStarted = false;
        Log.i(TAG, "onClose2");
    }

//    private synchronized void startHippoService(Activity activity) {
//        try {
//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//                activity.startForegroundService(HippoService.createIntent(activity));
//            } else {
//                activity.startService(HippoService.createIntent(activity));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * @param directOpen
     * @param labelId
     * @deprecated
     */
    public void setAdditionalInfo(boolean directOpen, Long labelId) {
        CommonData.directScreens(directOpen);
        CommonData.setConstantLabelId(labelId);
    }


    private ArrayList<String> tags;

    public void setTags(ArrayList<String> tags) {
        tags = new ArrayList<>();
        this.tags = tags;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    private String currentLanguage;

    public String getCurrentLanguage() {
        currentLanguage = CommonData.getCurrentLanguage();
        return currentLanguage;
    }


    public void openDevicePermission(Context context) {
        XiaomiUtilities.checkForDevicePermission(context);
        //XiaomiUtilities.checkForDevicePermission(context);

//        if(Build.VERSION.SDK_INT>=19 && XiaomiUtilities.isMIUI() &&
//                !XiaomiUtilities.isCustomPermissionGranted(context, XiaomiUtilities.OP_SHOW_WHEN_LOCKED)){
//
//        }
//        Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
//        intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");
//        intent.putExtra("extra_pkgname", context.getPackageName());
//        context.startActivity(intent);


//        Intent rIntent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName() );
//        PendingIntent intent = PendingIntent.getActivity(context, 0, rIntent, PendingIntent.FLAG_CANCEL_CURRENT);
//        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//        manager.set(AlarmManager.RTC, System.currentTimeMillis(), intent);
//        System.exit(2);

        //PermissionHandler.INSTANCE.addAutoStartupswitch(context);
    }

    public int getUnreadAnnouncementsCount() {
        return CommonData.getAnnouncementCount().size();
    }

}
