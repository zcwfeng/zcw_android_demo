package top.zcwfeng.mvp_observer.presenter;

import androidx.lifecycle.ViewModel;

import top.zcwfeng.mvp_observer.model.MainActivityModel;

public
class MainActivityPresenter {



    private MainActivityModel mainActivityModel;
    public void registerViewModel(ViewModel model){
        mainActivityModel = (MainActivityModel) model;
    }

    public void doLogin(String username,String userpwd){
        //我们使用一线程来模拟用户登录的网络请求, 这里我们就不处理方法的参数了
        new Thread(new Runnable() {
            @Override
            public void run() {
              mainActivityModel.loginState.postValue(MainActivityModel.LOGIN_START);
              // 模拟服务器交互
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    mainActivityModel.loginState.postValue(MainActivityModel.LOGIN_FAIL);
                }
                mainActivityModel.loginState.postValue(MainActivityModel.LOGIN_SUCCESS);
            }
        }).start();
    }
}
