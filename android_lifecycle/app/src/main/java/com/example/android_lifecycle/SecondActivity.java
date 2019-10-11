package com.example.android_lifecycle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import com.example.android_lifecycle.viewmodels.MyViewModel;

public class SecondActivity extends AppCompatActivity implements LifecycleOwner {

    private LifecycleRegistry mLifecycleRegistry;
    private MyViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        mLifecycleRegistry = new LifecycleRegistry(this);
        testViewModel();
    }

    @Override
    public Lifecycle getLifecycle() {
        return mLifecycleRegistry;
    }

    public void testViewModel(){
        viewModel = ViewModelProviders.of(this).get(MyViewModel.class);
        viewModel.init();

    }

}
