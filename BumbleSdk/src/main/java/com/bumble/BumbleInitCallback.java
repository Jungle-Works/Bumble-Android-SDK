package com.bumble;

public interface BumbleInitCallback {

    void onPutUserResponse();

    void hasData();

    void onErrorResponse();
}