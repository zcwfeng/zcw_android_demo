package top.zcwfeng.rxjava.doonnext;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public
interface IRequestNetWork {
//    请求注册，模拟登陆，伪代码---OKhttp
    @POST
    public Observable<RegisterResponse> registerAction(@Body RegisterRequest registerRequest);

    //    请求登陆，模拟登陆，伪代码---OKHttp
    @POST
    public Observable<LoginResponse> loginAction(@Body LoginRequest loginRequest);
}
