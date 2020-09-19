package com.zero.myfish.fish;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

public class FishActivity extends Activity {

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(new FishheaderView(this));
    }
}
