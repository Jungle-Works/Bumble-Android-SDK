package com.bumble.model;

import android.content.Context;
import android.content.pm.PackageManager;

import com.google.gson.annotations.SerializedName;

public class FuguDeviceDetails {
    private Context context;
    @SerializedName("os")
    private String operating_system = "Android";
    @SerializedName("model")
    private String model = android.os.Build.MODEL;
    @SerializedName("manufacturer")
    private String manufacturer = android.os.Build.MANUFACTURER;
    @SerializedName("app_version")
    private int app_version = 0;
    @SerializedName("os_version")
    private String os_version = android.os.Build.VERSION.RELEASE;

    public FuguDeviceDetails(int appVersion) {
        this.app_version = appVersion;
    }

    public FuguDeviceDetails getDeviceDetails() throws PackageManager.NameNotFoundException {
        return new FuguDeviceDetails(app_version);
    }
}