package top.zcwfeng.libpermissionhelper;

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
    public static void requestPermission(Activity activity,String[]permission,int requestCode){
        doRequestPermission(activity,permission,requestCode);
    }
    //给fragment用
    public static void requestPermission(Fragment fragment, String[]permission, int requestCode){
        doRequestPermission(fragment.getActivity(),permission,requestCode);

    }

    //查找proxy
    private static void doExecutGrant(Activity activity, String[] permission, int requestCode) {
        PermissionProxy proxy = findProxy(activity);
        if(proxy != null) {
            proxy.grant(requestCode,activity,permission);
        }
    }

    private static void doRequestPermission(Activity activity, String[] permission, int requestCode) {

        if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.M){
            doExecutGrant(activity,permission,requestCode);
            return;
        }


        boolean rational = shoudShowPermissionRational(activity,permission,requestCode);
        if(rational) return;
        //继续往下传
        _doRequestPermission(activity,permission,requestCode);

    }


    private static void _doRequestPermission(Activity activity, String[] permission, int requestCode) {
        List<String> deniedPermissions = findDeniePermission(activity,permission);
        if(deniedPermissions.size() > 0) {
            String[] denieds = new String[deniedPermissions.size()];
            deniedPermissions.toArray(denieds);
            ActivityCompat.requestPermissions(activity,permission,requestCode);
        }else{
            doExecutGrant(activity,permission,requestCode);
        }
    }

    private static boolean shoudShowPermissionRational(final Activity activity, final String[] permissions, final int requestCode) {
        PermissionProxy proxy = findProxy(activity);

        final List<String> deniePermissions = findShouldShowPermission(activity,permissions);
        if(!deniePermissions.isEmpty()) {
            final String[] denied = new String[deniePermissions.size()];
            deniePermissions.toArray(denied);

            return proxy.rational(requestCode,activity,denied, new PermissionCallback() {
                @Override
                public void onRationalExecute() {
                    ActivityCompat.requestPermissions(activity,denied,requestCode);
                }
            });
        }

        return false;
    }

    private static List<String> findShouldShowPermission(Activity activity,String[] permissions) {
        List<String> rational = new ArrayList<>();
        for(String p:permissions){
            if(ActivityCompat.shouldShowRequestPermissionRationale(activity,p)){
                rational.add(p);
            }
        }
        return rational;
    }


    /**
     *
     * @param activity
     * @param permissions
     * @return
     */
    private static List<String> findDeniePermission(Activity activity,String[] permissions) {
        List<String> denied = new ArrayList<>();
        for(String p:permissions){
            if(ActivityCompat.checkSelfPermission(activity,p) != PackageManager.PERMISSION_GRANTED){
                denied.add(p);
            }
        }
        return denied;
    }



    private static void doExecutDenied (Activity activity, String[] permission, int requestCode) {
        PermissionProxy proxy = findProxy(activity);
        if(proxy != null) {
            proxy.denied(requestCode,activity,permission);
        }
    }

    public static PermissionProxy   findProxy(Activity activity) {
        Class<? extends Activity> clazz = activity.getClass();
        try {
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
        List<String> denied = new ArrayList<>();
        List<String> grant = new ArrayList<>();

        for(int i=0;i<grantResults.length;i++) {
            String permission = permissions[i];
            if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                denied.add(permission);
            }else {
                grant.add(permission);
            }
        }
        if(grant.size() > 0){
            doExecutGrant(activity,permissions,requestCode);
        }

    }
}
