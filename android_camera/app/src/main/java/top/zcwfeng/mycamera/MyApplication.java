package top.zcwfeng.mycamera;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.camera.core.CameraXConfig;

public
class MyApplication extends Application implements CameraXConfig.Provider{
    @NonNull
    @Override
    public CameraXConfig getCameraXConfig() {
        return null;
    }
}
