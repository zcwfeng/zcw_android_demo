package top.zcwfeng.materialdesign.translucent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;

import top.zcwfeng.materialdesign.R;
import top.zcwfeng.materialdesign.databinding.ActivityQqMusicBinding;

/**
 * 1. 在values、values-v19、values-v21的style.xml都设置一个 Translucent System Bar 风格的Theme
 * 2. 在AndroidManifest.xml中对指定Activity的theme进行设置
 * 3. android:fitsSystemWindows设置为true
 */
public class QQMusicActivity extends AppCompatActivity {

    private ActivityQqMusicBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        binding = ActivityQqMusicBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}