package top.zcwfeng.mvp_observer.model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainActivityModel extends ViewModel {
    public static final int LOGIN_START   =  1;
    public static final int LOGIN_SUCCESS =  2;
    public static final int LOGIN_FAIL    = -1;
    public MutableLiveData<Integer> loginState = new MutableLiveData<>();
}