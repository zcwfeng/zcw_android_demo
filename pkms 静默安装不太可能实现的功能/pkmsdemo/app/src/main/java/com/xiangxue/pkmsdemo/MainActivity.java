package com.xiangxue.pkmsdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.IPackageInstallObserver;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Xiangxue";

    @BindView(R.id.btn_install)
    Button btnInstall;

    private MyInstallReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //注册apk安装监听
        receiver = new MyInstallReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.PACKAGE_ADDED");
        filter.addAction("android.intent.action.PACKAGE_REMOVED");
        filter.addDataScheme("package");
        this.registerReceiver(receiver, filter);

    }

    // 需要增加 3个aidl,PackageManager等系统源码才行
    // 同学们注意：这是直接调用PKMS.installPackage方法安装
    // 这种安装的方式，都需要在AndroidManifest.xml中 声明 android:sharedUserId="android.uid.system"
    @OnClick(R.id.btn_install)
    public void onViewClicked() {

        String fileName = Environment.getExternalStorageDirectory() + File.separator + File.separator + "wms.apk";
        Uri uri = Uri.fromFile(new File(fileName));
        int installFlags = 0;
        PackageManager pm = getPackageManager();
        try {
            PackageInfo pi = pm.getPackageInfo("com.xiangxue.pkmsdemo", PackageManager.GET_UNINSTALLED_PACKAGES);
            if (pi != null) {
                installFlags |= PackageManager.INSTALL_REPLACE_EXISTING;
            }
        } catch (PackageManager.NameNotFoundException e) {
        }
        MyPakcageInstallObserver observer = new MyPakcageInstallObserver();
        pm.installPackage(uri, observer, installFlags, "com.zero.wmsdemo");

    }

    // 需要增加 3个aidl,PackageManager等系统源码才行
    // 同学们注意：这种就是要去调用到 ADB 服务 来安装
    // 这种安装的方式，都需要在AndroidManifest.xml中 声明 android:sharedUserId="android.uid.system"
    public static boolean silentInstall(String pkgName, String apkAbsolutePath) {
        boolean isSuccess = false;
        String[] args = {"pm", "install", "-r", "-d", "-i", pkgName, "--user", "0", apkAbsolutePath};
        ProcessBuilder processBuilder = new ProcessBuilder(args);
        Process process = null;
        InputStream inIs = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            process = processBuilder.start();
            baos.write('/');
            inIs = process.getInputStream();
            byte[] b = new byte[1024];
            while (inIs.read(b) != -1) {
                baos.write(b);
            }
            String res = new String(baos.toByteArray(), "utf-8");
            isSuccess = res.contains("Success");
            baos.close();
        } catch (Exception e) {
            Log.i(TAG, "silentInstall end" + e.toString());
            e.printStackTrace();
        } finally {
            try {
                if (inIs != null) {
                    inIs.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (process != null) {
                process.destroy();
            }
        }
        Log.i(TAG, "silentInstall end isSuccess" + isSuccess);
        return isSuccess;
    }

    @OnClick(R.id.btn_install)
    public void onViewSmartClicked() {
        smartInstall();
    }


    // 智能安装
    // 智能安装比较常用的案例就是，微信抢红包，自动去点击 打开红包

    // 什么是智能安装？ 就是通过 AccessibilityService辅助服务 来帮我们自动点击 确定 下一步 下一步 安装完成
    // 这个也是扯淡的，很多手机都需要输入密码，那还有什么智能的
    private void smartInstall() {
        String fileName = Environment.getExternalStorageDirectory() + File.separator + File.separator + "wms.apk";
        Uri uri = Uri.fromFile(new File(fileName));
        Intent localIntent = new Intent(Intent.ACTION_VIEW);
        localIntent.setDataAndType(uri, "application/vnd.android.package-archive");
        startActivity(localIntent);
    }

    // 监听apk安装
    private class MyInstallReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.intent.action.PACKAGE_ADDED")) {// install
                String packageName = intent.getDataString();
                Log.i(TAG, "安装了 :" + packageName);
            }
            if (intent.getAction().equals("android.intent.action.PACKAGE_REMOVED")) { // uninstall
                String packageName = intent.getDataString();
                Log.i(TAG, "卸载了 :" + packageName);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (receiver != null) {
            unregisterReceiver(receiver);
        }
    }




/*静默安装回调*/
class MyPakcageInstallObserver extends IPackageInstallObserver.Stub {

    @Override
    public void packageInstalled(String packageName, int returnCode) {
        if (returnCode == 1) {
            Log.e("Zero", "安装成功");
        } else {
            Log.e("Zero", "安装失败,返回码是:" + returnCode);
        }
    }
}
}
