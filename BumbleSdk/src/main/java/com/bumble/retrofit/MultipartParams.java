package com.bumble.retrofit;

import android.text.TextUtils;


import com.bumble.BumbleConfig;
import com.bumblesdk.BuildConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

import static com.bumble.constants.BumbleAppConstants.ANDROID_USER;
import static com.bumble.constants.BumbleAppConstants.APP_VERSION;
import static com.bumble.constants.BumbleAppConstants.DEVICE_TYPE;
import static com.bumble.constants.BumbleAppConstants.LANG;


/**
 * Created by cl-macmini-33 on 03/10/16.
 */

public class MultipartParams {
    HashMap<String, RequestBody> map = new HashMap<>();

    private MultipartParams(Builder builder, int sourceType) {
//        builder.map.put(APP_SOURCE_TYPE, RetrofitUtils.getRequestBodyFromString(String.valueOf(sourceType)));
        builder.map.put(APP_VERSION, RetrofitUtils.getRequestBodyFromString(String.valueOf(BuildConfig.VERSION_CODE)));
        builder.map.put(DEVICE_TYPE, RetrofitUtils.getRequestBodyFromString(String.valueOf(ANDROID_USER)));
        this.map = builder.map;
        if(!TextUtils.isEmpty(BumbleConfig.getInstance().getCurrentLanguage()))
            builder.map.put(LANG, RetrofitUtils.getRequestBodyFromString(BumbleConfig.getInstance().getCurrentLanguage()));
    }

    public HashMap<String, RequestBody> getMap() {
        return map;
    }


    public static class Builder {
        HashMap<String, RequestBody> map = new HashMap<>();

        public Builder() {
        }

        public Builder add(String key, Object value) {

            if (value == null || String.valueOf(value).isEmpty())
                return this;
            map.put(key, RetrofitUtils.getRequestBodyFromString(String.valueOf(value)));
            return this;
        }

        //for single file
        public Builder addFile(String key, File mFile) {
            if (mFile == null)
                return this;

            map.put(key + "\"; filename=\"" + mFile.getName(), RequestBody.create(MediaType.parse(RetrofitUtils.getMimeType(mFile)), mFile));
            return this;
        }

        // for multiple file with same key
        public Builder addArrayOfFiles(String key, ArrayList<File> mFileArrayList) {
            if (mFileArrayList == null || mFileArrayList.size() == 0)
                return this;

            for (int i = 0; i < mFileArrayList.size(); i++)
                if (mFileArrayList.get(i) != null)
                    map.put(key + "\"; filename=\"" + mFileArrayList.get(i).getName(), RequestBody.create(MediaType.parse(RetrofitUtils.getMimeType(mFileArrayList.get(i))), mFileArrayList.get(i)));
            return this;
        }


        public MultipartParams build() {
            return new MultipartParams(this, 1);
        }
        public MultipartParams build(int sourceType) {
            return new MultipartParams(this, 1);
        }

    }
}

