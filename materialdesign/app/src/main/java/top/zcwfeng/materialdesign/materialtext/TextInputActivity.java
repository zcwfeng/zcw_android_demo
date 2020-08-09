package top.zcwfeng.materialdesign.materialtext;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.textfield.TextInputLayout;
import top.zcwfeng.materialdesign.R;
import top.zcwfeng.materialdesign.databinding.ActivityTextInputBinding;

public class TextInputActivity extends AppCompatActivity {

    private ActivityTextInputBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTextInputBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.materialbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showError(binding.textInput,"错误展示！！！");
            }
        });
    }

    private void showError(TextInputLayout textInputLayout, String error){
        textInputLayout.setError(error);
        textInputLayout.getEditText().setFocusable(true);
        textInputLayout.getEditText().setFocusableInTouchMode(true);
        textInputLayout.getEditText().requestFocus();
    }
}