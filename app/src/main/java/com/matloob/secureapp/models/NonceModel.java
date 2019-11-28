package com.matloob.secureapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NonceModel {
    @SerializedName("nonce")
    @Expose
    private String nonce;

    public String getNonce() {
        return nonce;
    }
}