package com.bumble.retrofit;

import android.text.TextUtils;


import com.bumble.BumbleConfig;

import org.apache.http.BuildConfig;

import java.util.HashMap;

import static com.bumble.constants.BumbleAppConstants.ANDROID_USER;
import static com.bumble.constants.BumbleAppConstants.APP_VERSION;
import static com.bumble.constants.BumbleAppConstants.DEVICE_TYPE;
import static com.bumble.constants.BumbleAppConstants.LANG;


/**
 * Created by cl-macmini-33 on 27/09/16.
 */

public class CommonParams {
    HashMap<String, Object> map = new HashMap<>();

    private CommonParams(Builder builder, int sourceType) {
//        builder.map.put(APP_SOURCE_TYPE, String.valueOf(sourceType));
        builder.map.put(APP_VERSION, BuildConfig.VERSION_CODE);
        builder.map.put(DEVICE_TYPE, ANDROID_USER);
        String lang = BumbleConfig.getInstance().getCurrentLanguage();
        if(!TextUtils.isEmpty(lang))
            builder.map.put(LANG, lang);
        this.map = builder.map;
    }

    private CommonParams(Builder builder, int sourceType, String lang) {
//        builder.map.put(APP_SOURCE_TYPE, String.valueOf(sourceType));
        builder.map.put(APP_VERSION, BuildConfig.VERSION_CODE);
        builder.map.put(DEVICE_TYPE, ANDROID_USER);
        builder.map.put(LANG, lang);
        this.map = builder.map;
    }

    public HashMap<String, Object> getMap() {
        return map;
    }


    public static class Builder {
        HashMap<String, Object> map = new HashMap<>();

        public Builder() {
        }

        public Builder add(String key, Object value) {
            map.put(key, String.valueOf(value));
            return this;
        }

        public Builder addAll(HashMap<String, Object> objectHashMap) {
            map.putAll(objectHashMap);
            return this;
        }
        /**
         * Temp fix for future use of common key inside CommonParams constr.
         * @param map
         * @return
         */
        public Builder putMap(HashMap<String, Object> map) {
            this.map = map;
            return this;
        }

        public CommonParams build() {
            return new CommonParams(this, 1);
        }

        public CommonParams build(int sourceType) {
            return new CommonParams(this, 1);
        }

        public CommonParams build(String lang) {
            return new CommonParams(this, 1, lang);
        }

    }
}


