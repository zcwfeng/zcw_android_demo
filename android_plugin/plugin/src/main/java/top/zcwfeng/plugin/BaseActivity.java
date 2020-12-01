package top.zcwfeng.plugin;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.ContextThemeWrapper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Field;

public
class BaseActivity extends AppCompatActivity {
    // TODO: 2020/12/1 测试方案三
    protected Context context;

//    @Override
//    public Resources getResources() {
        // TODO: 2020/12/1 测试方案一
//        if (getApplication() != null && getApplication().getResources() != null) {
//            return getApplication().getResources();
//        }
//        return super.getResources();


        // TODO: 2020/12/1 方案二
//        Resources resources = LoadUtils.getResources(getApplication());
//        // 如果插件 是单独的app 那么 super.getResources()
//        return resources == null ? super.getResources() : resources;
//    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Resources resources = LoadUtils.getResources(getApplication());
        // TODO: 2020/12/1 方案三 创建context，替换resource
        context = new ContextThemeWrapper(getBaseContext(),0);
        Class<? extends Context> clazz = context.getClass();
        try {
            Field mResourcesField = clazz.getDeclaredField("mResources");
            mResourcesField.setAccessible(true);
            mResourcesField.set(context, resources);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
