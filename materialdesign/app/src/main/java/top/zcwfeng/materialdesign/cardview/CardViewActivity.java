package top.zcwfeng.materialdesign.cardview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import top.zcwfeng.materialdesign.R;
import top.zcwfeng.materialdesign.databinding.ActivityCardViewBinding;
import top.zcwfeng.materialdesign.databinding.ActivityNestScrollBinding;
import top.zcwfeng.materialdesign.databinding.ItemLayoutBinding;

public class CardViewActivity extends AppCompatActivity {

    private ActivityCardViewBinding binding;
    private ItemLayoutBinding itemLayoutBinding,itemLayoutBinding1,itemLayoutBinding2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCardViewBinding.inflate(getLayoutInflater());
        itemLayoutBinding = ItemLayoutBinding.inflate(getLayoutInflater(),binding.cardview,true);
        itemLayoutBinding1 = ItemLayoutBinding.inflate(getLayoutInflater(),binding.cardview1,true);
        itemLayoutBinding2 = ItemLayoutBinding.inflate(getLayoutInflater(),binding.cardview2,true);
        setContentView(binding.getRoot());
        itemLayoutBinding.ivPortrait.setImageDrawable(getDrawable(R.drawable.xiaoxin));
        itemLayoutBinding.tvMotto.setText("Hi,美女，喜欢吃青椒吗？");
        itemLayoutBinding.tvNickname.setText("蜡笔小新");

        itemLayoutBinding1.ivPortrait.setImageDrawable(getDrawable(R.drawable.mingren));
        itemLayoutBinding1.tvMotto.setText("我是要成为火影的男人！！！");
        itemLayoutBinding1.tvNickname.setText("鸣人");


        itemLayoutBinding2.ivPortrait.setImageDrawable(getDrawable(R.drawable.liudao));
        itemLayoutBinding2.tvMotto.setText("触碰万物之理，能控制森罗万象");
        itemLayoutBinding2.tvNickname.setText("六道仙人");


    }
}