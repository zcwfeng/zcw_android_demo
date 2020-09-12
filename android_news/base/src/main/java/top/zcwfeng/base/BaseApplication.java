package top.zcwfeng.base;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import java.util.List;

public class BaseApplication extends Application{
    // OOM won't happen.
    public static Application sApplication;

    public void setsDebug(boolean isDebug) {
        sDebug = isDebug;
    }

    public static boolean sDebug;

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;
    }

    /**
     * 获取进程名
     *
     * @param context
     * @return
     */
    public static String getCurrentProcessName(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo proInfo : runningApps) {
            if (proInfo.pid == android.os.Process.myPid()) {
                if (proInfo.processName != null) {
                    return proInfo.processName;
                }
            }
        }
        return null;
    }
}
