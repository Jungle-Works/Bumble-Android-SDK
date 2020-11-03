package com.bumble.model.promotional;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {
    @Expose
    @SerializedName("user_id")
    private int userId;
    @Expose
    @SerializedName("title")
    private String title;
    @Expose
    @SerializedName("description")
    private String description;
    @Expose
    @SerializedName("custom_attributes")
    private CustomAttributes customAttributes;
    @Expose
    @SerializedName("disable_reply")
    private int disableReply;
    @Expose
    @SerializedName("created_at")
    private String createdAt;
    @Expose
    @SerializedName("channel_id")
    private int channelId;

    private int showMore;

    public int getUserId() {
        return userId;
    }

    public Data(int userId) {
        this.userId = userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CustomAttributes getCustomAttributes() {
        return customAttributes;
    }

    public void setCustomAttributes(CustomAttributes customAttributes) {
        this.customAttributes = customAttributes;
    }

    public int getDisableReply() {
        return disableReply;
    }

    public void setDisableReply(int disableReply) {
        this.disableReply = disableReply;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public int getShowMore() {
        return showMore;
    }

    public void setShowMore(int showMore) {
        this.showMore = showMore;
    }
}
