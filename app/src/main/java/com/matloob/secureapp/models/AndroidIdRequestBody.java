package com.matloob.secureapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AndroidIdRequestBody {
    @SerializedName("googleAndroidId")
    @Expose
    private String googleAndroidId;

    public AndroidIdRequestBody(String googleAndroidId) {
        this.googleAndroidId = googleAndroidId;
    }
}