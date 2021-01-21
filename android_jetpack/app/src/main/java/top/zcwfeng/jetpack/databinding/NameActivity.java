package top.zcwfeng.jetpack.databinding;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import top.zcwfeng.jetpack.R;
import top.zcwfeng.jetpack.viewmodel.NameViewModel;

public class NameActivity extends AppCompatActivity {

    private NameViewModel nameViewModel;
    TextView nameTextView;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);
        nameTextView = findViewById(R.id.tvText);
        btn = findViewById(R.id.btn);
//        nameViewModel = new ViewModelProvider.NewInstanceFactory().create(NameViewModel.class);
//        nameViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(NameViewModel.class);
//        nameViewModel = new ViewModelProvider(this).get(NameViewModel.class);
        nameViewModel = ViewModelProviders.of(this).get(NameViewModel.class);
        Observer observer = new Observer<String>() {
            @Override
            public void onChanged(String s) {
                nameTextView.setText(s);
            }
        };

        nameViewModel.getCurrentName().observe(this, observer);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String anotherName = "David" + (nameViewModel.i++);
                nameViewModel.getCurrentName().setValue(anotherName);
            }
        });
    }

    @Nullable
    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        Log.e("zcwfeng", "Object onRetainCustomNonConfigurationInstance()");
        return super.onRetainCustomNonConfigurationInstance();
    }

    @Nullable
    @Override
    public Object getLastNonConfigurationInstance() {
        Log.e("zcwfeng", "public Object getLastNonConfigurationInstance()");
        return super.getLastNonConfigurationInstance();
    }
}