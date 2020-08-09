package top.zcwfeng.materialdesign.bottomAppBar;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import top.zcwfeng.materialdesign.databinding.ActivityBottomAppBarBinding;

public class BottomAppBarActivity extends AppCompatActivity {

    private ActivityBottomAppBarBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBottomAppBarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}
