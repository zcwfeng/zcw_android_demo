package top.zcwfeng.mvp.model;

import java.util.List;

import top.zcwfeng.mvp.bean.Girl;

public interface IGirlModel {

    void loadGril(CallbackListener listener);

    interface CallbackListener{
        void onComplete(List<Girl> data);
    }

}
