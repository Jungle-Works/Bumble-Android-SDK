package com.test.bumble;

import android.os.Bundle;

public class DataClass {
    private static Bundle fuguBundle;
    public static Bundle getFuguBundle() {
        return fuguBundle;
    }

    public static void setFuguBundle(Bundle fuguBundle) {
        DataClass.fuguBundle = fuguBundle;
    }
}
