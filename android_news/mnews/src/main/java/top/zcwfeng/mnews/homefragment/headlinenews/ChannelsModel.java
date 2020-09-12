package top.zcwfeng.mnews.homefragment.headlinenews;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import top.zcwfeng.base.model.BaseModel;
import top.zcwfeng.mnews.homefragment.api.NewsApi;
import top.zcwfeng.network.beans.NewsChannelsBean;
import top.zcwfeng.network.errorhandler.ExceptionHandle;
import top.zcwfeng.network.observer.BaseObserver;

public
class ChannelsModel extends BaseModel<ArrayList<ChannelsModel.Channel>> {
    private static final String PREF_KEY_HOME_CHANNEL = "pref_key_home_channel";
    public static final String PREDEFINED_CHANNELS = "[\n" +
            "    {\n" +
            "        \"channelId\": \"5572a108b3cdc86cf39001cd\",\n" +
            "        \"channelName\": \"国内焦点\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"channelId\": \"5572a108b3cdc86cf39001ce\",\n" +
            "        \"channelName\": \"国际焦点\"\n" +
            "    }\n" +
            "]";

    public class Channel {
        public String channelId;
        public String channelName;
    }

    protected String getCachedPreferenceKey() {
        return PREF_KEY_HOME_CHANNEL;
    }

    protected Type getTClass() {
        return new TypeToken<ArrayList<Channel>>() {}.getType();
    }

    protected String getApkString() {
        return PREDEFINED_CHANNELS;
    }

    @Override
    public void refresh() {
    }

    @Override
    protected void load() {
        NewsApi.getInstance().getNewsChannels(new BaseObserver<NewsChannelsBean>(this) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                e.printStackTrace();
                loadFail(e.message);
            }

            @Override
            public void onNext(NewsChannelsBean newsChannelsBean) {
                ArrayList<Channel> channels = new ArrayList<>();
                for (NewsChannelsBean.ChannelList source : newsChannelsBean.showapiResBody.channelList) {
                    Channel channel = new Channel();
                    channel.channelId = source.channelId;
                    channel.channelName = source.name;
                    channels.add(channel);
                }
                loadSuccess(channels);
            }
        });
    }

}
