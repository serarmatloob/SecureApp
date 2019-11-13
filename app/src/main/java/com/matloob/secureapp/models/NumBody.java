package com.matloob.secureapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Serar Matloob on 11/12/2019.
 */
public class NumBody {
    @SerializedName("num1")
    @Expose
    private int num1;
    @SerializedName("num2")
    @Expose
    private int num2;

    public NumBody(int num1, int num2) {
        this.num1 = num1;
        this.num2 = num2;
    }
}
