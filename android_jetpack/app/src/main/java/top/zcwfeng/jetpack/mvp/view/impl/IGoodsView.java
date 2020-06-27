package top.zcwfeng.jetpack.mvp.view.impl;

import java.util.List;

import top.zcwfeng.jetpack.bean.Goods;
import top.zcwfeng.jetpack.mvp.view.IBaseView;

/**
 * UI逻辑
 */
public interface IGoodsView extends IBaseView {
    //显示图片
    void showGoodsView(List<Goods> goods);

    //加载进度条
    //加载动画
    //.......
}