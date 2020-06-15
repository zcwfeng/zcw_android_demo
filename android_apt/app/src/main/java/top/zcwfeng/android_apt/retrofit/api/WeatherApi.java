package top.zcwfeng.android_apt.retrofit.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface WeatherApi {
    @POST("/v3/weather/weatherInfo")
    @FormUrlEncoded
    Call<ResponseBody> postWeather(@Field("city") String city, @Field("key") String key);
    @GET("/v3/weather/weatherInfo")
    Call<ResponseBody> getWeather(@Query("city") String city, @Query("key") String key);

}
