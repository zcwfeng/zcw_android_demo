package top.zcwfeng.android_okhttp;

import android.util.Log;

public class L {

    public final static String TAG = "zcw_okhttp";

    private static boolean debug = true;

    public static void e(String message){
        if(debug) {
            Log.e(TAG,message);

        }
    }
}
