package top.zcwfeng.base.model;

import android.util.Log;

import java.lang.ref.WeakReference;

public abstract
class BaseModel<T> extends SuperBaseModel<T> {
    public interface IModelListener<T> extends IBaseModelListener {
        void onLoadFinish(BaseModel model, T data);

        void onLoadFail(BaseModel model, String prompt);
    }


    /**
     *  加载网络数据成功
     *  通知所有的注册着加载结果
     *  */
    protected void loadSuccess(T data) {

        synchronized (this) {
            mUiHandler.postDelayed(() -> {
                for (WeakReference<IBaseModelListener> weakListener : mWeakListenerArrayList) {
                    Log.e("zcw:::","1================");

                    if (weakListener.get() instanceof IModelListener) {
                        Log.e("zcw:::","2================");

                        IModelListener listenerItem = (IModelListener) weakListener.get();
                        if (listenerItem != null) {
                            Log.e("zcw:::","3================");

                            listenerItem.onLoadFinish(BaseModel.this, data);
                            Log.e("zcw:::","4================");

                        }
                    }
                }
                Log.e("zcw:::","5================");

                /** 如果我们需要缓存数据，加载成功，让我们保存他到preference */
                if (getCachedPreferenceKey() != null) {
                    saveDataToPreference(data);
                    Log.e("zcw:::","6================");

                }
            }, 0);
        }
    }

    /**
     *  加载网络数据失败
     *  通知所有的注册着加载结果
     *  */
    protected void loadFail(final String prompt) {
        synchronized (this) {
            mUiHandler.postDelayed(() -> {
                for (WeakReference<IBaseModelListener> weakListener : mWeakListenerArrayList) {
                    if (weakListener.get() instanceof IModelListener) {
                        IModelListener listenerItem = (IModelListener) weakListener.get();
                        if (listenerItem != null) {
                            listenerItem.onLoadFail(BaseModel.this, prompt);
                        }
                    }
                }
            }, 0);
        }
    }

    @Override
    protected void notifyCachedData(T data) {
        loadSuccess(data);
    }
}
