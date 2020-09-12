package top.zcwfeng.mnews.homefragment.newslist;

import java.util.ArrayList;

import top.zcwfeng.base.customview.BaseCustomViewModel;
import top.zcwfeng.base.fragment.IBasePagingView;
import top.zcwfeng.base.model.BasePagingModel;
import top.zcwfeng.base.viewmodel.MvvmBaseViewModel;

public
class NewsListViewModel extends MvvmBaseViewModel<NewsListViewModel.INewsView,NewsListModel> implements BasePagingModel.IModelListener<ArrayList<BaseCustomViewModel>>{

    private ArrayList<BaseCustomViewModel> mNewsList = new ArrayList<>();

    public NewsListViewModel(String classId, String lboClassId) {
        model = new NewsListModel(classId, lboClassId);
        model.register(this);
        model.getCachedDataAndLoad();
    }
    @Override
    public void onLoadFinish(BasePagingModel model, ArrayList<BaseCustomViewModel> data, boolean isEmpty, boolean isFirstPage, boolean hasNextPage) {
        if (getPageView() != null) {
            if (model instanceof NewsListModel) {
                if (isFirstPage) {
                    mNewsList.clear();
                }
                if (isEmpty) {
                    if (isFirstPage) {
                        getPageView().onRefreshEmpty();
                    } else {
                        getPageView().onLoadMoreEmpty();
                    }
                } else {
                    mNewsList.addAll(data);
                    getPageView().onNewsLoaded(mNewsList);
                }
            }
        }
    }

    @Override
    public void onLoadFail(BasePagingModel model, String prompt, boolean isFirstPage) {
        if (getPageView() != null) {
            if (isFirstPage) {
                getPageView().onRefreshFailure(prompt);
            } else {
                getPageView().onLoadMoreFailure(prompt);
            }
        }
    }

    public void tryToRefresh() {
        model.refresh();
    }

    public void tryToLoadNextPage() {
        model.loadNexPage();
    }


    public interface INewsView extends IBasePagingView{
        void onNewsLoaded(ArrayList<BaseCustomViewModel> channels);
    }



}
