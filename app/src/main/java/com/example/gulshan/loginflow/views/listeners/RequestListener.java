package com.example.gulshan.loginflow.views.listeners;

/**
 * Created by gulshan on 31/1/16.
 */
public interface RequestListener {

    public void onRequestStarted();

    public void onRequestCompleted(Object responseObject);

    public void onRequestError(final int errorCode, final String message);
}
