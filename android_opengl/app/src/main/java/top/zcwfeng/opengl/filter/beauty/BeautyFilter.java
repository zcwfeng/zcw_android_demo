package top.zcwfeng.opengl.filter.beauty;

import android.content.Context;

import top.zcwfeng.opengl.filter.AbstractFilter;
import top.zcwfeng.opengl.filter.FilterChain;


/**
 * 组合， 自己不需要画，调起下一个滤镜即可
 */
public class BeautyFilter extends AbstractFilter {

    private BeautyblurFilter beautyVerticalblurFilter;
    private BeautyblurFilter beautyHorizontalblurFilter;
    private BeautyHighpassFilter beautyHighpassFilter;
    private BeautyHighpassBlurFilter beautyHighpassBlurFilter;
    private BeautyAdjustFilter beautyAdjustFilter;

    public BeautyFilter(Context context) {
        super(context, -1, -1);
        beautyVerticalblurFilter = new BeautyblurFilter(context);
        beautyHorizontalblurFilter = new BeautyblurFilter(context);
        beautyHighpassFilter = new BeautyHighpassFilter(context);
        beautyHighpassBlurFilter = new BeautyHighpassBlurFilter(context);
        beautyAdjustFilter = new BeautyAdjustFilter(context);
    }


    @Override
    public int onDraw(int texture, FilterChain filterChain) {
        filterChain.setPause(true);

        //1、模糊处理
        beautyVerticalblurFilter.setTexelOffsetSize(0, filterChain.filterContext.height);
        int blurTexture = beautyVerticalblurFilter.onDraw(texture, filterChain);
        beautyHorizontalblurFilter.setTexelOffsetSize(filterChain.filterContext.width,0);
        blurTexture = beautyHorizontalblurFilter.onDraw(blurTexture,filterChain);

        //2、高反差保留 边缘锐化
        beautyHighpassFilter.setBlurTexture(blurTexture);
        int highpassTexture = beautyHighpassFilter.onDraw(texture, filterChain);

        //3、保边预处理 保留边沿的细节不被模糊掉
        int highpassBlurTexture = beautyHighpassBlurFilter.onDraw(highpassTexture, filterChain);

        //4、磨皮调整
        beautyAdjustFilter.setBlurTexture(blurTexture);
        beautyAdjustFilter.setHighpassBlurTexture(highpassBlurTexture);
        int beautyTextre = beautyAdjustFilter.onDraw(texture, filterChain);


        filterChain.setPause(false);
        return filterChain.proceed(beautyTextre);
    }
}
