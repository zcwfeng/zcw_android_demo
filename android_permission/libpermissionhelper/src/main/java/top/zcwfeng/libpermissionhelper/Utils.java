package top.zcwfeng.libpermissionhelper;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

final public class Utils {
  private Utils(){}

  public static boolean isOverMarshmallow() {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
  }

  @TargetApi(value = Build.VERSION_CODES.M)
  public static List<String> findDeniedPermissions(Activity activity, String... permission){
    List<String> denyPermissions = new ArrayList<>();
    for(String value : permission){
      if(activity.checkSelfPermission(value) != PackageManager.PERMISSION_GRANTED){
        denyPermissions.add(value);
      }
    }
    return denyPermissions;
  }

  public static List<Method> findAnnotationMethods(Class clazz, Class<? extends Annotation> clazz1){
    List<Method> methods = new ArrayList<>();
    for(Method method : clazz.getDeclaredMethods()){
      if(method.isAnnotationPresent(clazz1)){
        methods.add(method);
      }
    }
    return methods;
  }






  @NonNull
  protected static Activity getActivity(Object context) {
    Activity source = null;
    if (context instanceof Activity) {
      source = (Activity) context;
    } else if (context instanceof Fragment) {
      source = ((Fragment) context).getActivity();
    }
    if (source == null) {
      throw new IllegalStateException("context is null");
    }
    return source;
  }
}