package com.matloob.secureapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultModel {
    @SerializedName("result")
    @Expose
    private int result;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("error")
    @Expose
    private boolean error;

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public int getResult() {
        return result;
    }
}