package top.zcwfeng.base.customview.mvvm.viewmodel;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModel;

import java.util.List;

import top.zcwfeng.base.customview.mvvm.model.BaseMvvmModel;
import top.zcwfeng.base.customview.mvvm.model.IBaseModelListener;
import top.zcwfeng.base.customview.mvvm.model.PagingResult;

public abstract class BaseMvvmViewModel<MODEL extends BaseMvvmModel, DATA>
        extends ViewModel implements LifecycleObserver, IBaseModelListener<List<DATA>> {
    protected MODEL model;
    public MutableLiveData<List<DATA>> dataList = new MutableLiveData<>();
    public MutableLiveData<String> errMessage = new MutableLiveData<>();
    public MutableLiveData<ViewStatus> viewStatusLiveData = new MutableLiveData<>();

    public BaseMvvmViewModel() {
    }

    public abstract MODEL createModel();

    public void getCachedDataAndload() {
        viewStatusLiveData.setValue(ViewStatus.LOADING);
        createAndRegistModel();
        if (model != null) {
            model.getCachedDataAndload();
        }

    }


    public void refresh() {
        viewStatusLiveData.setValue(ViewStatus.LOADING);
        createAndRegistModel();
        if (model != null) {
            model.refresh();
        }
    }

    public void loadNextPage() {
        createAndRegistModel();
        if (model != null) {
            model.loadNextPage();
        }
    }


    private void createAndRegistModel() {
        if (model == null) {
            model = createModel();
            if (model != null) {
                model.register(this);
            } else {
                // TODO: 2020/10/17 throw Exception
                new RuntimeException("createModel 实例话对象null 或者没有实现");
            }
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (model != null) {
            model.cancel();
        }
    }

    @Override
    public void onLoadSuccess(BaseMvvmModel model, List<DATA> data, PagingResult... results) {
        if (model.isPaging()) {
            if (results[0].isEmpty) {
                if (results[0].isFirstPage) {
                    viewStatusLiveData.postValue(ViewStatus.EMPTY);
                } else {
                    viewStatusLiveData.postValue(ViewStatus.NO_MORE_DATA);
                }
            } else {
                if (results[0].isFirstPage) {
                    dataList.postValue(data);
                } else {
                    dataList.getValue().addAll(data);
                    dataList.postValue(dataList.getValue());
                }
                viewStatusLiveData.postValue(ViewStatus.SHOW_CONTENT);
            }


        } else {
            dataList.postValue(data);
            viewStatusLiveData.postValue(ViewStatus.SHOW_CONTENT);
        }
    }


    @Override
    public void onLoadFailed(BaseMvvmModel model, String msg, PagingResult... results) {
        errMessage.postValue(msg);
        if (results != null && results.length > 0 && results[0].isFirstPage) {
            viewStatusLiveData.postValue(ViewStatus.REFRESH_ERROR);
        } else {
            viewStatusLiveData.postValue(ViewStatus.LOAD_MORE_FAILED);
        }
    }

    // TODO: 2020/10/17 why ......
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        if (dataList == null || dataList.getValue() == null || dataList.getValue().size() == 0) {
            createAndRegistModel();
            model.getCachedDataAndload();
        } else {
            dataList.postValue(dataList.getValue());
            viewStatusLiveData.postValue(viewStatusLiveData.getValue());
        }
    }


}
