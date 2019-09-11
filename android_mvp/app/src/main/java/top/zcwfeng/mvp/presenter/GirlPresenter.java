package top.zcwfeng.mvp.presenter;

import java.util.List;

import top.zcwfeng.mvp.bean.Girl;
import top.zcwfeng.mvp.model.GirlModelImpl;
import top.zcwfeng.mvp.model.IGirlModel;
import top.zcwfeng.mvp.view.IGirlView;

public class GirlPresenter<T extends IGirlView> extends BasePresenter<T> {
    IGirlModel model = new GirlModelImpl();


    public GirlPresenter() {

    }



    public void fetch(){
        if(mViewRef.get() != null) {
            mViewRef.get().loading();
            if(model != null) {
                model.loadGril(new IGirlModel.CallbackListener() {
                    @Override
                    public void onComplete(List<Girl> data) {
                        if(mViewRef.get() != null) {
                            mViewRef.get().showGirls(data);
                        }
                    }
                });
            }
        }
    }

}
