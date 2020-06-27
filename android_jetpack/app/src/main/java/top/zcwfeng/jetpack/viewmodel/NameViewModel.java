package top.zcwfeng.jetpack.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NameViewModel extends ViewModel {
    public int i = 0;
    private MutableLiveData<String> currentName;
    public MutableLiveData<String> getCurrentName(){
        if(currentName==null){
            currentName=new MutableLiveData<>();
        }
        return currentName;
    }
}