package top.zcwfeng.mvp.view;

import java.util.List;

import top.zcwfeng.mvp.bean.Girl;

public interface IGirlView {

    void showGirls(List<Girl> data);

    void loading();


}
