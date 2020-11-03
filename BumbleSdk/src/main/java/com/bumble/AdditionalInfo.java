package com.bumble;

/**
 * Created by gurmail on 2020-03-09.
 *
 * @author gurmail
 */
public class AdditionalInfo {

    private boolean hasChannelPager;
    private String emptyChannelList;
    private boolean hasCreateNewChat;
    private String createChatBtnText;
    private boolean hasLogout;
    private boolean replyOnDiable;
    private boolean replyOnFeedback;
    private boolean showAgentImage;
    private boolean showEmptyChatBtn;
    private boolean needDeviceOptimization;
    private boolean fetchPaymentMethod;
    private boolean isAnnouncementCount;

    public boolean showEmptyChatBtn() {
        return showEmptyChatBtn;
    }

    public boolean isHasChannelPager() {
        return hasChannelPager;
    }

    public String getEmptyChannelList() {
        return emptyChannelList;
    }

    public boolean isHasCreateNewChat() {
        return hasCreateNewChat;
    }

    public String getCreateChatBtnText() {
        return createChatBtnText;
    }

    public boolean hasLogoutBtn() {
        return hasLogout;
    }

    public boolean isReplyOnDiable() {
        return replyOnDiable;
    }

    public boolean istReplyOnFeedback() {
        return replyOnFeedback;
    }

    public boolean canShowAgentImage() {
        return showAgentImage;
    }

    public boolean needDeviceOptimization() {
        return needDeviceOptimization;
    }

    public boolean isFetchPaymentMethod() {
        return fetchPaymentMethod;
    }

    public boolean isAnnouncementCount() {
        return isAnnouncementCount;
    }

    public static class Builder {

        private AdditionalInfo additionalInfo = new AdditionalInfo();

        public Builder showAgentImage(boolean showAgentImage) {
            additionalInfo.showAgentImage = showAgentImage;
            return this;
        }

        public Builder showEmptyChatBtn(boolean showEmptyChatBtn) {
            additionalInfo.showEmptyChatBtn = showEmptyChatBtn;
            return this;
        }

        public Builder replyOnDiable(boolean replyOnDiable) {
            additionalInfo.replyOnDiable = replyOnDiable;
            return this;
        }

        public Builder replyOnFeedback(boolean replyOnFeedback) {
            additionalInfo.replyOnFeedback = replyOnFeedback;
            return this;
        }

        public Builder hasChannelPager(boolean hasChannelPager) {
            additionalInfo.hasChannelPager = hasChannelPager;
            return this;
        }

        public Builder hasLogout(boolean hasLogout) {
            additionalInfo.hasLogout = hasLogout;
            return this;
        }

        public Builder setEmptyChannelList(String emptyChannelList) {
            additionalInfo.emptyChannelList = emptyChannelList;
            return this;
        }

        public Builder hasCreateNewChat(boolean hasCreateNewChat) {
            additionalInfo.hasCreateNewChat = hasCreateNewChat;
            return this;
        }

        public Builder setCreateChatBtnText(String createChatBtnText) {
            additionalInfo.createChatBtnText = createChatBtnText;
            return this;
        }

        public Builder needDeviceOptimization(boolean needDeviceOptimization) {
            additionalInfo.needDeviceOptimization = needDeviceOptimization;
            return this;
        }

        public Builder prePaymentMethodFetched(boolean fetchPaymentMethod) {
            additionalInfo.fetchPaymentMethod = fetchPaymentMethod;
            return this;
        }

        public Builder isAnnouncementCount(boolean isAnnouncementCount) {
            additionalInfo.isAnnouncementCount = isAnnouncementCount;
            return this;
        }

        public AdditionalInfo build() {
            return additionalInfo;
        }
    }
}
