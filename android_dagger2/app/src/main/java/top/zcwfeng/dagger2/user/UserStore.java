package top.zcwfeng.dagger2;

import android.content.Context;
import android.util.Log;

public class UserStore {

    private Context mContext;
    public UserStore(Context context) {
        mContext = context;
    }

    public void register() {
        mContext.getSharedPreferences("user_sp",0).edit().commit();
        Log.e(Config.TAG,"UserStore----register");

    }
}
