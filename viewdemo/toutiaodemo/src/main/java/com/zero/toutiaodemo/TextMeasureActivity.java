package com.zero.toutiaodemo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.zero.toutiaodemo.view.TextMeasureView;

public class TextMeasureActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new TextMeasureView(this));


    }
}
