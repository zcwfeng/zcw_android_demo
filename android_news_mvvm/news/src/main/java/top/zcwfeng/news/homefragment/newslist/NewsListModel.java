package top.zcwfeng.news.homefragment.newslist;

import com.xiangxue.network.TecentNetworkApi;
import com.xiangxue.network.observer.BaseObserver;

import java.util.ArrayList;
import java.util.List;

import top.zcwfeng.base.customview.customview.BaseCustomViewModel;
import top.zcwfeng.base.customview.mvvm.model.BaseMvvmModel;
import top.zcwfeng.common.views.pictureview.PictureTitleViewModel;
import top.zcwfeng.common.views.titleview.TitleViewModel;
import top.zcwfeng.news.homefragment.api.NewsApiInterface;
import top.zcwfeng.news.homefragment.api.NewsListBean;

public
class NewsListModel extends BaseMvvmModel<NewsListBean,List<BaseCustomViewModel>> {
    String channelId;
    String channelName;

    public NewsListModel(String channelId, String channelName) {
        super(true,
                channelId+channelName+"_preference_key",
                null,
                1);
        this.channelId = channelId;
        this.channelName = channelName;
    }




    @Override
    public void load() {
        TecentNetworkApi.getService(NewsApiInterface.class)
                .getNewsList(channelId,
                        channelName, String.valueOf(mPage))
                .compose(TecentNetworkApi.getInstance()
                        .applySchedulers(new BaseObserver<NewsListBean>(this,this)));
    }

    @Override
    public void onSuccess(NewsListBean newsListBean, boolean isFromcache) {
        List<BaseCustomViewModel> viewModels = new ArrayList<>();

        for (NewsListBean.Contentlist contentlist:newsListBean.showapiResBody.pagebean.contentlist) {

            if(contentlist.imageurls !=null && contentlist.imageurls.size() >0){
                PictureTitleViewModel model = new PictureTitleViewModel();
                model.pictureUrl = contentlist.imageurls.get(0).url;
                model.title = contentlist.title;
                model.jumpUrl = contentlist.link;
                viewModels.add(model);
            } else{
                TitleViewModel model = new TitleViewModel();
                model.title = contentlist.title;
                model.jumpUrl = contentlist.link;
                viewModels.add(model);
            }
        }
        notifyResultToListener(newsListBean,viewModels,isFromcache);
    }

    @Override
    public void onFailure(Throwable e) {
        loadFail(e.getMessage());
    }


}
