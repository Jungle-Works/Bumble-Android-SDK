package com.bumble.model;

public class UnreadCountModel {

    private Long channel;
    private Long labelId;
    private int count;

    public Long getLabelId() {
        return labelId;
    }

    public void setLabelId(Long labelId) {
        this.labelId = labelId;
    }

    public Long getChannel() {
        return channel;
    }

    public void setChannel(Long channel) {
        this.channel = channel;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public UnreadCountModel(Long channel, Long labelId, int count) {
        this.channel = channel;
        this.labelId = labelId;
        this.count = count;
    }

    public UnreadCountModel(Long channel) {
        this.channel = channel;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof UnreadCountModel && ((UnreadCountModel)obj).getChannel().equals(getChannel());
    }
}