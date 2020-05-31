package com.example.android_lifecycle.views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.CycleInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.android_lifecycle.R;

import java.util.Random;

public class RPEarnCashEntranceView extends RelativeLayout {
 
    private static final int COUNT_COINS_RAIN = 10;
 
    private ImageView vEarnCash;
    private LinearLayout vCoinsRain;//金币雨ViewGroup
    private int mScreenWidth;
    private int mCoinRainWidth;
    private long mIntervalTime = 0;//动画间隔时间
 
    public RPEarnCashEntranceView(@NonNull Context context) {
        this(context, null);
        init(context);
    }
 
    public RPEarnCashEntranceView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }
 
    public RPEarnCashEntranceView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
 
    @Override
    protected void onDetachedFromWindow() {
        vEarnCash.clearAnimation();
        super.onDetachedFromWindow();
    }
 
    private void init(Context context) {
        mScreenWidth = DimenUtils.getScreenWidth();
        mCoinRainWidth = DimenUtils.dp2px(60);
        LayoutInflater.from(context).inflate(R.layout.inflate_result_page_earn_cash_entrance, this, true);
        vEarnCash = (ImageView) findViewById(R.id.earn_cash_entrance);
        vCoinsRain = (LinearLayout) findViewById(R.id.earn_cash_coins_rain);
        adjustCoinsRainHeight();
        vEarnCash.setImageResource(R.mipmap.grab_hot_rank_mcoin_png);
        addCoinRainView();
    }
 
    /**
     * 按屏幕比例适配金币雨高度
     */
    private void adjustCoinsRainHeight() {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) vCoinsRain.getLayoutParams();
        params.height = DimenUtils.dp2px(112 + 500 *2);
        vCoinsRain.setLayoutParams(params);
    }
 
    /**
     * 开始金币翻转动画
     */
    private void startEarnCoins3DAnim() {
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(vEarnCash, "rotationY", 0, 360).setDuration(600);
        animator1.start();
    }
 
    /**
     * 添加金币雨
     */
    private void addCoinRainView() {
        //金币之间的间距
        int unitSpace = (mScreenWidth - DimenUtils.dp2px(2 * 60) - 9 * mCoinRainWidth) / 10;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mCoinRainWidth, mCoinRainWidth);
        params.leftMargin = unitSpace;
        for (int i = 0; i < COUNT_COINS_RAIN; i++) {
            ImageView imageView = new ImageView(getContext());
            imageView.setImageResource(R.mipmap.grab_hot_rank_mcoin_png);
            imageView.setLayoutParams(params);
            imageView.setVisibility(View.INVISIBLE);
            vCoinsRain.addView(imageView);
            CoinRainModel coinRainModel = new CoinRainModel(imageView, (i + 1) * unitSpace + i * mCoinRainWidth);
            startCoinRainAnim(coinRainModel, i);
        }
    }
 
    private void startCoinRainAnim(CoinRainModel coinRainModel, int what) {
        Message message = Message.obtain();
        message.obj = coinRainModel;
        message.what = what;
        mIntervalTime += 20;
        animHandler.sendMessageDelayed(message, mIntervalTime);
    }
 
    private void startCoinsAnim(final CoinRainModel coinRainModel) {
        startFallDownAnim(coinRainModel.coinView, 800).addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                startCollapseCoinsAnim(coinRainModel.coinView);
            }
        });
    }
 
    /**
     * 回弹一圈的View
     */
    private void startReboundOnceAnim(final CoinRainModel coinRainModel, final int direction) {
        final Random random = new Random();
        startFallDownAnim(coinRainModel.coinView, 600).addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                int radius = DimenUtils.dp2px(80);
                AnimatorSet animatorSet = startCycleCurveAnim(coinRainModel.coinView, direction == 0 ? 600 : 800, direction == 0 ? coinRainModel.positionX - radius : coinRainModel.positionX + radius, 350 + random.nextInt(100));
                animatorSet.start();

                animatorSet.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        startCollapseCoinsAnim(coinRainModel.coinView);
                    }
                });
            }
        });
    }
 
    /**
     * 回弹两圈的动
     */
    private void startReboundTwiceAnim(final CoinRainModel coinRainModel, final int direction) {
        startFallDownAnim(coinRainModel.coinView, 400).addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                int radius = DimenUtils.dp2px(50);
                AnimatorSet animatorSet = startCycleCurveAnim(coinRainModel.coinView, 600, direction == 0 ? coinRainModel.positionX - radius : coinRainModel.positionX + radius, 200);
                animatorSet.start();

                final AnimatorSet animatorSet1 = startCycleCurveAnim(coinRainModel.coinView, 600, direction == 0 ? coinRainModel.positionX - radius * 2 : coinRainModel.positionX + radius * 2, 360);
                animatorSet.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        animatorSet1.start();
                    }
                });
                animatorSet1.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        startCollapseCoinsAnim(coinRainModel.coinView);
                    }
                });
            }
        });
    }
 
    /**
     * 漂浮出去的动画
     */
    private void floatOutAnim(final CoinRainModel coinRainModel, final int direction) {
        startFallDownAnim(coinRainModel.coinView, 400).addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
 
                AnimatorSet animatorSet = startCycleCurveAnim(coinRainModel.coinView, 600, direction == 0 ? coinRainModel.positionX / 2 : coinRainModel.positionX + (mScreenWidth - coinRainModel.positionX) / 2, 300);
                animatorSet.start();
 
                final AnimatorSet animatorSet1 = startCycleCurveAnim(coinRainModel.coinView, 600, direction == 0 ? -coinRainModel.positionX / 2 : coinRainModel.positionX + (mScreenWidth - coinRainModel.positionX) / 2 * 3, 400);
 
                animatorSet.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        animatorSet1.start();
                    }
                });
                animatorSet1.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        coinRainModel.coinView.setAlpha(0);
                        vCoinsRain.removeView(coinRainModel.coinView);
                    }
                });
            }
        });
    }
 
    /**
     * 收起金币的动画
     */
    private void startCollapseCoinsAnim(final View view) {
        int marginRight = 40;
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(view, "x", mScreenWidth - DimenUtils.dp2px(marginRight));
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(view, "y", DimenUtils.dp2px(12));
        animator1.setInterpolator(new AccelerateInterpolator(0.5f));
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(500);
        animatorSet.playTogether(animator1, animator2);
        animatorSet.start();
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                view.setAlpha(0);
                vCoinsRain.removeView(view);
                if (vCoinsRain != null && vCoinsRain.getChildCount() == 1) {
                    startEarnCoins3DAnim();
                }
            }
        });
    }
 
    /**
     * 下落的动画
     */
    private ObjectAnimator startFallDownAnim(View view, int duration) {
        view.setVisibility(View.VISIBLE);
        ObjectAnimator coinsAnim = ObjectAnimator.ofFloat(view, "translationY", 0f, 1000f);
        coinsAnim.setDuration(duration);
        coinsAnim.start();
        return coinsAnim;
    }
 
    /**
     * 获取半个周期正弦弧形的动画
     */
    private AnimatorSet startCycleCurveAnim(View view, int duration, int x, int y) {
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(view, "x", x);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(view, "y", y);
        animator2.setInterpolator(new CycleInterpolator(0.5f));
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(duration);
        animatorSet.playTogether(animator1, animator2);
        return animatorSet;
    }
 
 
    private static class CoinRainModel {
 
        View coinView;
        int positionX;
 
        public CoinRainModel(View coinView, int positionX) {
            this.coinView = coinView;
            this.positionX = positionX;
        }
    }
 
    private static class AnimHandler extends Handler {
 
    }
 
    @SuppressLint("HandlerLeak")
    private AnimHandler animHandler = new AnimHandler() {
 
        @Override
        public void handleMessage(Message message) {
            super.handleMessage(message);
            CoinRainModel coinRainModel = (CoinRainModel) message.obj;
            if (message.what == COUNT_COINS_RAIN / 2 + 1) {  //5
                //右边飘出去
                floatOutAnim(coinRainModel, 0);
            } else if (message.what == COUNT_COINS_RAIN / 2 - 1) { //3
                //左边飘出去
                floatOutAnim(coinRainModel, 1);
            } else if (message.what == COUNT_COINS_RAIN / 2) { //4
                //直下
                startCoinsAnim(coinRainModel);
            } else if (message.what == COUNT_COINS_RAIN / 2 + 2) { //6
                //右边两圈
                startReboundTwiceAnim(coinRainModel, 0);
            } else if (message.what == COUNT_COINS_RAIN / 2 - 2) { //2
                //左边两圈
                startReboundTwiceAnim(coinRainModel, 1);
            } else if (message.what < COUNT_COINS_RAIN / 2 - 2) { //0 1
                //向右边转一圈
                startReboundOnceAnim(coinRainModel, 0);
            } else {                                              // 7 8
                //向左边转一圈
                startReboundOnceAnim(coinRainModel, 1);
            }
        }
    };
}