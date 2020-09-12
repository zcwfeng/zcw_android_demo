package top.zcwfeng.mnews.homefragment.headlinenews;

import java.util.ArrayList;
import java.util.List;

import top.zcwfeng.base.activity.IBaseView;
import top.zcwfeng.base.model.BaseModel;
import top.zcwfeng.base.viewmodel.MvvmBaseViewModel;

public
class HeadlineNewsViewModel extends MvvmBaseViewModel<HeadlineNewsViewModel.IMainView, ChannelsModel>
implements BaseModel.IModelListener<ArrayList<ChannelsModel.Channel>>{
    public ArrayList<ChannelsModel.Channel> channels = new ArrayList<>();
    public HeadlineNewsViewModel(){
        model = new ChannelsModel();
        model.register(this);
    }

    public void refresh(){
        model.getCachedDataAndLoad();
    }
    @Override
    public void onLoadFinish(BaseModel model, ArrayList<ChannelsModel.Channel> data) {
        if(model instanceof ChannelsModel){
            if(getPageView() != null && data instanceof List) {
                channels.clear();
                channels.addAll(data);
                getPageView().onChannelsLoaded(channels);
            }
        }
    }

    @Override
    public void onLoadFail(BaseModel model, String prompt) {

    }

    public  interface IMainView extends IBaseView{
        void onChannelsLoaded(ArrayList<ChannelsModel.Channel> channels);
    }
}
