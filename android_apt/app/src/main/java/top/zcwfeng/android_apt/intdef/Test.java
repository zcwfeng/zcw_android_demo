package top.zcwfeng.android_apt.intdef;

import androidx.annotation.DrawableRes;
import androidx.annotation.IntDef;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import top.zcwfeng.android_apt.R;

public class Test {
    @IntDef({SUNDAY,MONDAY})
    @Target({ElementType.FIELD,ElementType.PARAMETER})
    @Retention(RetentionPolicy.CLASS)
    @interface WeekDay{

    }

    private static WekDay mCurrentDay;
    private static int mCurrentIntDay;

    enum WekDay{
        SUNDAY,MONDAY
    }

    private static final int SUNDAY = 0;
    private static final int MONDAY = 7;

    public static void setDrawable(@DrawableRes int id) {

    }
    public static void setCurrentIntDay(@WeekDay int currentDay) {
        mCurrentIntDay = currentDay;
    }
    public static void setCurrentDay(WekDay currentDay) {
        mCurrentDay = currentDay;
    }


    public static void main(String[] args) {
        setDrawable(R.drawable.ic_launcher_background);

        setCurrentDay(WekDay.MONDAY);
        setCurrentIntDay(SUNDAY);
    }
}
