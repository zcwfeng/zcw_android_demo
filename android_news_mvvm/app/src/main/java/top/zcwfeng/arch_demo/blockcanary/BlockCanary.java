package top.zcwfeng.arch_demo.blockcanary;

import android.os.Looper;

public class BlockCanary {
    public static void install() {
        LogMonitor logMonitor = new LogMonitor();
        Looper.getMainLooper().setMessageLogging(logMonitor);
    }
}