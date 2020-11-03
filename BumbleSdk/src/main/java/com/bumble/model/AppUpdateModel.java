package com.bumble.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AppUpdateModel {

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

        @SerializedName("latest_version")
        @Expose
        private int latestVersion;
        @SerializedName("critical_version")
        @Expose
        private int criticalVersion;
        @SerializedName("business_id")
        @Expose
        private long businessId;
        @SerializedName("messaage")
        @Expose
        private String messaage;
        @SerializedName("download_link")
        @Expose
        private String downloadLink;
        @SerializedName("soft_update_retry")
        @Expose
        private long softUpdateRetry;

        public int getLatestVersion() {
            return latestVersion;
        }

        public void setLatestVersion(int latestVersion) {
            this.latestVersion = latestVersion;
        }

        public int getCriticalVersion() {
            return criticalVersion;
        }

        public void setCriticalVersion(int criticalVersion) {
            this.criticalVersion = criticalVersion;
        }

        public long getBusinessId() {
            return businessId;
        }

        public void setBusinessId(long businessId) {
            this.businessId = businessId;
        }

        public String getMessaage() {
            return messaage;
        }

        public void setMessaage(String messaage) {
            this.messaage = messaage;
        }

        public String getDownloadLink() {
            return downloadLink;
        }

        public void setDownloadLink(String downloadLink) {
            this.downloadLink = downloadLink;
        }

        public long getSoftUpdateRetry() {
            return softUpdateRetry;
        }

        public void setSoftUpdateRetry(long softUpdateRetry) {
            this.softUpdateRetry = softUpdateRetry;
        }
    }

}
