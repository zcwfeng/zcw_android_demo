package com.zero.toutiaodemo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.zero.toutiaodemo.view.Clip1View;
import com.zero.toutiaodemo.view.OverdrawView;

public class DrawTestActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new Clip1View(this));


    }

}
