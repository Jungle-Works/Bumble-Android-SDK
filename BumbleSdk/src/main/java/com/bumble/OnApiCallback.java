package com.bumble;

public interface OnApiCallback {

    public void onSucess();

    public void onFailure(String errorMessage);

    public void onProcessing();
}