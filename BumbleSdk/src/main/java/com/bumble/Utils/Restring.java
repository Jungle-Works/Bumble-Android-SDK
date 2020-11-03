package com.bumble.Utils;

import android.content.Context;
import android.text.TextUtils;


import com.bumble.database.CommonData;

import java.util.Map;

/**
 * Created by gurmail on 2020-06-18.
 *
 * @author gurmail
 */
public class Restring {

    public static String getString(Context context, int key) {
        if (context == null) {
            return "";
        }
        String value = "";
        String resName = context.getResources().getResourceEntryName(key);
        Map<String, String> translationStrings = getStrings(context);

        if (translationStrings != null) {
            value = translationStrings.get(resName);
        }
        return TextUtils.isEmpty(value) ? context.getString(key) : value;
    }

    public static String getString(String key) {
        Map<String, String> translationStrings = getStrings(null);
        String value = "";
        if (translationStrings != null) {
            value = translationStrings.get(key);
        }
        return value;
    }


    public static void saveStrings(Translation translation) {
        CommonData.saveLangKeys(translation);
    }


    private static Map<String, String> getStrings(Context context) {
        Translation pojo = CommonData.getLangKeys();
        return pojo == null ? null : pojo.getMessages();
    }
}
