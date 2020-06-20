package top.zcwfeng.rxjava.util;

import android.app.Activity;
import android.content.Intent;

public
class TestUtil
{
    public static void testStartActivity(Activity activity,Class<?> clazz){
        Intent intent = new Intent(activity,clazz);
        activity.startActivity(intent);
    }
}
