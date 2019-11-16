package com.matloob.secureapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Serar Matloob on 11/12/2019.
 */
public class ComputeRequestBody {
    @SerializedName("safety")
    @Expose
    private String safety;
    @SerializedName("num1")
    @Expose
    private int num1;
    @SerializedName("num2")
    @Expose
    private int num2;

    /**
     * Constructor to create new compute request body
     * @param safety a {@link String} of Jws Safetynet result
     * @param num1 a {@link Integer} of first number
     * @param num2 a {@link Integer} of second number
     */
    public ComputeRequestBody(String safety, int num1, int num2) {
        this.safety = safety;
        this.num1 = num1;
        this.num2 = num2;
    }
}
