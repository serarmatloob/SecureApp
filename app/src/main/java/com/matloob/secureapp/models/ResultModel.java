package com.matloob.secureapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultModel {

@SerializedName("result")
@Expose
private int result;

public int getResult() {
return result;
}
}