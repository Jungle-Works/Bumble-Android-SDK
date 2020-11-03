package com.bumble.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashSet;
import java.util.List;

/**
 * Created by Bhavya Rattan on 09/05/17
 * Click Labs
 * bhavya.rattan@click-labs.com
 */

public class FuguPutUserDetailsResponse {

    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private Data data;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {

        @SerializedName("is_whitelabel")
        @Expose
        private Boolean isWhiteLabel;
        @SerializedName("en_user_id")
        @Expose
        private String en_user_id;
        @SerializedName("user_id")
        @Expose
        private Long userId;
        @SerializedName("user_unique_key")
        @Expose
        private String userUniqueKey;
        @SerializedName("business_id")
        @Expose
        private Integer businessId;
        @SerializedName("business_name")
        @Expose
        private String businessName;
        @SerializedName("full_name")
        @Expose
        private String fullName;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("app_secret_key")
        @Expose
        private String appSecretKey;


        @SerializedName("in_app_support_panel_version")
        @Expose
        private Integer inAppSupportVersion;
        @SerializedName("is_faq_enabled")
        @Expose
        private Integer isFAQEnabled;
        @SerializedName("is_video_call_enabled")
        @Expose
        private Integer isVideoCallEnabled;
        @SerializedName("is_audio_call_enabled")
        @Expose
        private Integer isAudioCallEnabled;
        @SerializedName("max_file_size")
        @Expose
        private Long maxFileSize;
        @SerializedName("unsupported_message")
        @Expose
        private String unsupportedMessage;

        @SerializedName("user_channel")
        @Expose
        private String userChannel;
        @SerializedName("show_message_source")
        @Expose
        private int showMessageSource = 0;
        @SerializedName("encode_to_html_entites")
        @Expose
        private Integer encodeToHtmlEntites;
        @SerializedName("bot_image_url")
        @Expose
        private String botImageUrl;
        @SerializedName("multi_channel_label_mapping")
        @Expose
        private int multiChannelLabelMapping;

        @SerializedName("customer_conversation_bot_id")
        @Expose
        private String customerConversationBotId;

        @SerializedName("is_ask_payment_allowed")
        @Expose
        private int isAskPaymentAllowed;

        @SerializedName("jitsi_url")
        @Expose
        private String jitsiUrl;
//        @SerializedName("unread_announcements_count")
//        @Expose
//        private int unreadAnnouncementsCount = 0;
        @SerializedName("unread_channels")
        @Expose
        private HashSet<String> unreadChannels;

        public HashSet<String> getUnreadChannels() {
            return unreadChannels;
        }

        public void setUnreadChannels(HashSet<String> unreadChannels) {
            this.unreadChannels = unreadChannels;
        }

        public String getCustomerConversationBotId() {
            return customerConversationBotId;
        }

        public void setCustomerConversationBotId(String customerConversationBotId) {
            this.customerConversationBotId = customerConversationBotId;
        }

        public String getBotImageUrl() {
            return botImageUrl;
        }

        public void setBotImageUrl(String botImageUrl) {
            this.botImageUrl = botImageUrl;
        }

        public boolean isEncodeToHtml() {
            try {
                return encodeToHtmlEntites == 1;
            } catch (Exception e) {
                return false;
            }
        }

        public boolean isAskPaymentAllowed() {
            try {
                return isAskPaymentAllowed == 1;
            } catch (Exception e) {
                return false;
            }
        }

        public boolean isMessageSourceEnabled() {
            try {
                if(showMessageSource == 1)
                    return true;
            } catch (Exception e) {

            }
            return false;
        }

        public void setShowMessageSource(int showMessageSource) {
            this.showMessageSource = showMessageSource;
        }

        public String getUserChannel() {
            return userChannel;
        }

        public void setUserChannel(String userChannel) {
            this.userChannel = userChannel;
        }

//        public CustomerInitialFormInfo getCustomerInitalForm() {
//            return customerInitialFormInfo;
//        }
//
//        public void setCustomerInitalForm(CustomerInitialFormInfo customerInitialFormInfo) {
//            this.customerInitialFormInfo = customerInitialFormInfo;
//        }
//        @SerializedName("customer_initial_form_info")
//        @Expose
//        private CustomerInitalForm customerInitalForm;
//
//        public CustomerInitalForm getCustomerInitalForm() {
//            return customerInitalForm;
//        }
//
//        public void setCustomerInitalForm(CustomerInitalForm customerInitalForm) {
//            this.customerInitalForm = customerInitalForm;
//        }

        public String getUnsupportedMessage() {
            return unsupportedMessage;
        }

        public void setUnsupportedMessage(String unsupportedMessage) {
            this.unsupportedMessage = unsupportedMessage;
        }

        public Long getMaxFileSize() {
            return maxFileSize;
        }

        public void setMaxFileSize(Long maxFileSize) {
            this.maxFileSize = maxFileSize;
        }

        public boolean isAudioCallEnabled() {
            try {
                return isAudioCallEnabled == 1;
            } catch (Exception e) {
                return false;
            }
        }

        public void setisAudioCallEnabled(Integer isAudioCallEnabled) {
            this.isAudioCallEnabled = isAudioCallEnabled;
        }

        public boolean isVideoCallEnabled() {
            try {
                return isVideoCallEnabled == 1;
            } catch (Exception e) {
                return false;
            }
        }

        public void setIsVideoCallEnabled(Integer isVideoCallEnabled) {
            this.isVideoCallEnabled = isVideoCallEnabled;
        }

        public Long getUserId() {
            return userId;
        }

        public String getEn_user_id() {
            return en_user_id;
        }

        public String getBusinessName() {
            if (businessName == null) {
                businessName = "";
            }
            return businessName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }
        public String getFullName() {
            return fullName;
        }

        public String getAppSecretKey() {
            return appSecretKey;
        }

        public void setAppSecretKey(String appSecretKey) {
            this.appSecretKey = appSecretKey;
        }


        public Boolean getWhiteLabel() {
            if (isWhiteLabel == null) {
                isWhiteLabel = false;
            }
            return isWhiteLabel;
        }

        public void setWhiteLabel(Boolean whiteLabel) {
            isWhiteLabel = whiteLabel;
        }



        public Integer getInAppSupportVersion() {
            return inAppSupportVersion;
        }

        public void setInAppSupportVersion(Integer inAppSupportVersion) {
            this.inAppSupportVersion = inAppSupportVersion;
        }

        public boolean isFAQEnabled() {
            try {
                return isFAQEnabled == 1;
            } catch (Exception e) {
                return false;
            }
        }


        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        @SerializedName("hide_direct_call_button")
        @Expose
        private Integer isCallEnabled;

        public boolean isNavCallBtnDisabled() {
            try {
                return isCallEnabled == 1;
            } catch (Exception e) {
                return false;
            }
        }

        public void setIsNavCallBtnEnabled(Integer isCallEnabled) {
            this.isCallEnabled = isCallEnabled;
        }

        public boolean isMultiChannelLabelMapping() {
            try {
                return multiChannelLabelMapping == 1;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        public int getMultiChannelLabelMapping() {
            return multiChannelLabelMapping;
        }

        public void setMultiChannelLabelMapping(Integer multiChannelLabelMapping) {
            this.multiChannelLabelMapping = multiChannelLabelMapping;
        }

        public String getJitsiUrl() {
            return jitsiUrl;
        }

        public void setJitsiUrl(String jitsiUrl) {
            this.jitsiUrl = jitsiUrl;
        }

//        public int getUnreadAnnouncementsCount() {
//            return unreadAnnouncementsCount;
//        }
//
//        public void setUnreadAnnouncementsCount(int unreadAnnouncementsCount) {
//            this.unreadAnnouncementsCount = unreadAnnouncementsCount;
//        }

        //        public boolean isHideDirectCallButton() {
//            try {
//                return hideDirectCallButton == 1;
//            } catch (Exception e) {
//                e.printStackTrace();
//                return false;
//            }
//        }
//
//        public void setHideDirectCallButton(Integer hideDirectCallButton) {
//            this.hideDirectCallButton = hideDirectCallButton;
//        }
    }
}