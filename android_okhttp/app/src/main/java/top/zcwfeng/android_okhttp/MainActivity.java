package top.zcwfeng.android_okhttp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private TextView mTv;
    private ImageView mDownloadImg;
    private String mBaseUrl = "http://192.168.3.3:8080/okhttp_testserver/";
    OkHttpClient okHttpClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTv = findViewById(R.id.tv);
        mDownloadImg = findViewById(R.id.download_img);
        okHttpClient = new OkHttpClient.Builder().cookieJar(new CookiesManager()).build();

    }

    public void doPost(View view) {
        //1.拿到OkhttpClient对象
        //2.构建Request，formBody对象
        //3.执行post请求
        FormBody formBody = new FormBody.Builder().add("username","zcw")
                .add("password","12345678").build();
        Request request = new Request.Builder()
                .url(mBaseUrl + "login")
                .post(formBody).build();

        executeRequest(request);

    }

    public void doPostString(View view) {
        //1.拿到OkhttpClient对象
        //2.构建Request，formBody对象
        //3.执行post请求
        RequestBody body = RequestBody.create("{\"username\":\"zcwfeng\",\"password\":\"123\"}", MediaType.parse("text/plain;charset=UTF-8"));
        Request request = new Request.Builder()
                .url(mBaseUrl + "postString")
                .post(body).build();

        executeRequest(request);

    }

    public void downloadImg(View view){
        Request request = new Request.Builder()
                .get()
                .url(mBaseUrl + "files/exception.png")
                .build();

        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                L.e("onFailure:" + e.getMessage());
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                L.e("onResponse:");
                InputStream in = response.body().byteStream();
//                BitmapFactory.Options
                Bitmap bitmap =  BitmapFactory.decodeStream(in);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mDownloadImg.setImageBitmap(bitmap);
//                        in.mark();
//                        in.reset();
                    }
                });

            }
        });

    }

    public void doPostFile(View view) {
        File file = new File(getExternalCacheDir(),"exception.png");
        if(!file.exists()) {
            L.e(file.getAbsolutePath() + " not found file");
            return;
        }
        RequestBody body = RequestBody.create(file, MediaType.parse("application/octet-stream"));
        Request request = new Request.Builder()
                .url(mBaseUrl + "postFile")
                .post(body).build();

        executeRequest(request);
    }

    public void download(View view) {
        Request request = new Request.Builder()
                .get()
                .url(mBaseUrl + "files/exception.png")
                .build();

        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                L.e("onFailure:" + e.getMessage());
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                L.e("onResponse:");

                long total = response.body().contentLength();
                long sum = 0;
                InputStream in = response.body().byteStream();
                File file = new File(getExternalCacheDir(),"zcwfeng.png");
                FileOutputStream fo = new FileOutputStream(file);
                int len;
                byte[] buf = new byte[128];
                while((len = in.read(buf))!=-1) {
                    fo.write(buf,0,len);

                    sum += len;

                    L.e(sum + "/" + total);

                    long finalSum = sum;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mTv.setText(finalSum + "/" + total);
                        }
                    });

                }
                fo.flush();
                fo.close();
                in.close();
                L.e("download success!");

            }
        });
    }

    public void upload(View view) {
        File file = new File(getExternalCacheDir(),"exception.png");
        if(!file.exists()) {
            L.e(file.getAbsolutePath() + " not found file");
            return;
        }

        MultipartBody.Builder builder = new MultipartBody.Builder();

        MultipartBody multipartBody  =  builder.setType(MultipartBody.FORM)
                .addFormDataPart("username","zcwfeng")
                .addFormDataPart("password","111111")
                .addFormDataPart("mPhoto","exception_upload.png",
                        RequestBody.create(file,
                        MediaType.parse("application/octet-stream")))
                .build();

        ContentRequestBody contentRequestBody = new ContentRequestBody(multipartBody, new ContentRequestBody.Listener() {
            @Override
            public void onRequestProgress(long byteWritrd, long contentLength) {
                L.e(byteWritrd + "/" + contentLength);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTv.setText("upload:" + byteWritrd + "/" + contentLength);
                    }
                });
            }
        });



        Request request = new Request.Builder()
                .url(mBaseUrl + "uploadInfo")
                .post(contentRequestBody).build();

        executeRequest(request);
    }


    public void doGet(View view) throws IOException {
        Request.Builder builder = new Request.Builder();
        Request request = builder
                .url(mBaseUrl + "login?username=zcw&password=1234")
                .build();
        executeRequest(request);
    }

    private void executeRequest(Request request) {
        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                L.e("onFailure:" + e.getMessage());
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                L.e("onResponse:");
                final String res = response.body().string();


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTv.setText(res);
                    }
                });

                L.e(res);
            }
        });
    }


    /**
     * 自动管理Cookies
     */
    private class CookiesManager implements CookieJar {
        private final PersistentCookieStore cookieStore = new PersistentCookieStore(getApplicationContext());

        @Override
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
            if (cookies != null && cookies.size() > 0) {
                for (Cookie item : cookies) {
                    cookieStore.add(url, item);
                }
            }
        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl url) {
            List<Cookie> cookies = cookieStore.get(url);
            return cookies;
        }
    }

}
