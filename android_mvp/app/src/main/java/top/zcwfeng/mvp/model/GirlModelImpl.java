package top.zcwfeng.mvp.model;

import java.util.ArrayList;
import java.util.List;

import top.zcwfeng.mvp.bean.Girl;

public class GirlModelImpl implements IGirlModel {




    @Override
    public void loadGril(CallbackListener listener) {
        List<Girl> data = new ArrayList<>();
        data.add(new Girl("楚天","https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1636376134,3671020030&fm=26&gp=0.jpg"));
        data.add(new Girl("美美","https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1567452261203&di=f12de7c777400f797ac8e2175766a73a&imgtype=0&src=http%3A%2F%2Fimg.popo.cn%2Fuploadfile%2F2017%2F0617%2F20170617032830844.jpg"));
        data.add(new Girl("美美","https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1567452261202&di=40bec8d177ebc5eb117158f9d881c729&imgtype=0&src=http%3A%2F%2Fimg.361games.com%2Ffile%2Ftu%2Fmeinv%2Fqjvjmuumck2.jpg"));
        data.add(new Girl("美美","https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3519641913,3897236435&fm=26&gp=0.jpg"));
        data.add(new Girl("美美","https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1567452261203&di=f12de7c777400f797ac8e2175766a73a&imgtype=0&src=http%3A%2F%2Fimg.popo.cn%2Fuploadfile%2F2017%2F0617%2F20170617032830844.jpg"));
        data.add(new Girl("美美","https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1567452261200&di=0b0560d84e84f9d65e287b68b34c98a3&imgtype=0&src=http%3A%2F%2Fwww.wndhw.com%2Fxiezhen%2Fmeinv%2Fimages%2Fmn028_1.jpg"));
        data.add(new Girl("美美","https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1849663442,3787063112&fm=26&gp=0.jpg"));
        data.add(new Girl("美美","https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2789856891,2288667602&fm=26&gp=0.jpg"));

        listener.onComplete(data);
    }
}
