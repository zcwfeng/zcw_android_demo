package top.zcwfeng.sqlite;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import top.zcwfeng.sqlite.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}