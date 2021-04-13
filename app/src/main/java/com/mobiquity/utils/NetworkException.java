package com.mobiquity.utils;


public class NetworkException extends RuntimeException {

    public final int mErrorCode;


    public String message;
    public NetworkException(int errorCode) {
        mErrorCode = errorCode;
    }
    public NetworkException(String message, int mErrorCode) {
        super(message);
        this.mErrorCode = mErrorCode;
    }
    public boolean shouldRetry() {
        return mErrorCode < 400 || mErrorCode > 499;
    }

    public String getNetworkMessage(){
        return message;
    }
}
