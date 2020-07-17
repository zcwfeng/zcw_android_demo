package top.zcwfeng.summary.service;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

class MyIntentService extends IntentService {
    /**
     * @param name
     * @deprecated
     */
    public MyIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        // 做自己想做的事
    }
}
