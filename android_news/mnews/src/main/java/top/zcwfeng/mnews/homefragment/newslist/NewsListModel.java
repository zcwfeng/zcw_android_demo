package top.zcwfeng.mnews.homefragment.newslist;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import top.zcwfeng.base.customview.BaseCustomViewModel;
import top.zcwfeng.base.model.BasePagingModel;
import top.zcwfeng.common.views.picturetitleview.PictureTitleViewViewModel;
import top.zcwfeng.common.views.titleview.TitleViewViewModel;
import top.zcwfeng.mnews.homefragment.api.NewsApi;
import top.zcwfeng.network.beans.NewsListBean;
import top.zcwfeng.network.errorhandler.ExceptionHandle;
import top.zcwfeng.network.observer.BaseObserver;

public
class NewsListModel <T> extends BasePagingModel<T> {
    private String mChannelId = "";
    private String mChannelName = "";
    private static final String PREF_KEY_NEWS_CHANNEL = "pref_key_news_";
    @Override
    protected String getCachedPreferenceKey() {
        return PREF_KEY_NEWS_CHANNEL + mChannelId;
    }

    protected Type getTClass() {
        return new TypeToken<ArrayList<PictureTitleViewViewModel>>() {
        }.getType();
    }

    public NewsListModel(String channelId, String channelName) {
        mChannelId = channelId;
        mChannelName = channelName;
    }
    @Override
    public void refresh() {
        isRefresh = true;
        load();
    }

    public void loadNexPage() {
        isRefresh = false;
        load();
    }

    @Override
    protected void load() {
        NewsApi.getInstance().getNewsList(new BaseObserver<NewsListBean>(this) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                e.printStackTrace();
                loadFail(e.message, isRefresh);
            }

            @Override
            public void onNext(NewsListBean newsChannelsBean) {
                // All observer run on main thread, no need to synchronize
                pageNumber = isRefresh ? 2 : pageNumber + 1;
                ArrayList<BaseCustomViewModel> baseViewModels = new ArrayList<>();

                for (NewsListBean.Contentlist source : newsChannelsBean.showapiResBody.pagebean.contentlist) {
                    if (source.imageurls != null && source.imageurls.size() > 1) {
                        PictureTitleViewViewModel viewModel = new PictureTitleViewViewModel();
                        viewModel.avatarUrl = source.imageurls.get(0).url;
                        viewModel.link = source.link;
                        viewModel.title = source.title;
                        baseViewModels.add(viewModel);
                    } else {
                        TitleViewViewModel viewModel = new TitleViewViewModel();
                        viewModel.link = source.link;
                        viewModel.title = source.title;
                        baseViewModels.add(viewModel);
                    }
                }
                loadSuccess((T) baseViewModels, baseViewModels.size() == 0, isRefresh,
                        baseViewModels.size() == 0);
            }
        }, mChannelId, mChannelName, String.valueOf(isRefresh ? 1 : pageNumber));
    }
}
