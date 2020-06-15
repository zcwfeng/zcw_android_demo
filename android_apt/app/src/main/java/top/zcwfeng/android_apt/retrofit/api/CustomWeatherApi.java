package top.zcwfeng.android_apt.retrofit.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import top.zcwfeng.android_apt.retrofit.annotation.GET;
import top.zcwfeng.android_apt.retrofit.annotation.POST;

import top.zcwfeng.android_apt.retrofit.annotation.FIELD;
import top.zcwfeng.android_apt.retrofit.annotation.QUERY;

public interface CustomWeatherApi {
    @POST("/v3/weather/weatherInfo")
    Call<ResponseBody> postWeather(@FIELD("city") String city, @FIELD("key") String key);
    @GET("/v3/weather/weatherInfo")
    Call<ResponseBody> getWeather(@QUERY("city") String city, @QUERY("key") String key);

}
