package com.bumble;

import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.RoundRectShape;

/**
 * Created by bhavya on 01/08/17.
 */

public class HippoColorConfig {

    public int getHippoActionBarBg() {
        return Color.parseColor(hippoActionBarBg);
    }

    public int getFuguRunsOnColor() {
        return Color.parseColor("#627de3");
    }

    public int getHippoActionBarText() {
        return Color.parseColor(hippoActionBarText);
    }

    public int getHippoBgMessageYou() {
        return Color.parseColor(hippoBgMessageYou);
    }

    public int getHippoPrivateMsg(){
        return Color.parseColor(hippoBgPrivateMessageYou);
    }

    public int getHippoBgMessageFrom() {
        return Color.parseColor(hippoBgMessageFrom);
    }

    public int getHippoPrimaryTextMsgYou() {
        return Color.parseColor(hippoPrimaryTextMsgYou);
    }

    public int getHippoMessageRead() {
        return Color.parseColor(hippoMessageRead);
    }

    public int getHippoPrimaryTextMsgFrom() {
        return Color.parseColor(hippoPrimaryTextMsgFrom);
    }

    public int getHippoSecondaryTextMsgYou() {
        return Color.parseColor(hippoSecondaryTextMsgYou);
    }

    public int getHippoSecondaryTextMsgFrom() {
        return Color.parseColor(hippoSecondaryTextMsgFrom);
    }

    public int getHippoSecondaryTextMsgFromName() {
        return Color.parseColor(hippoSecondaryTextMsgFromName);
    }

    public int getHippoPrimaryTextMsgFromName() {
        return Color.parseColor(hippoPrimaryTextMsgFromName);
    }

    public int getHippoTextColorPrimary() {
        return Color.parseColor(hippoTextColorPrimary);
    }

    public int getHippoTextColorSecondary() {
        return Color.parseColor(hippoTextColorSecondary);
    }

    public int getHippoListTextColorPrimary() {
        return Color.parseColor(hippoListTextColorPrimary);
    }

    public int getHippoListTextColorSecondary() {
        return Color.parseColor(hippoListTextColorSecondary);
    }

    public int getHippoChannelDateText() {
        return Color.parseColor(hippoChannelDateText);
    }

    public int getHippoChatBg() {
        return Color.parseColor(hippoChatBg);
    }

    public int getHippoBorderColor() {
        return Color.parseColor(hippoBorderColor);
    }

    public int getHippoChatDateText() {
        return Color.parseColor(hippoChatDateText);
    }

    public int getHippoThemeColorPrimary() {
        return Color.parseColor(hippoThemeColorPrimary);
    }

    public int getHippoThemeColorSecondary() {
        return Color.parseColor(hippoThemeColorSecondary);
    }

    public int getHippoTypeMessageBg() {
        return Color.parseColor(hippoTypeMessageBg);
    }

    public int getHippoTypeMessageHint() {
        return Color.parseColor(hippoTypeMessageHint);
    }

    public int getHippoTypeMessageText() {
        return Color.parseColor(hippoTypeMessageText);
    }

    public int getHippoChannelBg() {
        return Color.parseColor(hippoChannelBg);
    }

    public int getHippoChannelItemBgPressed() {
        return Color.parseColor(hippoChannelItemBgPressed);
    }

    public int getHippoChannelItemBg() {
        return Color.parseColor(hippoChannelItemBg);
    }

    public int getHippoTabTextColor() {
        return Color.parseColor(hippoTabTextColor);
    }

    public int getHippoTabSelectedTextColor() {
        return Color.parseColor(hippoTabSelectedTextColor);
    }

    public int getHippoSelectedTabIndicatorColor() {
        return Color.parseColor(hippoSelectedTabIndicatorColor);
    }

    public int getHippoFaqDescription() {
        return Color.parseColor(hippoFaqDescription);
    }

    public int getHippoNotConnected() {
        return Color.parseColor(hippoNotConnected);
    }

    public int getHippoConnected() {
        return Color.parseColor(hippoConnected);
    }

    public int getHippoSourceType() {
        return Color.parseColor(hippoSourceType);
    }

    public int getHippoStatusBar() {
        return Color.parseColor(hippoStatusBar);
    }

    public int getHippoUrlLinkText() {
        return Color.parseColor(hippoUrlLinkText);
    }



    public int getHippoProfileTitle() {
        return Color.parseColor(hippoProfileTitle);
    }

    public int getHippoPaymentTitle() {
        return Color.parseColor(hippoPaymentTitle);
    }

    public int getHippoPaymentBg() {
        return Color.parseColor(hippoPaymentBg);
    }

    public int getHippoPaymentDescription() {
        return Color.parseColor(hippoPaymentDescription);
    }

    public int getHippoPaymentAmount() {
        return Color.parseColor(hippoPaymentAmount);
    }

    public int getHippoProfileValue() {
        return Color.parseColor(hippoProfileValue);
    }

    public int getHippoPromotionalTitle() {
        return Color.parseColor(hippoPromotionalTitle);
    }

    public int getHippoPromotionalMessage() {
        return Color.parseColor(hippoPromotionalMessage);
    }

    public int getHippoPromotionalTime() {
        return Color.parseColor(hippoPromotionalTime);
    }

    public int getHippoBotMessageBg() {
        return Color.parseColor(hippoBotMessageBg);
    }

    public int getHippoBotMessageText() {
        return Color.parseColor(hippoBotMessageText);
    }

    public int getHippoBotMessageBorder() {
        return Color.parseColor(hippoBotMessageBorder);
    }

    public int getHippoChannelTimeText() {
        return Color.parseColor(hippoChannelTime);
    }

    public int getHippoSendBtnBg() {
        return Color.parseColor(hippoSendBtnBg);
    }

    public int getHippoBotConcentBtnBg() {
        return Color.parseColor(hippoBotConcentBtnBg);
    }

    public int getHippoBotConcentText() {
        return Color.parseColor(hippoBotConcentText);
    }

    public int getHippoChannelReadMessage() {
        return Color.parseColor(hippoChannelReadMessage);
    }

    public int getHippoChannelReadTime() {
        return Color.parseColor(hippoChannelReadTime);
    }


    public int getHippoToolbarHighlighted() {
        return Color.parseColor(hippoToolbarHighlighted);
    }

    public int getHippoToolbardisable() {
        return Color.parseColor(hippoToolbardisable);
    }



    private String hippoActionBarBg = "#ffffff";                // Toolbar BG
    private String hippoActionBarText = "#000000";              // Toolbar text message color
    private String hippoStatusBar = "#ffffff";                  // status bar color
    private String hippoBgMessageFrom = "#ffffff";              // depricated
    private String hippoBotConcentBtnBg = "#ffffff";            // button message bg
    private String hippoBotConcentText = "#000000";             // button message text
    private String hippoBgPrivateMessageYou = "#FEF8E3";        // not used
    private String hippoBgMessageYou = "#f2f5f8";               // send message BG
    private String hippoPrimaryTextMsgYou = "#151515";          // sent message text color
    private String hippoSecondaryTextMsgYou = "#7c7c7d";        // sent time message color
    private String hippoMessageRead = "#627de3";                // read message bg color
    private String hippoPrimaryTextMsgFrom = "#000000";         // received message color
    private String hippoSecondaryTextMsgFrom = "#8b8c8d";       // received time message color
    private String hippoPrimaryTextMsgFromName = "#535353";     // sender name text color
    private String hippoSecondaryTextMsgFromName = "#535353";   // from name color
    private String hippoTabTextColor = "#000000";               // Disable text color of a tab
    private String hippoTabSelectedTextColor = "#000000";       // Selected text color of a tab
    private String hippoSelectedTabIndicatorColor = "#5b9f0d";  // selected tab underline color
    private String hippoTextColorPrimary = "#2c2333";           // sent message color
    private String hippoTextColorSecondary = "#8e8e8e";         // received message color
    private String hippoChatBg = "#f8f9ff";                     // chat BG color
    private String hippoBorderColor = "#dce0e6";                // chat screen Date boarder stoke
    private String hippoChatDateText = "#8b98a5";               // chat screen Date color
    private String hippoThemeColorPrimary = "#627de3";          // SDK primary theme
    private String hippoThemeColorSecondary = "#6cc64d";        // SDK secondary theme
    private String hippoTypeMessageBg = "#ffffff";              // not in used
    private String hippoTypeMessageText = "#2c2333";            // chat screen edittext color
    private String hippoTypeMessageHint = "#8e8e8e";            // chat screen edittext hit color
    private String hippoChannelBg = "#ffffff";                  // conversation screen BG
    private String hippoChannelItemBg = "#ffffff";              // depricated
    private String hippoChannelItemBgPressed = "#ffd2d1d1";     // depricated
    private String hippoFaqDescription = "#858585";             // for support screen
    private String hippoNotConnected = "#FF0000";               // not in used
    private String hippoConnected = "#00AA00";                  // not in used
    private String hippoSourceType = "#2296ff";                 // Integration source color
    private String hippoUrlLinkText = "#000000";                //Url messages color
    private String hippoSendBtnBg = "#5b9e0e";                  // Chat screen attachement and send button color
    private String hippoProfileTitle = "#2c2333";               // Profile screen title text color
    private String hippoProfileValue = "#8e8e8e";               // Profile screen description text color
    private String hippoListTextColorPrimary = "#000000";       //Channel list title text color
    private String hippoListTextColorSecondary = "#4a4a4a";     // Channel list message text color
    private String hippoChannelReadMessage = "#aebdc0";         // channel message text color if unread count 0
    private String hippoChannelDateText = "#6e6e6e";            // channel date/time color
    private String hippoChannelReadTime = "#a0afb2";            // channel time text color if unread count 0
    private String hippoPaymentBg = "#FFFFFF";                  // Payment BG color
    private String hippoPaymentTitle = "#000000";               // Payment title text color
    private String hippoPaymentDescription = "#8e8e8e";         // payment description text color
    private String hippoPaymentAmount = "#2c2333";              // payment amount text color

    private String hippoToolbarHighlighted = "#000000"; // Tab bar selected text color and
    private String hippoToolbardisable = "#000000"; //nDisable tabview text color
    private String hippoFloatingBtnBg = "#5b9f0d"; // consult now btn BG
    private String hippoFloatingBtnText = "#ffffff"; // consult now btn text color

    // not using
    private String hippoPromotionalTitle = "#2c2333";
    private String hippoPromotionalMessage = "#2c2333";
    private String hippoPromotionalTime = "#8e8e8e";
    private String hippoChannelTime = "#b3bec9";
    private String hippoBotMessageBg = "#E9EFFD";
    private String hippoBotMessageText = "#2c2333";
    private String hippoBotMessageBorder = "#2c2333";

    public int getHippoFloatingBtnBg() {
        return Color.parseColor(hippoFloatingBtnBg);
    }

    public int getHippoFloatingBtnText() {
        return Color.parseColor(hippoFloatingBtnText);
    }

    public static class Builder {
        private HippoColorConfig hippoColorConfig = new HippoColorConfig();

        public Builder hippoFloatingBtnBg(String hippoFloatingBtnBg) {
            hippoColorConfig.hippoFloatingBtnBg = hippoFloatingBtnBg;
            return this;
        }

        public Builder hippoFloatingBtnText(String hippoFloatingBtnText) {
            hippoColorConfig.hippoFloatingBtnText = hippoFloatingBtnText;
            return this;
        }

        public Builder hippoToolbarHighlighted(String hippoToolbarHighlighted) {
            hippoColorConfig.hippoToolbarHighlighted = hippoToolbarHighlighted;
            return this;
        }

        public Builder hippoToolbardisable(String hippoToolbardisable) {
            hippoColorConfig.hippoToolbardisable = hippoToolbardisable;
            return this;
        }

        public Builder hippoPaymentBg(String hippoPaymentBg) {
            hippoColorConfig.hippoPaymentBg = hippoPaymentBg;
            return this;
        }

        public Builder hippoPaymentDescription(String hippoPaymentDescription) {
            hippoColorConfig.hippoPaymentDescription = hippoPaymentDescription;
            return this;
        }

        public Builder hippoProfileTitle(String hippoProfileTitle) {
            hippoColorConfig.hippoProfileTitle = hippoProfileTitle;
            return this;
        }

        public Builder hippoPaymentAmount(String hippoPaymentAmount) {
            hippoColorConfig.hippoPaymentAmount = hippoPaymentAmount;
            return this;
        }

        public Builder hippoPaymentTitle(String hippoPaymentTitle) {
            hippoColorConfig.hippoPaymentTitle = hippoPaymentTitle;
            return this;
        }

        public Builder hippoChannelUnReadMessage(String hippoChannelReadMessage) {
            hippoColorConfig.hippoChannelReadMessage = hippoChannelReadMessage;
            return this;
        }

        public Builder hippoChannelReadTime(String hippoChannelReadTime) {
            hippoColorConfig.hippoChannelReadTime = hippoChannelReadTime;
            return this;
        }

        public Builder hippoChannelTime(String hippoChannelTime) {
            hippoColorConfig.hippoChannelTime = hippoChannelTime;
            return this;
        }

        public Builder hippoProfileValue(String hippoProfileValue) {
            hippoColorConfig.hippoProfileValue = hippoProfileValue;
            return this;
        }

        public Builder hippoPromotionalTitle(String hippoPromotionalTitle) {
            hippoColorConfig.hippoPromotionalTitle = hippoPromotionalTitle;
            return this;
        }

        public Builder hippoPromotionalTime(String hippoPromotionalTime) {
            hippoColorConfig.hippoPromotionalTime = hippoPromotionalTime;
            return this;
        }

        public Builder hippoPromotionalMessage(String hippoPromotionalMessage) {
            hippoColorConfig.hippoPromotionalMessage = hippoPromotionalMessage;
            return this;
        }

        public Builder hippoBotMessageBorder(String hippoBotMessageBorder) {
            hippoColorConfig.hippoBotMessageBorder = hippoBotMessageBorder;
            return this;
        }

        public Builder hippoBotMessageBg(String hippoBotMessageBg) {
            hippoColorConfig.hippoBotMessageBg = hippoBotMessageBg;
            return this;
        }
        public Builder hippoBotMessageText(String hippoBotMessageText) {
            hippoColorConfig.hippoBotMessageText = hippoBotMessageText;
            return this;
        }

        public Builder hippoStatusBar(String hippoStatusBar) {
            hippoColorConfig.hippoStatusBar = hippoStatusBar;
            return this;
        }

        public Builder hippoUrlLinkText(String hippoUrlLinkText) {
            hippoColorConfig.hippoUrlLinkText = hippoUrlLinkText;
            return this;
        }

        public Builder hippoSourceType(String hippoSourceType) {
            hippoColorConfig.hippoSourceType = hippoSourceType;
            return this;
        }

        public Builder hippoActionBarBg(String hippoActionBarBg) {
            hippoColorConfig.hippoActionBarBg = hippoActionBarBg;
            return this;
        }

        public Builder hippoActionBarText(String hippoActionBarText) {
            hippoColorConfig.hippoActionBarText = hippoActionBarText;
            return this;
        }

        public Builder hippoBgMessageYou(String hippoBgMessageYou) {
            hippoColorConfig.hippoBgMessageYou = hippoBgMessageYou;
            return this;
        }

        public Builder hippoBgMessageFrom(String hippoBgMessageFrom) {
            hippoColorConfig.hippoBgMessageFrom = hippoBgMessageFrom;
            return this;
        }

        public Builder hippoPrimaryTextMsgYou(String hippoPrimaryTextMsgYou) {
            hippoColorConfig.hippoPrimaryTextMsgYou = hippoPrimaryTextMsgYou;
            return this;
        }

        public Builder hippoMessageRead(String hippoMessageRead) {
            hippoColorConfig.hippoMessageRead = hippoMessageRead;
            return this;
        }

        public Builder hippoPrimaryTextMsgFrom(String hippoPrimaryTextMsgFrom) {
            hippoColorConfig.hippoPrimaryTextMsgFrom = hippoPrimaryTextMsgFrom;
            return this;
        }

        public Builder hippoSecondaryTextMsgYou(String hippoSecondaryTextMsgYou) {
            hippoColorConfig.hippoSecondaryTextMsgYou = hippoSecondaryTextMsgYou;
            return this;
        }

        public Builder hippoSecondaryTextMsgFrom(String hippoSecondaryTextMsgFrom) {
            hippoColorConfig.hippoSecondaryTextMsgFrom = hippoSecondaryTextMsgFrom;
            return this;
        }

        public Builder hippoPrimaryTextMsgFromName(String hippoPrimaryTextMsgFromName) {
            hippoColorConfig.hippoPrimaryTextMsgFromName = hippoPrimaryTextMsgFromName;
            return this;
        }

        public Builder hippoSecondaryTextMsgFromName(String hippoSecondaryTextMsgFromName) {
            hippoColorConfig.hippoSecondaryTextMsgFromName = hippoSecondaryTextMsgFromName;
            return this;
        }

        public Builder hippoTextColorPrimary(String hippoTextColorPrimary) {
            hippoColorConfig.hippoTextColorPrimary = hippoTextColorPrimary;
            return this;
        }

        public Builder hippoTextColorSecondary(String hippoTextColorSecondary) {
            hippoColorConfig.hippoTextColorSecondary = hippoTextColorSecondary;
            return this;
        }

        public Builder hippoListTextColorPrimary(String hippoListTextColorPrimary) {
            hippoColorConfig.hippoListTextColorPrimary = hippoListTextColorPrimary;
            return this;
        }

        public Builder hippoListTextColorSecondary(String hippoListTextColorSecondary) {
            hippoColorConfig.hippoListTextColorSecondary = hippoListTextColorSecondary;
            return this;
        }

        public Builder hippoChannelDateText(String hippoChannelDateText) {
            hippoColorConfig.hippoChannelDateText = hippoChannelDateText;
            return this;
        }

        public Builder hippoChatBg(String hippoChatBg) {
            hippoColorConfig.hippoChatBg = hippoChatBg;
            return this;
        }

        public Builder hippoBorderColor(String hippoBorderColor) {
            hippoColorConfig.hippoBorderColor = hippoBorderColor;
            return this;
        }

        public Builder hippoChatDateText(String hippoChatDateText) {
            hippoColorConfig.hippoChatDateText = hippoChatDateText;
            return this;
        }

        public Builder hippoThemeColorPrimary(String hippoThemeColorPrimary) {
            hippoColorConfig.hippoThemeColorPrimary = hippoThemeColorPrimary;
            return this;
        }

        public Builder hippoThemeColorSecondary(String hippoThemeColorSecondary) {
            hippoColorConfig.hippoThemeColorSecondary = hippoThemeColorSecondary;
            return this;
        }

        public Builder hippoTypeMessageBg(String hippoTypeMessageBg) {
            hippoColorConfig.hippoTypeMessageBg = hippoTypeMessageBg;
            return this;
        }

        public Builder hippoTypeMessageHint(String hippoTypeMessageHint) {
            hippoColorConfig.hippoTypeMessageHint = hippoTypeMessageHint;
            return this;
        }

        public Builder hippoTypeMessageText(String hippoTypeMessageText) {
            hippoColorConfig.hippoTypeMessageText = hippoTypeMessageText;
            return this;
        }

        public Builder hippoChannelBg(String hippoChannelBg) {
            hippoColorConfig.hippoChannelBg = hippoChannelBg;
            return this;
        }

        public Builder hippoChannelItemBgPressed(String hippoChannelItemBgPressed) {
            hippoColorConfig.hippoChannelItemBgPressed = hippoChannelItemBgPressed;
            return this;
        }

        public Builder hippoChannelItemBg(String hippoChannelItemBg) {
            hippoColorConfig.hippoChannelItemBg = hippoChannelItemBg;
            return this;
        }

        public Builder hippoTabTextColor(String hippoTabTextColor) {
            hippoColorConfig.hippoTabTextColor = hippoTabTextColor;
            return this;
        }

        public Builder hippoTabSelectedTextColor(String hippoTabSelectedTextColor) {
            hippoColorConfig.hippoTabSelectedTextColor = hippoTabSelectedTextColor;
            return this;
        }

        public Builder hippoSelectedTabIndicatorColor(String hippoSelectedTabIndicatorColor) {
            hippoColorConfig.hippoSelectedTabIndicatorColor = hippoSelectedTabIndicatorColor;
            return this;
        }

        public Builder hippoFaqDescription(String hippoFaqDescription) {
            hippoColorConfig.hippoFaqDescription = hippoFaqDescription;
            return this;
        }

        public Builder hippoBgPrivateMessageYou(String hippoBgPrivateMessageYou) {
            hippoColorConfig.hippoBgPrivateMessageYou = hippoBgPrivateMessageYou;
            return this;
        }

        public Builder hippoNotConnected(String hippoNotConnected) {
            hippoColorConfig.hippoNotConnected = hippoNotConnected;
            return this;
        }

        public Builder hippoConnected(String hippoConnected) {
            hippoColorConfig.hippoConnected = hippoConnected;
            return this;
        }

        public Builder hippoSendBtnBg(String hippoSendBtnBg) {
            hippoColorConfig.hippoSendBtnBg = hippoSendBtnBg;
            return this;
        }

        public Builder hippoBotConcentBtnBg(String hippoBotConcentBtnBg) {
            hippoColorConfig.hippoBotConcentBtnBg = hippoBotConcentBtnBg;
            return this;
        }

        public Builder hippoBotConcentText(String hippoBotConcentText) {
            hippoColorConfig.hippoBotConcentText = hippoBotConcentText;
            return this;
        }

        public HippoColorConfig build() {
            return hippoColorConfig;
        }

    }

    public static StateListDrawable makeSelector(int color, int colorPressed) {
        StateListDrawable res = new StateListDrawable();
        res.addState(new int[]{android.R.attr.state_pressed}, roundedBackground(0, colorPressed, false));
        res.addState(new int[]{}, roundedBackground(0, color, false));
        return res;
    }

    public static StateListDrawable makeRoundedSelector(int color) {
        return makeRoundedSelector(color, 150);
    }
    public static StateListDrawable makeRoundedSelector(int color, float radius) {
        StateListDrawable res = new StateListDrawable();
        res.addState(new int[]{android.R.attr.state_pressed}, roundedBackground(radius, color, true));
        res.addState(new int[]{}, roundedBackground(radius, color, false));
        return res;
    }

    private static ShapeDrawable roundedBackground(float radius, int color, boolean isPressed) {
        ShapeDrawable footerBackground = new ShapeDrawable();
        float[] radii = new float[8];
        radii[0] = radius;
        radii[1] = radius;

        radii[2] = radius;
        radii[3] = radius;

        radii[4] = radius;
        radii[5] = radius;

        radii[6] = radius;
        radii[7] = radius;

        footerBackground.setShape(new RoundRectShape(radii, null, null));
        footerBackground.getPaint().setColor(color);
        if (isPressed)
            footerBackground.setAlpha(250);

        return footerBackground;
    }

}
