package com.example.android_lifecycle.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

/**
 * 作者：zcw on 2019-10-10
 * <p>
 * ViewModel ViewModelProvider ViewModelStore ViewModelStoreOwner
 */
public class MyViewModel extends ViewModel {
    private ArrayList<String> test;

    private MutableLiveData<String> mValue = new MutableLiveData<>();

    public LiveData getValue(){
        return mValue;
    }

    public void setValue(String value) {
        mValue.setValue(value);
    }

    public void init() {
        test = new ArrayList<>();
        test.add("test");
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        doCleared();
    }

    private void doCleared() {
        if (test != null) {
            test.clear();
            test = null;
        }
        Log.d("zcw", "MyViewModel: activity finish onCleared lifecycle android");

    }
}
