package top.zcwfeng.jetpack.mvp.presenter.impl;

import android.util.Log;

import androidx.lifecycle.LifecycleOwner;

import java.util.List;

import top.zcwfeng.jetpack.mvp.model.GoodsModel;
import top.zcwfeng.jetpack.mvp.model.IGoodsModel;
import top.zcwfeng.jetpack.mvp.presenter.BasePresenter;
import top.zcwfeng.jetpack.mvp.view.impl.IGoodsView;

public class GoodsPresenter<T extends IGoodsView> extends BasePresenter {

    IGoodsModel iGoodsModel=new GoodsModel();

    /**
     * 执行业务逻辑
     */
    public void fetch(){
        if(iGoodsView !=null && iGoodsModel!=null){
            iGoodsModel.loadGoodsData(new IGoodsModel.OnLoadListener() {
                @Override
                public void onComplete(List goods) {
                    ((IGoodsView)iGoodsView.get()).showGoodsView(goods);
                }

                @Override
                public void onError(String msg) {

                }
            });
        }
    }

    @Override
    public void onCreateX(LifecycleOwner owner) {
        super.onCreateX(owner);
        Log.i("David","create");
    }

    @Override
    public void onDestory(LifecycleOwner owner) {
        super.onDestory(owner);
        Log.i("David","destroy");
    }
}