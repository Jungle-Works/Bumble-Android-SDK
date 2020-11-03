package com.bumble;

import android.text.TextUtils;

import java.util.HashMap;

public class CaptureUserDataBumble {

    private String userUniqueKey = "";
    private String fullName = "";
    private String email = "";
    private String phoneNumber = "";
    private double latitude = 0;
    private double longitude = 0;
    private Long userId = -1l;
    private String enUserId = "";
    private String addressLine1 = "";
    private String addressLine2 = "";
    private String region = "";
    private String city = "";
    private String country = "";
    private String zipCode = "";
    private String lang = "";
    private boolean fetchBusinessLang;
    private HashMap<String, String> custom_attributes = new HashMap<>();



    public HashMap<String, String> getCustom_attributes() {
        return custom_attributes;
    }

    public void setCustom_attributes(HashMap<String, String> custom_attributes) {
        this.custom_attributes = custom_attributes;
    }

    public String getUserUniqueKey() {
        return userUniqueKey;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Long getUserId() {
        return userId;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public String getRegion() {
        return region;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getEnUserId() {
        return enUserId;
    }

    public void setEnUserId(String enUserId) {
        this.enUserId = enUserId;
    }

    public boolean isFetchBusinessLang() {
        return fetchBusinessLang;
    }

    public String getLang() {
        return lang;
    }

    public static class Builder {
        private CaptureUserDataBumble captureUserDataBumble = new CaptureUserDataBumble();

        public Builder userUniqueKey(String userUniqueKey) {
            captureUserDataBumble.userUniqueKey = userUniqueKey;
            return this;
        }

        public Builder fullName(String fullName) {
            captureUserDataBumble.fullName = fullName;
            return this;
        }

        public Builder email(String email) {
            captureUserDataBumble.email = email;
            return this;
        }

        public Builder phoneNumber(String phoneNumber) {
            captureUserDataBumble.phoneNumber = phoneNumber;
            return this;
        }

        public Builder addressLine1(String addressLine1) {
            captureUserDataBumble.addressLine1 = addressLine1;
            return this;
        }

        public Builder addressLine2(String addressLine2) {
            captureUserDataBumble.addressLine2 = addressLine2;
            return this;
        }

        public Builder region(String region) {
            captureUserDataBumble.region = region;
            return this;
        }

        public Builder city(String city) {
            captureUserDataBumble.city = city;
            return this;
        }

        public Builder country(String country) {
            captureUserDataBumble.country = country;
            return this;
        }

        public Builder zipCode(String zipCode) {
            captureUserDataBumble.zipCode = zipCode;
            return this;
        }

        public Builder latitude(double latitude) {
            captureUserDataBumble.latitude = latitude;
            return this;
        }

        public Builder longitude(double longitude) {
            captureUserDataBumble.longitude = longitude;
            return this;
        }

        public Builder fetchBusinessLang(boolean fetchBusinessLang) {
            captureUserDataBumble.fetchBusinessLang = fetchBusinessLang;
            return this;
        }

        public Builder setLang(String lang) {
            captureUserDataBumble.lang = lang;
            return this;
        }

        public Builder customAttributes(HashMap<String, String> customAttributes) {
            captureUserDataBumble.custom_attributes = customAttributes;
            return this;
        }



        public CaptureUserDataBumble build() {
            if(TextUtils.isEmpty(captureUserDataBumble.userUniqueKey))
                throw new IllegalStateException("User unique key can not be empty!");
            return captureUserDataBumble;
        }

    }
}
