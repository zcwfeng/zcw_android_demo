package top.zcwfeng.rxjava.retrofit;


import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import top.zcwfeng.rxjava.retrofit.bean.Bean;
import top.zcwfeng.rxjava.retrofit.bean.ProjectBean;

public interface WanAndroidApi {
    @GET("/project/tree/json")
    Observable<ProjectBean> getProject();

    @GET("/article/list/{pageIndex}/json")
    Observable<Bean> getProjectItem(@Path("pageIndex") int pageIndex, @Query("cid") int cid);

}

