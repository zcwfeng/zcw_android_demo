package top.zcwfeng.base.customview.mvvm.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BaseCachedData<DATA> {
    @SerializedName("updateTimeInMillis")
    @Expose
    public long updateTimeInMillis;
    @SerializedName("data")
    @Expose
    public DATA data;
}