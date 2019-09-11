package top.zcwfeng.flowlayout.anim;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import top.zcwfeng.flowlayout.R;

public class AnimationActivity extends AppCompatActivity {

//    ValueAnimator
//    TimeAnimator
//    ObjectAnimator
//    AnimatorSet
    Button valueAnimatorBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        valueAnimatorBtn = findViewById(R.id.valueAnimationBtn);
        valueAnimatorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                valueAniamtor(valueAnimatorBtn);
                objectAnimator(valueAnimatorBtn);
            }
        });
    }

    private void objectAnimator(final View view){
//        Color Argb HSV HSL
        ObjectAnimator animator = ObjectAnimator.ofInt(view,"backgroundColor",0x77ff0000,0x9900ff00);
//        animator.setEvaluator(new ArgbEvaluator());
        animator.setEvaluator(new HSVEvaluator());
        animator.start();
    }

    private void valueAniamtor(final View view){
        //设置动画 始 & 末值
        //ofInt()两个作用：
        //1. 获取实例
        //2. 在传入参数之间平滑过渡
        //如下则0平滑过渡到3
        ValueAnimator animator = ValueAnimator.ofInt(0,3);
        //如下传入多个参数，效果则为0->5,5->3,3->10
        //ValueAnimator animator = ValueAnimator.ofInt(0,5,3,10);

        //设置动画的基础属性
        animator.setDuration(5000);//播放时长
        animator.setStartDelay(300);//延迟播放
        animator.setRepeatCount(0);//重放次数
        animator.setRepeatMode(ValueAnimator.RESTART);
        //重放模式
        //ValueAnimator.START：正序
        //ValueAnimator.REVERSE：倒序

        //设置更新监听
        //值 改变一次，该方法就执行一次
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //获取改变后的值
                int currentValue = (int) animation.getAnimatedValue();
                //输出改变后的值
                Log.d("1111", "onAnimationUpdate: " + currentValue);

                //改变后的值发赋值给对象的属性值
//                view.setpropert(currentValue);

                //刷新视图
                view.requestLayout();
            }
        });
        //启动动画
        animator.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        valueAnimatorBtn.setOnClickListener(null);
    }
}
