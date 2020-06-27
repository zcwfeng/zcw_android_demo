package top.zcwfeng.jetpack.mvp.model;

import java.util.List;

import top.zcwfeng.jetpack.bean.Goods;

public interface IGoodsModel {
    void loadGoodsData(OnLoadListener onLoadListener);
    interface OnLoadListener{
        void onComplete(List<Goods> goods);
        void onError(String msg);
    }
}