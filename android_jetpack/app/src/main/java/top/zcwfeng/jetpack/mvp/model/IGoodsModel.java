package top.zcwfeng.jetpack.mvp.model;

import java.util.List;

public interface IGoodsModel<T> {
    void loadGoodsData(OnLoadListener onLoadListener);
    interface OnLoadListener<T>{
        void onComplete(List<T> goods);
        void onError(String msg);
    }
}