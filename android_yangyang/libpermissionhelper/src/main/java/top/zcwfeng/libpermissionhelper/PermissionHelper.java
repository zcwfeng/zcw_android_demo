package top.zcwfeng.libpermissionhelper;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class PermissionHelper {
   public final static String SUFFIX = "$$PermissionProxy";

    //给Activity用
    public static void requestPermission(Activity activity,String[]permissions,int requestCode){
        _requestPermissions(activity,requestCode,permissions);
    }
    //给fragment用
    public static void requestPermission(Fragment fragment, String[]permissions, int requestCode){
        _requestPermissions(fragment.getActivity(),requestCode,permissions);

    }

    //查找proxy
    private static void doExecutGrant(Activity activity, String[] permission, int requestCode) {
        PermissionProxy proxy = findPermissionProxy(activity);
        if(proxy != null) {
            proxy.grant(requestCode,activity,permission);
        }
    }


    private static boolean shoudShowPermissionRational(final Object context, final String[] permissions, final int requestCode) {

        final Activity activity = Utils.getActivity(context);

        PermissionProxy proxy = findPermissionProxy(activity);
        if (permissions!=null && proxy!=null && !proxy.needShowRationale(requestCode, permissions)) return false;
        List<String> rationalList = new ArrayList<>();
        for (String permission : permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                rationalList.add(permission);
            }
        }

        if(!rationalList.isEmpty()) {
            return proxy.rational(requestCode,activity,permissions, new PermissionCallback() {
                @Override
                public void onRationalExecute() {
                    _doRequestPermission(activity,permissions,requestCode);
                }
            });
        }
        return false;
    }

    @TargetApi(value = Build.VERSION_CODES.M)
    private static void _requestPermissions(Object object, int requestCode, String... permissions) {
        if (!Utils.isOverMarshmallow()) {
            doExecuteSuccess(object, requestCode, permissions);
            return;
        }

        boolean rationale = shoudShowPermissionRational((Activity)object, permissions, requestCode);
        if (rationale) {
            return;
        }

        _doRequestPermission(object, permissions, requestCode);
    }

    private static void _doRequestPermission(Object obj, String[] permissions, int requestCode) {
        List<String> deniedPermissions = Utils.findDeniedPermissions(Utils.getActivity(obj), permissions);
        if(deniedPermissions.size() > 0) {
            ActivityCompat.requestPermissions(Utils.getActivity(obj),permissions,requestCode);
        }else{
            doExecutGrant(Utils.getActivity(obj),permissions,requestCode);
        }
    }

    public static PermissionProxy findPermissionProxy(Object activity) {
        try {
            Class clazz = activity.getClass();
            Class forName = Class.forName(clazz.getName() + SUFFIX);
            return (PermissionProxy) forName.newInstance();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }




    public static void onRequestPermissionsResult(Activity activity, int requestCode, String[] permissions, int[] grantResults) {
        requestResult(activity, requestCode, permissions, grantResults);
    }

    public static void onRequestPermissionsResult(Fragment fragment, int requestCode, String[] permissions, int[] grantResults) {
        requestResult(fragment, requestCode, permissions, grantResults);
    }

    private static void requestResult(Object obj, int requestCode, String[] permissions, int[] grantResults) {
        List<String> grant = new ArrayList<>();
        List<String> denied = new ArrayList<>();
        for (int i = 0; i < grantResults.length; i++) {
            String permission = permissions[i];

            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                denied.add(permission);
            } else {
                grant.add(permission);
            }
        }

        if (!grant.isEmpty()) {
            doExecuteSuccess(obj, requestCode, grant.toArray(new String[grant.size()]));
        }

        if (!denied.isEmpty()) {
            doExecuteFail(obj, requestCode, grant.toArray(new String[denied.size()]));
        }
    }


    private static void doExecuteSuccess(Object context, int requestCode, String... permission) {
        findPermissionProxy(context).grant(requestCode, context,permission);

    }

    private static void doExecuteFail(Object context, int requestCode, String... permission) {
        findPermissionProxy(context).denied(requestCode,context, permission);
    }


}
