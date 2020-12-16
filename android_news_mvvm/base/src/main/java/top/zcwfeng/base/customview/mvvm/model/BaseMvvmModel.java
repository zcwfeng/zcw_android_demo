package top.zcwfeng.base.customview.mvvm.model;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import top.zcwfeng.base.customview.preference.BasicDataPreferenceUtil;
import top.zcwfeng.base.customview.utils.GenericUtils;

public abstract
class BaseMvvmModel<NETWORK_DATA, RESULT_DATA> implements MvvmDataObserve<NETWORK_DATA> {
    private CompositeDisposable compositeDisposable;

    protected int mPage = 1;
    private boolean mIsLoading;
    private final boolean mIsPaging;
    private final String mCachedPreferenceKey;
    private final int INIT_PAGE_NUMBER;
    //最新安装的时候没有网络的缓存
    private final String mApkPredefineData;
    protected WeakReference<IBaseModelListener> mReferenceBaseModelListener;

    public BaseMvvmModel(boolean mIsPaging, String mCachedPreferenceKey, String apkPredefinedData, int... initPageNumber) {
        this.mIsPaging = mIsPaging;
        if (mIsPaging && initPageNumber != null && initPageNumber.length > 0) {
            INIT_PAGE_NUMBER = initPageNumber[0];
        } else {
            INIT_PAGE_NUMBER = -1;
        }
        this.mApkPredefineData = apkPredefinedData;
        this.mCachedPreferenceKey = mCachedPreferenceKey;

    }

    public void register(IBaseModelListener listener) {
        if (listener != null) {
            mReferenceBaseModelListener = new WeakReference<>(listener);
        }
    }

    public abstract void load();

    public void loadNextPage() {
        if (!mIsLoading) {
            mIsLoading = true;
            load();
        }
    }

    public void refresh() {
        if (!mIsLoading) {
            if (mIsPaging) {
                mPage = INIT_PAGE_NUMBER;
            }
            mIsLoading = true;
            load();
        }

    }

    /**
     * 加载缓存
     */
    public void getCachedDataAndload() {
        if (!mIsLoading) {
            mIsLoading = true;
            if (mCachedPreferenceKey != null) {
                String saveDataString = BasicDataPreferenceUtil.getInstance()
                        .getString(mCachedPreferenceKey);
                if (!TextUtils.isEmpty(saveDataString)) {
                    try {
                        NETWORK_DATA saveData = new Gson().fromJson(new JSONObject(saveDataString)
                                        .optString("data"),
                                (Class<NETWORK_DATA>) GenericUtils.getGenericType(this));
                        if (saveData != null) {
                            onSuccess(saveData, true);
                        }
                        long timeSlot = Long.parseLong(new JSONObject(saveDataString)
                                .optString("updateTimeInMillis"));
                        if (isNeedToUpdate(timeSlot)) {
                            load();
                            return;
                        }
                    } catch (JSONException e) {
                        Log.e("BaseMvvModel", e.getMessage());
//                        e.printStackTrace();
                    }
                }

                if (mApkPredefineData != null) {
                    NETWORK_DATA saveData = new Gson().fromJson(mApkPredefineData,
                            (Class<NETWORK_DATA>) GenericUtils.getGenericType(this));
                    if (saveData != null) {
                        onSuccess(saveData, true);
                    }
                }
            }
            load();
        }
    }

    private boolean isNeedToUpdate(long cachedTimeSlot) {
        // TODO: 2020/10/16 缓存更新
        return true;
    }

    protected void notifyResultToListener(NETWORK_DATA networkData, RESULT_DATA resultData,
                                          boolean isFromCache) {
        IBaseModelListener listener = mReferenceBaseModelListener.get();
        if (listener != null) {
            // notify
            if (mIsPaging) {
                listener.onLoadSuccess(
                        this,
                        resultData,
                        new PagingResult(
                                mPage == INIT_PAGE_NUMBER,
                                resultData == null || ((List) resultData).isEmpty(),
                                ((List) resultData).size() > 0));
            } else {
                listener.onLoadSuccess(this, resultData);
            }
            // save resultData to preference
            if (mIsPaging) {
                if (mCachedPreferenceKey != null && mPage == INIT_PAGE_NUMBER && !isFromCache) {
                    saveDataToPreference(networkData);
                }
            } else {
                if (mCachedPreferenceKey != null && !isFromCache) {
                    saveDataToPreference(networkData);
                }
            }
            // update page number
            if (mIsPaging && !isFromCache) {
                if (resultData != null && ((List) resultData).size() > 0) {
                    mPage++;
                }
            }

        }

        if (!isFromCache) {
            mIsLoading = false;
        }
    }

    protected void loadFail(final String errorMessage) {
        IBaseModelListener listener = mReferenceBaseModelListener.get();

        if (listener != null) {
            if (mIsPaging) {
                listener.onLoadFailed(this, errorMessage, new PagingResult(mPage == INIT_PAGE_NUMBER, true, false));
            } else {
                listener.onLoadFailed(this, errorMessage);

            }

        }


        mIsLoading = false;

    }

    protected void saveDataToPreference(NETWORK_DATA data) {
        if (data != null) {
            BaseCachedData<NETWORK_DATA> cachedData = new BaseCachedData<>();
            cachedData.data = data;
            cachedData.updateTimeInMillis = System.currentTimeMillis();
            BasicDataPreferenceUtil.getInstance().setString(mCachedPreferenceKey, new Gson().toJson(cachedData));
        }
    }

    public void addDisposable(Disposable d) {
        if (d == null) {
            return;
        }

        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(d);
    }

    public void cancel() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

    public boolean isPaging() {
        return mIsPaging;
    }
}
