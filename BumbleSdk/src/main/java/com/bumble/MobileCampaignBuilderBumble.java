package com.bumble;

/**
 * Created by gurmail on 2019-12-23.
 *
 * @author gurmail
 */
public class MobileCampaignBuilderBumble {

    private String mTitle;
    private String parseFormat;
    private NotificationListener listener;
    private boolean hasCampaignPager;
    private boolean closeActivityOnClick;
    private boolean closeOnlyDeepLink;
    private String notificationTitle;
    private String emptyNotificationText;

    public String getmTitle() {
        return mTitle;
    }

    public String getParseFormat() {
        return parseFormat;
    }

    public NotificationListener getListener() {
        return listener;
    }

    public boolean isCloseActivityOnClick() {
        return closeActivityOnClick;
    }

    public boolean isCloseOnlyDeepLink() {
        return closeOnlyDeepLink;
    }

    public boolean hasCampaignPager() {
        return hasCampaignPager;
    }

    public String getNotificationTitle() {
        return notificationTitle;
    }

    public String getEmptyNotificationText() {
        return emptyNotificationText;
    }

    public static class Builder {

        private String mTitle;
        private String parseFormat;
        private NotificationListener listener;
        private boolean closeActivityOnClick;
        private boolean closeOnlyDeepLink;
        private boolean hasCampaignPager;
        private String notificationTitle;
        private String emptyNotificationText;

        public Builder setTitle(String mTitle) {
            this.mTitle = mTitle;
            return this;
        }

        public Builder setParseFormat(String parseFormat) {
            this.parseFormat = parseFormat;
            return this;
        }

        public Builder setListener(NotificationListener listener) {
            this.listener = listener;
            return this;
        }

        public Builder isCloseActivityOnClick(boolean closeActivityOnClick) {
            this.closeActivityOnClick = closeActivityOnClick;
            return this;
        }

        public Builder isCloseOnlyDeepLink(boolean closeOnlyDeepLink) {
            this.closeOnlyDeepLink = closeOnlyDeepLink;
            return this;
        }

        public Builder hasCampaignPager(boolean hasCampaignPager) {
            this.hasCampaignPager = hasCampaignPager;
            return this;
        }

        public Builder setNotificationTitle(String notificationTitle) {
            this.notificationTitle = notificationTitle;
            return this;
        }

        public Builder setEmptyNotificationText(String emptyNotificationText) {
            this.emptyNotificationText = emptyNotificationText;
            return this;
        }

        public MobileCampaignBuilderBumble build() {
            return new MobileCampaignBuilderBumble(this);
        }
    }

    private MobileCampaignBuilderBumble(Builder builder) {
        this.mTitle = builder.mTitle;
        this.parseFormat = builder.parseFormat;
        this.listener = builder.listener;
        this.closeActivityOnClick = builder.closeActivityOnClick;
        this.closeOnlyDeepLink = builder.closeOnlyDeepLink;
        this.hasCampaignPager = builder.hasCampaignPager;
        this.notificationTitle = builder.notificationTitle;
        this.emptyNotificationText = builder.emptyNotificationText;
    }
}
