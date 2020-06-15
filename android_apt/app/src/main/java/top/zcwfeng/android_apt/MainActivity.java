package top.zcwfeng.android_apt;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import top.zcwfeng.android_apt.annotation.CLick;
import top.zcwfeng.android_apt.annotation.DavidAnnotation;
import top.zcwfeng.android_apt.annotation.IndectUtils;
import top.zcwfeng.android_apt.annotation.InjectView;
import top.zcwfeng.android_apt.bean.UserParceable;
import top.zcwfeng.android_apt.bean.UserSerializable;
import top.zcwfeng.android_apt.retrofit.CustomRetrofit;
import top.zcwfeng.android_apt.retrofit.api.CustomWeatherApi;
import top.zcwfeng.android_apt.retrofit.api.WeatherApi;

@DavidAnnotation(value = 1, david = "aligadou")
public class MainActivity extends AppCompatActivity {

    @InjectView(id = R.id.testAnnotation)
    TextView textView;
    private WeatherApi weatherApi;
    private CustomWeatherApi customWeatherApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IndectUtils.injectView(this);
        IndectUtils.injectClick(this);
        textView.setText("testllllll");


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://restapi.amap.com").build();
        weatherApi = retrofit.create(WeatherApi.class);

        CustomRetrofit customRetrofit = new CustomRetrofit.Builder()
                .baseUrl("https://restapi.amap.com").build();
        customWeatherApi = customRetrofit.create(CustomWeatherApi.class);

    }

    @CLick(R.id.testAnnotation)
    public void testAnnotationIntentClick(View view){
        Log.e("zcw::","ok Click");

        testAnnotationIntent();

    }

    public void get(View view) {
        Call<ResponseBody> call = weatherApi.getWeather("110101", "ae6c53e2186f33bbf240a12d80672d1b");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    ResponseBody body = response.body();
                    try {
                        String string = body.string();
                        Log.d("zcw::", "onResponse get: " + string);
                    } catch (IOException e) {
                        e.printStackTrace();

                    } finally {
                        body.close();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void post(View view) {
        Call<ResponseBody> call = weatherApi.postWeather("110101", "ae6c53e2186f33bbf240a12d80672d1b");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                if(response.isSuccessful()) {
                ResponseBody body = response.body();
                try {
                    String string = body.string();
                    Log.d("zcw::", "onResponse post: " + string);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    body.close();
                }
//                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void customGet(View view) {
        Call<ResponseBody> call = customWeatherApi.getWeather("110101", "ae6c53e2186f33bbf240a12d80672d1b");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    ResponseBody body = response.body();
                    try {
                        String string = body.string();
                        Log.d("zcw::", "onResponse get: " + string);
                    } catch (IOException e) {
                        e.printStackTrace();

                    } finally {
                        body.close();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void customPost(View view) {
        Call<ResponseBody> call = customWeatherApi.postWeather("110101", "ae6c53e2186f33bbf240a12d80672d1b");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                if(response.isSuccessful()) {
                ResponseBody body = response.body();
                try {
                    String string = body.string();
                    Log.d("zcw::", "onResponse post: " + string);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    body.close();
                }
//                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }


    void testAnnotationIntent() {
        IndectUtils.injectExtras(this);

        ArrayList<UserParceable> userParceablesList = new ArrayList<>();
        userParceablesList.add(new UserParceable("David"));
        Intent intent = new Intent(this, SecondActivity.class)
                .putExtra("name", "David")
                .putExtra("attr", "帅")
                .putExtra("arry", new int[]{1, 2, 3, 4, 5, 6})
                .putExtra("userParceable", new UserParceable("David"))
                .putExtra("userParceables", new UserParceable[]{new UserParceable("Merry")})
                .putExtra("users", new UserSerializable[]{new UserSerializable("Jack")})
                .putParcelableArrayListExtra("userParcelableList", userParceablesList);
        startActivity(intent);
    }


}