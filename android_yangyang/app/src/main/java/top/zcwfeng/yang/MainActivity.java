package top.zcwfeng.yang;

import android.Manifest;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import timber.log.Timber;
import top.zcwfeng.libannotation.PermissionDenied;
import top.zcwfeng.libannotation.PermissionGrant;
import top.zcwfeng.libannotation.PermissionRational;
import top.zcwfeng.libpermissionhelper.PermissionCallback;
import top.zcwfeng.libpermissionhelper.PermissionHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PermissionHelper.requestPermission(this, new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE},
                CommonConfig.RESULT_CODE_PERMISSION_WRITE_EXTERNAL_STORAGE);

        Timber.tag("zzzzzz");
        Timber.d("Activity Created");

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        PermissionHelper.onRequestPermissionsResult(this,requestCode,permissions,grantResults);
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }

    @PermissionGrant(CommonConfig.RESULT_CODE_PERMISSION_WRITE_EXTERNAL_STORAGE)
    public void onRequestWriteExternalStorageGranted(String[] pemissions){
        Toast.makeText(this,"授予权限成功",Toast.LENGTH_SHORT).show();
    }

    @PermissionRational(CommonConfig.RESULT_CODE_PERMISSION_WRITE_EXTERNAL_STORAGE)
    public void onRequestWriteExternalStorageRational(String[] permisions, final PermissionCallback callback){

        StringBuilder builder = new StringBuilder();
        for(String permission:permisions){
            System.out.println(permission+"\n");
            builder.append(permission + "\n");

        }
        AlertDialog.Builder builder2 = new AlertDialog.Builder(this);

        builder2.setMessage("很遗憾，一下权限被拒绝，功能无法继续使用，请到达应用程序xxx,设置相应权限\n\n"+builder.toString())
                .setPositiveButton("好的", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if(callback != null) {
                            callback.onRationalExecute();

                        }

                        dialogInterface.dismiss();

                    }
                });
        builder2.create().show();

    }


    @PermissionDenied(CommonConfig.RESULT_CODE_PERMISSION_WRITE_EXTERNAL_STORAGE)
    public void onRequestWriteExternalStorageDenied(String[] deniedPermission){
        Toast.makeText(this,"权限被拒绝",Toast.LENGTH_SHORT).show();
        StringBuilder builder = new StringBuilder();
        for(String permission:deniedPermission){
            System.out.println(permission+"\n");
            builder.append(permission + "\n");

        }


        new AlertDialog.Builder(this)
                .setMessage("请授权以继续使用\n\n设备存储权限\n\n" + builder.toString())
                .setPositiveButton("好的", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                }).setCancelable(true).show();
    }
}
