package com.bumble.database;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Message;
import android.text.TextUtils;

import com.bumble.BumbleConfigAttributes;
import com.bumble.CaptureUserDataBumble;
import com.bumble.HippoColorConfig;
import com.bumble.MobileCampaignBuilderBumble;
import com.bumble.Utils.BumbleLog;
import com.bumble.Utils.Restring;
import com.bumble.Utils.Translation;
import com.bumble.constants.BumbleAppConstants;

import com.bumble.model.AppUpdateModel;
import com.bumble.model.FuguDeviceDetails;
import com.bumble.model.FuguPutUserDetailsResponse;
import com.bumble.model.UnreadCountModel;
import com.bumble.model.promotional.PromotionResponse;
import com.bumblesdk.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.TreeMap;

import io.paperdb.Paper;

import static com.bumble.constants.BumbleAppConstants.BUMBLE_PAPER_NAME;

/**
 * Created by Bhavya Rattan on 15/05/17
 * Click Labs
 * bhavya.rattan@click-labs.com
 */

public final class CommonData implements PaperDbConstant {

    public static FuguPutUserDetailsResponse USER_DETAILS = null;
    public static String SERVER_URL = "";
    public static String APP_SECRET_KEY = "";
    public static String APP_TYPE = "1";
    public static TreeMap<Long, TreeMap<String, Message>> UNSENT_MESSAGE_MAP = new TreeMap<>();
    public static HippoColorConfig COLOR_CONFIG = new HippoColorConfig();
    public static String isNewChatKey = "IS_NEW_CHAT";
    public static String providerKey = "PROVIDER_KEY";
    public static String pushKey = "PUSH_KEY";
    public static String NOTIFICATION_FIRST_CLICK = "hippo_notification_first_click";
    public static String pushChannelKey = "PUSH_CHANNEL_KEY";
    public static String isAppOpenKey = "isAppOpen";
    public static TreeMap<Long, ArrayList<Message>> FUGU_MESSAGE_LIST = new TreeMap<>();
    public static final String clearFuguDataKey = "clearHippoData";
    public static final String clearBumbleDataKey = "clearBumbleData";
    public static final String HIPPO_UNREAD_COUNT = "hippo_unread_count";
    private static String keyQuickReply = "saveQuickReplay";


    public static HashMap<Long, LinkedHashMap<String, JSONObject>> UNSENT_MESSAGE_JSON = new HashMap<>();
    public static HashMap<Long, LinkedHashMap<String, Message>> SENT_MESSAGES = new HashMap<>();
    public static HashMap<Long, LinkedHashMap<String, Message>> UNSENT_MESSAGES = new HashMap<>();
    public static HashMap<Long, Long> LABEL_CHANNEL_ID = new HashMap<>();



    /**
     * Empty Constructor
     * not called
     */
    private CommonData() {
    }





    /**
     * Save PAPER_SERVER_URL
     *
     * @param serverUrl
     */

    public static void setServerUrl(String serverUrl) {
        SERVER_URL = serverUrl;
        Paper.book(BUMBLE_PAPER_NAME).write(PAPER_SERVER_URL, CommonData.SERVER_URL);
    }

    /**
     * Gets PAPER_CONVERSATION_LIST
     *
     * @return the serverUrl
     */

    public static String getServerUrl() {
        if (TextUtils.isEmpty(SERVER_URL.trim())) {
            try {
                SERVER_URL = Paper.book(BUMBLE_PAPER_NAME).read(PAPER_SERVER_URL, BumbleAppConstants.LIVE_SERVER);
            } catch (Exception e) {
                SERVER_URL = BumbleAppConstants.LIVE_SERVER;
            }
        }
        return SERVER_URL;
    }


    /**
     * Save PAPER_SERVER_URL
     *
     * @param serverUrl
     */

    public static void setSocketServerUrl(String serverUrl) {
        Paper.book(BUMBLE_PAPER_NAME).write(PAPER_SOCKET_SERVER_URL, serverUrl);
    }

    /**
     * Gets PAPER_CONVERSATION_LIST
     *
     * @return the serverUrl
     */

    public static String getSocketServerUrl() {
        try {
            return Paper.book(BUMBLE_PAPER_NAME).read(PAPER_SOCKET_SERVER_URL, BumbleAppConstants.LIVE_SOCKEY_SERVER);
        } catch (Exception e) {
            return BumbleAppConstants.LIVE_SOCKEY_SERVER;
        }
    }


    /**
     * Save PAPER_APP_SECRET_KEY
     *
     * @param appSecretKey
     */

    public static void setAppSecretKey(String appSecretKey) {
        APP_SECRET_KEY = appSecretKey;
        Paper.book(BUMBLE_PAPER_NAME).write(PAPER_APP_SECRET_KEY, CommonData.APP_SECRET_KEY);
    }

    /**
     * Gets PAPER_APP_SECRET_KEY
     *
     * @return the appSecretKey
     */

    public static String getAppSecretKey() {
        if (APP_SECRET_KEY.isEmpty()) {
            APP_SECRET_KEY = Paper.book(BUMBLE_PAPER_NAME).read(PAPER_APP_SECRET_KEY, "");
        }
        return APP_SECRET_KEY;
    }


    /**
     * Save PAPER_APP_SECRET_KEY
     *
     * @param appType
     */

    public static void setAppType(String appType) {
        APP_TYPE = appType;
        Paper.book(BUMBLE_PAPER_NAME).write(PAPER_APP_TYPE, CommonData.APP_TYPE);
    }

    /**
     * Gets PAPER_APP_SECRET_KEY
     *
     * @return the appSecretKey
     */

    public static String getAppType() {
        APP_TYPE = Paper.book(BUMBLE_PAPER_NAME).read(PAPER_APP_TYPE, "1");
        return APP_TYPE;
    }

    public static int getPushFlags() {
        try {
            return Paper.book(BUMBLE_PAPER_NAME).read(PAPER_PUSH_FLAGS, -1);
        } catch (Exception e) {
            return -1;
        }
    }

    public static void setPushFlags(int flags) {
        Paper.book(BUMBLE_PAPER_NAME).write(PAPER_PUSH_FLAGS, flags);
    }


    /**
     * Gets PAPER_USER_DETAILS
     *
     * @return the userDetails
     */

    public static FuguPutUserDetailsResponse getUserDetails() {
        if (USER_DETAILS == null)
            USER_DETAILS = Paper.book(BUMBLE_PAPER_NAME).read(PAPER_USER_DETAILS, null);
        return USER_DETAILS;
    }

    /**
     * Save PAPER_COLOR_CONFIG
     *
     * @param hippoColorConfig
     */
    public static void setColorConfig(HippoColorConfig hippoColorConfig) {
        CommonData.COLOR_CONFIG = hippoColorConfig;
        Paper.book(BUMBLE_PAPER_NAME).write(PAPER_COLOR_CONFIG, hippoColorConfig);
    }

    /**
     * Gets PAPER_COLOR_CONFIG
     *
     * @return the fuguColorConfig
     */

    public static HippoColorConfig getColorConfig() {
        if (COLOR_CONFIG == null)
            COLOR_CONFIG = Paper.book(BUMBLE_PAPER_NAME).read(PAPER_COLOR_CONFIG, null);
        return COLOR_CONFIG;
    }

    /**
     * Save PAPER_USER_DETAILS
     *
     * @param userDetails
     */
    public static void setUserDetails(FuguPutUserDetailsResponse userDetails) {
        CommonData.USER_DETAILS = userDetails;
        Paper.book(BUMBLE_PAPER_NAME).write(PAPER_USER_DETAILS, userDetails);
    }

    public static FuguPutUserDetailsResponse getUpdatedDetails() {
        USER_DETAILS = Paper.book(BUMBLE_PAPER_NAME).read(PAPER_USER_DETAILS, null);
        return USER_DETAILS;
    }


    //======================================== Clear Data ===============================================

    /**
     * Delete paper.
     */
    public static void clearData() throws Exception {
        USER_DETAILS = null;
        UNSENT_MESSAGE_MAP = new TreeMap<>();
        SERVER_URL = "";
        APP_SECRET_KEY = "";
        COLOR_CONFIG = new HippoColorConfig();
        APP_TYPE = "";
        Paper.book(BUMBLE_PAPER_NAME).destroy();
    }

    public static JsonObject deviceDetails(Context context) {
        Gson gson = new GsonBuilder().create();
        JsonObject deviceDetailsJson = null;
        try {
            deviceDetailsJson = gson.toJsonTree(new FuguDeviceDetails(
                    getAppVersion(context)).getDeviceDetails()).getAsJsonObject();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return deviceDetailsJson;
    }

    public static String deviceDetailString(Context context) {
        Gson gson = new GsonBuilder().create();
        String deviceDetailsJson = null;
        try {
            deviceDetailsJson = gson.toJson(new FuguDeviceDetails(
                    getAppVersion(context)).getDeviceDetails());
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return deviceDetailsJson;
    }


    public static int getAppVersion(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String getPackageName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).packageName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
                 en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (Exception ex) {
            BumbleLog.e("IP Address", ex.toString());
        }
        return null;
    }

    public static void setIsNewchat(boolean isNewchat) {
        Paper.book(BUMBLE_PAPER_NAME).write(isNewChatKey, isNewchat);
    }

    public static boolean getIsNewChat() {
        return Paper.book(BUMBLE_PAPER_NAME).read(isNewChatKey);
    }

    public static void setProvider(String provider) {
        Paper.book(BUMBLE_PAPER_NAME).write(providerKey, provider);
    }

    public static String getProvider() {
        return Paper.book(BUMBLE_PAPER_NAME).read(providerKey);
    }

    public static void setPushBoolean(boolean push) {
        Paper.book(BUMBLE_PAPER_NAME).write(pushKey, push);
    }

    public static boolean getPushBoolean() {
        if (Paper.book(BUMBLE_PAPER_NAME).read(pushKey) == null) {
            return false;
        }
        return Paper.book(BUMBLE_PAPER_NAME).read(pushKey);
    }

    public static void setNotificationFirstClick(boolean flag) {
        Paper.book(BUMBLE_PAPER_NAME).write(NOTIFICATION_FIRST_CLICK, flag);
    }

    public static boolean isFirstTimeWithNotification() {
        return Paper.book(BUMBLE_PAPER_NAME).read(NOTIFICATION_FIRST_CLICK, false);
    }

    public static void setPushChannel(Long pushChannel) {
        Paper.book(BUMBLE_PAPER_NAME).write(pushChannelKey, pushChannel);
    }

    public static Long getPushChannel() {
        if (Paper.book(BUMBLE_PAPER_NAME).read(pushChannelKey) == null) {
            return -1l;
        }
        return Paper.book(BUMBLE_PAPER_NAME).read(pushChannelKey);
    }

    public static void clearPushChannel() {
        Paper.book(BUMBLE_PAPER_NAME).delete(pushChannelKey);
    }

    public static void setTransactionIdsMap(HashMap<String, Long> transactionIdsMap) {
        Paper.book(BUMBLE_PAPER_NAME).write("TransactionIdsMap", transactionIdsMap);
    }

    public static HashMap<String, Long> getTransactionIdsMap() {
        try {
            return Paper.book(BUMBLE_PAPER_NAME).read("TransactionIdsMap");
        } catch (Exception e) {
            return new HashMap<String, Long>();
        }
    }

    public static boolean getIsAppOpen() {
        if (Paper.book(BUMBLE_PAPER_NAME).read(isAppOpenKey) == null) {
            return true;
        }
        return Paper.book(BUMBLE_PAPER_NAME).read(isAppOpenKey);
    }

    public static void setIsAppOpen(boolean isAppOpen) {
        Paper.book(BUMBLE_PAPER_NAME).write(isAppOpenKey, isAppOpen);
    }

    public static void setClearBumbleDataKey(boolean clearFuguData) {
        Paper.book(BUMBLE_PAPER_NAME).write(clearBumbleDataKey, clearFuguData);
    }

    public static boolean getClearBumbleDataKey() {
        return Paper.book(BUMBLE_PAPER_NAME).read(clearBumbleDataKey, false);
    }

    public static void saveDeviceToken(String deviceToken) {
        Paper.book(BUMBLE_PAPER_NAME).write(PAPER_DEVICE_TOKEN, deviceToken);
    }

    public static String getDeviceToken() {
        return Paper.book(BUMBLE_PAPER_NAME).read(PAPER_DEVICE_TOKEN, "");
    }



    public static void setIsDataCleared(boolean isDataCleared) {
        Paper.book(BUMBLE_PAPER_NAME).write("isDataCleared", isDataCleared);
    }

    public static boolean getIsDataCleared() {
        return Paper.book(BUMBLE_PAPER_NAME).read("isDataCleared", false);
    }

    public static void setChatTitle(String chatTitle) {
        Paper.book(BUMBLE_PAPER_NAME).write("chat_title", chatTitle);
    }

    public static String getChatTitle(Activity activity) {
        String title = Paper.book(BUMBLE_PAPER_NAME).read("chat_title", "");
        if (TextUtils.isEmpty(title))
            title = Restring.getString(activity, R.string.fugu_support);
        return title;
    }

    public static String getChatTitleContext() {
        return Paper.book(BUMBLE_PAPER_NAME).read("chat_title", "");
    }

    public static void saveResellerData(String resellerToken, int referenceId) {
        Paper.book(BUMBLE_PAPER_NAME).write("fugu_resellerToken", resellerToken);
        Paper.book(BUMBLE_PAPER_NAME).write("fugu_referenceId", referenceId);
    }

    public static void saveFuguConfigAttribute(BumbleConfigAttributes attributes) {
        Paper.book(BUMBLE_PAPER_NAME).write("hippo_config_attribute", attributes);
    }

    public static BumbleConfigAttributes getAttributes() {
        return Paper.book(BUMBLE_PAPER_NAME).read("hippo_config_attribute");
    }

    public static void saveUserData(CaptureUserDataBumble userData) {
        Paper.book(BUMBLE_PAPER_NAME).write("hippo_userData", userData);
        setUserId(String.valueOf(userData.getUserId()));
    }

    public static CaptureUserDataBumble getUserData() {
        try {
            return Paper.book(BUMBLE_PAPER_NAME).read("hippo_userData", new CaptureUserDataBumble());
        } catch (Exception e) {
            return new CaptureUserDataBumble();
        }
    }

    public static int getReferenceId() {
        return Paper.book(BUMBLE_PAPER_NAME).read("fugu_referenceId", 1);
    }

    public static String getResellerToken() {
        return Paper.book(BUMBLE_PAPER_NAME).read("fugu_resellerToken", null);
    }

    // for support

    public static void setCurrentVersion(int versionCode) {
        Paper.book(BUMBLE_PAPER_NAME).write(PAPER_DB_VERSION, versionCode);
    }

    public static int getLocalVersion() {
        return Paper.book(BUMBLE_PAPER_NAME).read(PAPER_DB_VERSION, -1);
    }

    public static String getDefaultCategory() {
        return Paper.book(BUMBLE_PAPER_NAME).read(PAPER_USER_DEFAULT_CATEGORY, null);
    }

    public static void setDefaultCategory(String defaultCategory) {
        Paper.book(BUMBLE_PAPER_NAME).write(PAPER_USER_DEFAULT_CATEGORY, defaultCategory);
    }

    public static void setSupportPath(ArrayList<String> pathList) {
        Paper.book(BUMBLE_PAPER_NAME).write(PAPER_SUPPORT_PATH, pathList);
        BumbleLog.d("TAG", "Path = " + new Gson().toJson(pathList));
    }

    public static ArrayList<String> getPathList() {
        return Paper.book(BUMBLE_PAPER_NAME).read(PAPER_SUPPORT_PATH, new ArrayList<String>());
    }

    public static void removeLastPath() {
        ArrayList<String> pathList = getPathList();
        pathList.remove(pathList.size() - 1);
        setSupportPath(pathList);
    }

    public static void clearPathList() {
        Paper.book(BUMBLE_PAPER_NAME).delete(PAPER_SUPPORT_PATH);
    }

    public static void saveUserUniqueKey(String userUniqueKey) {
        Paper.book(BUMBLE_PAPER_NAME).write(PAPER_USER_UNIQUE_KEY, userUniqueKey);
    }

    public static String getUserUniqueKey() {
        return Paper.book(BUMBLE_PAPER_NAME).read(PAPER_USER_UNIQUE_KEY);
    }

    public static Message getQuickReplyData() {
        return Paper.book(BUMBLE_PAPER_NAME).read(keyQuickReply);
    }

    public static void setQuickReplyData(Message quickReplyData) {
        Paper.book(BUMBLE_PAPER_NAME).write(keyQuickReply, quickReplyData);
    }

    public static void clearQuickReplyData() {
        Paper.book(BUMBLE_PAPER_NAME).delete(keyQuickReply);
    }

    // Unsent messages as JSONObject
    public static void setUnsentMessageMapByChannel(Long uniqueId, LinkedHashMap<String, JSONObject> unsentMessageMap) {
        UNSENT_MESSAGE_JSON = getUnsentMessageMap();
        if (unsentMessageMap != null && unsentMessageMap.values().size() > 0) {
            UNSENT_MESSAGE_JSON.put(uniqueId, unsentMessageMap);
            Paper.book(BUMBLE_PAPER_NAME).write(PAPER_UNSENT_MESSAGE_MAP, UNSENT_MESSAGE_JSON);
        }
    }

    public static void removeUnsentMessageMapChannel(Long channelId) {
        UNSENT_MESSAGE_JSON = getUnsentMessageMap();
        UNSENT_MESSAGE_JSON.remove(channelId);
        Paper.book(BUMBLE_PAPER_NAME).write(PAPER_UNSENT_MESSAGE_MAP, UNSENT_MESSAGE_JSON);
    }

    public static LinkedHashMap<String, JSONObject> getUnsentMessageMapByChannel(Long channelId) {
        UNSENT_MESSAGE_JSON = getUnsentMessageMap();
        return UNSENT_MESSAGE_JSON.get(channelId);
    }
    // Unsent messages as JSONObject ended

    public static HashMap<Long, LinkedHashMap<String, JSONObject>> getUnsentMessageMap() {
        try {
            UNSENT_MESSAGE_JSON = Paper.book(BUMBLE_PAPER_NAME).read(PAPER_UNSENT_MESSAGE_MAP, new HashMap<Long, LinkedHashMap<String, JSONObject>>());
        } catch (Exception e) {
            UNSENT_MESSAGE_JSON = new HashMap<Long, LinkedHashMap<String, JSONObject>>();
        }
        return UNSENT_MESSAGE_JSON;
    }

    // clear all Unsent messages as JSONObject
    public static void clearAllUnsentMessages() {
        Paper.book(BUMBLE_PAPER_NAME).delete(PAPER_UNSENT_MESSAGE_MAP);
        Paper.book(BUMBLE_PAPER_NAME).delete(PAPER_UNSENT_MESSAGES);
    }

    // Unsent messages as JSONObject ended


    //Unsent messages as Object

    public static void setAllUnsentMessageByChannel(LinkedHashMap<String, Message> unsentMessage) {
        Paper.book(BUMBLE_PAPER_NAME).write(PAPER_UNSENT_MESSAGES, unsentMessage);
    }

    public static void setUnsentMessageByChannel(Long channelId, LinkedHashMap<String, Message> unsentMessage) {
        UNSENT_MESSAGES = getUnsentMessages();
        if (unsentMessage != null && unsentMessage.values().size() > 0) {
            UNSENT_MESSAGES.put(channelId, unsentMessage);
            Paper.book(BUMBLE_PAPER_NAME).write(PAPER_UNSENT_MESSAGES, UNSENT_MESSAGES);
        }
    }

    public static void removeUnsentMessageChannel(Long channelId) {
        UNSENT_MESSAGES = getUnsentMessages();
        UNSENT_MESSAGES.remove(channelId);
        Paper.book(BUMBLE_PAPER_NAME).write(PAPER_UNSENT_MESSAGES, UNSENT_MESSAGES);
    }

    public static LinkedHashMap<String, Message> getUnsentMessageByChannel(Long channelId) {
        UNSENT_MESSAGES = getUnsentMessages();
        return UNSENT_MESSAGES.get(channelId);
    }

    public static HashMap<Long, LinkedHashMap<String, Message>> getUnsentMessages() {
        try {
            UNSENT_MESSAGES = Paper.book(BUMBLE_PAPER_NAME).read(PAPER_UNSENT_MESSAGES, new HashMap<Long, LinkedHashMap<String, Message>>());
        } catch (Exception e) {
            UNSENT_MESSAGES = new HashMap<Long, LinkedHashMap<String, Message>>();
        }
        return UNSENT_MESSAGES;
    }
    //Unsent messages as Object ended


    //Sent messages
    public static void addExistingMessages(Long channelId, LinkedHashMap<String, Message> sentMessage) {
        LinkedHashMap<String, Message> allSentMessages = getSentMessageByChannel(channelId);
        allSentMessages.putAll(sentMessage);
        setSentMessageByChannel(channelId, allSentMessages);
    }

    public static void setSentMessageByChannel(Long channelId, LinkedHashMap<String, Message> sentMessage) {
        SENT_MESSAGES = getSentMessages();
        SENT_MESSAGES.put(channelId, sentMessage);
        Paper.book(BUMBLE_PAPER_NAME).write(PAPER_SENT_MESSAGES, SENT_MESSAGES);

    }

    public static void removeSentMessageChannel(Long channelId) {
        SENT_MESSAGES = getSentMessages();
        SENT_MESSAGES.remove(channelId);
        Paper.book(BUMBLE_PAPER_NAME).write(PAPER_SENT_MESSAGES, SENT_MESSAGES);
    }

    public static LinkedHashMap<String, Message> getSentMessageByChannel(Long channelId) {
        SENT_MESSAGES = getSentMessages();
        return SENT_MESSAGES.get(channelId);
    }

    public static HashMap<Long, LinkedHashMap<String, Message>> getSentMessages() {
        try {
            SENT_MESSAGES = Paper.book(BUMBLE_PAPER_NAME).read(PAPER_SENT_MESSAGES, new HashMap<Long, LinkedHashMap<String, Message>>());
        } catch (Exception e) {
            SENT_MESSAGES = new HashMap<Long, LinkedHashMap<String, Message>>();
        }
        return SENT_MESSAGES;
    }
    //Sent messages ended


    public static String getCallStatus() {
        return Paper.book(BUMBLE_PAPER_NAME).read(PAPER_CALL_STATUS);
    }

    public static void setCallStatus(String callStatus) {
        Paper.book(BUMBLE_PAPER_NAME).write(PAPER_CALL_STATUS, callStatus);
    }

    public static String getCallType() {
        return Paper.book(BUMBLE_PAPER_NAME).read(PAPER_CALL_TYPE);
    }

    public static void setUserId(String userId) {
        Paper.book(BUMBLE_PAPER_NAME).write(PAPER_USER_ID, userId);
    }

    public static void setCallType(String callType) {
        Paper.book(BUMBLE_PAPER_NAME).write(PAPER_CALL_TYPE, callType);
    }

    public static boolean isEncodeToHtml() {
        try {
            return getUserDetails().getData().isEncodeToHtml();
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean getDirectCallBtnDisabled() {
        try {
            return getUserDetails().getData().isNavCallBtnDisabled();
        } catch (Exception e) {
            return true;
        }
    }


    public static boolean getVideoCallStatus() {
        try {
            return getUserDetails().getData().isVideoCallEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean getAudioCallStatus() {
        try {
            return getUserDetails().getData().isAudioCallEnabled();
        } catch (Exception e) {
            return false;
        }
    }








    public static void setTime(String timeStamp) {
        Paper.book(BUMBLE_PAPER_NAME).write("hippo_time_stamp", timeStamp);
    }

    public static String getTime() {
        return Paper.book(BUMBLE_PAPER_NAME).read("hippo_time_stamp");
    }

    public static void setImageMuid(String imageMuid) {
        Paper.book(BUMBLE_PAPER_NAME).write("hippo_image_muid", imageMuid);
    }

    public static String getImageMuid() {
        return Paper.book(BUMBLE_PAPER_NAME).read("hippo_image_muid");
    }


    public static void setFirstTimeCreated(boolean flag) {
        Paper.book(BUMBLE_PAPER_NAME).write("hippo_create_first_time", flag);
    }

    public static boolean isFirstTimeCreated() {
        return Paper.book(BUMBLE_PAPER_NAME).read("hippo_create_first_time", true);
    }






//    public static String getUserCountryCode() {
//        return Paper.book(BUMBLE_PAPER_NAME).read("hippo_country_code", "");
//    }

    public static String getUserContCode() {
        return Paper.book(BUMBLE_PAPER_NAME).read("hippo_cont_code", "");
    }

//    public static void setUserCountryCode(String userCountryCode) {
//        Paper.book(BUMBLE_PAPER_NAME).write("hippo_country_code", userCountryCode);
//    }

    public static void setUserContCode(String userContCode) {
        Paper.book(BUMBLE_PAPER_NAME).write("hippo_cont_code", userContCode);
    }

    public static void savePutUserParams(HashMap<String, Object> commonParamsMap) {
        Paper.book(BUMBLE_PAPER_NAME).write("hippo_user_putdata", commonParamsMap);
    }

    public static HashMap<String, Object> getPutUserParams() {
        return Paper.book(BUMBLE_PAPER_NAME).read("hippo_user_putdata", new HashMap<String, Object>());
    }



    public static void clearPaymentData() {
        Paper.book(BUMBLE_PAPER_NAME).delete("hippo_payment_data");
    }

    public static void setLeftTimeInSec(long leftTimeInSec) {
        Paper.book(BUMBLE_PAPER_NAME).write("hippo_left_time", leftTimeInSec);
        Paper.book(BUMBLE_PAPER_NAME).write("hippo_left_time_added", System.currentTimeMillis());
    }

    public static long getLeftTime() {
        return Paper.book(BUMBLE_PAPER_NAME).read("hippo_left_time");
    }

    public static long getAddedLeftTime() {
        return Paper.book(BUMBLE_PAPER_NAME).read("hippo_left_time_added");
    }

    public static void clearLeftTimeInSec() {
        Paper.book(BUMBLE_PAPER_NAME).delete("hippo_left_time");
        Paper.book(BUMBLE_PAPER_NAME).delete("hippo_left_time_added");
    }

    public static void setUserImagePath(String imagePath) {
        Paper.book(BUMBLE_PAPER_NAME).write("hippo_user_image_path", imagePath);
    }

    public static String getImagePath() {
        return Paper.book(BUMBLE_PAPER_NAME).read("hippo_user_image_path", "");
    }

    public static void setBotId(Integer botId) {
        if (botId != null)
            Paper.book(BUMBLE_PAPER_NAME).write("hippo_bot_id", botId);
        else
            Paper.book(BUMBLE_PAPER_NAME).delete("hippo_bot_id");
    }

    public static int getBotId() {
        return Paper.book(BUMBLE_PAPER_NAME).read("hippo_bot_id", -1);
    }

    public static void skipBot(boolean skipBot) {
        Paper.book(BUMBLE_PAPER_NAME).write("hippo_skip_bot", skipBot);
    }

    public static boolean getSkipBot() {
        return Paper.book(BUMBLE_PAPER_NAME).read("hippo_skip_bot", false);
    }


    public static MobileCampaignBuilderBumble getCampaignBuilder() {
//        String title = Paper.book(HIPPO_PAPER_NAME).read("campaignBuilder", null);
//        MobileCampaignBuilder campaignBuilder = new MobileCampaignBuilder.Builder().setTitle(title).build();
        //String title = Paper.book(HIPPO_PAPER_NAME).read("title", "Inbox");
        MobileCampaignBuilderBumble campaignBuilder = new MobileCampaignBuilderBumble.Builder().setTitle(getMobilePushTitle()).build();
        return campaignBuilder;
        /*try {
            return Paper.book(HIPPO_PAPER_NAME).read("campaignBuilder", null);
        } catch (Exception e) {
            String title = Paper.book(HIPPO_PAPER_NAME).read("title", "Inbox");
            MobileCampaignBuilder campaignBuilder = new MobileCampaignBuilder.Builder().setTitle(getMobilePushTitle()).build();
            return campaignBuilder;
        }*/
    }

    public static void setMobileCampaignBuilder(MobileCampaignBuilderBumble campaignBuilder) {
        Paper.book(BUMBLE_PAPER_NAME).write("campaignBuilder", campaignBuilder);
    }


    public static String getMobilePushTitle() {
        return Paper.book(BUMBLE_PAPER_NAME).read("campaignBuilderTitle", "Inbox");
    }

    public static void setMobilePushTitle(String title) {
        Paper.book(BUMBLE_PAPER_NAME).write("campaignBuilderTitle", title);
    }

    public static String getActionId() {
        return Paper.book(BUMBLE_PAPER_NAME).read("hippo_actionId", "");
    }

    public static void setActionId(String actionId) {
        Paper.book(BUMBLE_PAPER_NAME).write("hippo_actionId", actionId);
    }

    public static String getUrl() {
        return Paper.book(BUMBLE_PAPER_NAME).read("hippo_action_url", "");
    }

    public static void setUrl(String url) {
        Paper.book(BUMBLE_PAPER_NAME).write("hippo_action_url", url);
    }

    public static void setChannelId(Long labelId, Long channelId) {
        HashMap<Long, Long> allChannels = getChhannelLabelds();
        allChannels.put(labelId, channelId);
        Paper.book(BUMBLE_PAPER_NAME).write("hippo_channel_labels", allChannels);
    }

    private static HashMap<Long, Long> getChhannelLabelds() {
        try {
            return Paper.book(BUMBLE_PAPER_NAME).read("hippo_channel_labels", new HashMap<Long, Long>());
        } catch (Exception e) {
            return new HashMap<Long, Long>();
        }
    }

    public static Long getChannelId(Long labelId) {
        try {
            HashMap<Long, Long> allChannels = getChhannelLabelds();
            Long channelId = -1l;
            channelId = allChannels.get(labelId);
            if (channelId == null)
                channelId = -1l;
            return channelId;
        } catch (Exception e) {
            return -1l;
        }
    }

    public static void setNewBackBtn(boolean flag) {
        Paper.book(BUMBLE_PAPER_NAME).write("hippo_nav_btn_required", flag);
    }

    public static boolean isBackBtn() {
        return Paper.book(BUMBLE_PAPER_NAME).read("hippo_nav_btn_required", true);
    }

    public static void saveVersionInfo(AppUpdateModel update) {
        Paper.book(BUMBLE_PAPER_NAME).write("hippo_version_check", update);
    }

    public static AppUpdateModel getVersionInfo() {
        try {
            return Paper.book(BUMBLE_PAPER_NAME).read("hippo_version_check", null);
        } catch (Exception e) {
            return null;
        }
    }




    public static String getAppName() {
        return Paper.book(BUMBLE_PAPER_NAME).read("hippo_app_name");
    }

    public static void setAppName(String appName) {
        Paper.book(BUMBLE_PAPER_NAME).write("hippo_app_name", appName);
    }

    public static void setConstantLabelId(Long constantLabelId) {
        Paper.book(BUMBLE_PAPER_NAME).write("constant_label_id", constantLabelId);
    }

    public static Long getConstantLabelId() {
        return Paper.book(BUMBLE_PAPER_NAME).read("constant_label_id", -1l);
    }

    public static void directScreens(boolean flag) {
        Paper.book(BUMBLE_PAPER_NAME).write("directScreens", flag);
    }

    public static boolean hasDirectScreen() {
        return Paper.book(BUMBLE_PAPER_NAME).read("directScreens", false);
    }

    public static PromotionResponse getPromotionResponse() {
        return Paper.book(BUMBLE_PAPER_NAME).read("promotionResponse", null);
    }

    public static void savePromotionResponse(PromotionResponse response) {
        Paper.book(BUMBLE_PAPER_NAME).write("promotionResponse", response);
    }

    public static HashSet<String> getAnnouncementCount() {
        return Paper.book(BUMBLE_PAPER_NAME).read("hippo_annoucement_list", new HashSet<String>());
    }

    public static void setAnnouncementCount(HashSet<String> count) {
        Paper.book(BUMBLE_PAPER_NAME).write("hippo_annoucement_list", count);
    }

    public static void deleteAnnouncementCount() {
        Paper.book(BUMBLE_PAPER_NAME).write("hippo_annoucement_list", new HashSet<String>());
    }

    public static void saveLangKeys(Translation translation) {
        Paper.book(BUMBLE_PAPER_NAME).write("hippo_lang_keys", translation);
    }

    public static Translation getLangKeys() {
        return Paper.book(BUMBLE_PAPER_NAME).read("hippo_lang_keys", new Translation());
    }

    public static String getCurrentLanguage() {
        return Paper.book(BUMBLE_PAPER_NAME).read("hippo_current_lang", "");
    }

    public static void saveCurrentLang(String lang) {
        Paper.book(BUMBLE_PAPER_NAME).write("hippo_current_lang", lang);
    }

    public static void saveRequiredLang(String lang) {
        Paper.book(BUMBLE_PAPER_NAME).write("hippo_required_lang", lang);
    }

    public static String getRequiredLanguage() {
        return Paper.book(BUMBLE_PAPER_NAME).read("hippo_required_lang", "");
    }

    public void clearLangKeys() {
        Paper.book(BUMBLE_PAPER_NAME).delete("hippo_lang_keys");
    }
    public static ArrayList<UnreadCountModel> getUnreadCountModel() {
        ArrayList<UnreadCountModel> countModel = null;
        try {
            countModel = Paper.book(BUMBLE_PAPER_NAME).read(HIPPO_UNREAD_COUNT, new ArrayList<UnreadCountModel>());
        } catch (Exception e) {
            countModel = new ArrayList<UnreadCountModel>();
        }
        return countModel;
    }

    public static void setUnreadCount(ArrayList<UnreadCountModel> unreadCount) {
        //FuguLog.v("Array", "Array = "+new Gson().toJson(unreadCount, listType));
        Paper.book(BUMBLE_PAPER_NAME).write(HIPPO_UNREAD_COUNT, unreadCount);
    }


}
