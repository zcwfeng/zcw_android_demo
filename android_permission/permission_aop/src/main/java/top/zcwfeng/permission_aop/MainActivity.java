package top.zcwfeng.permission_aop;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import top.zcwfeng.permission_aop.annotation.Permission;
import top.zcwfeng.permission_aop.annotation.PermissionCancel;
import top.zcwfeng.permission_aop.annotation.PermissionDenied;
/**

 要写权限申请库？
 权限申请这一件事情，应该独立处理，隔离出来。
 不要让Activity或Fragment有着高耦合度。
 抛砖引玉，以后同学们拥有打造自己其他库的能力。

 使用者：
 只需要关心，用一个注解，把具体权限传递到注解中就OK了，其他什么都不需要管

 */

/**
 * TODO 用户的角度上  use 我们写的权限申请库
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void test(View view) {}

    // 点击事件
    public void permissionRequestTest(View view) {
        testRequest();
    }

    // 申请权限  函数名可以随意些
    @Permission(value = Manifest.permission.READ_EXTERNAL_STORAGE, requestCode = 200)
    public void testRequest() {
        Toast.makeText(this, "权限申请成功...", Toast.LENGTH_SHORT).show();
        // 100 行
    }

    // 权限被取消  函数名可以随意些
    @PermissionCancel
    public void testCancel() {
        Toast.makeText(this, "权限被拒绝", Toast.LENGTH_SHORT).show();
    }

    // 多次拒绝，还勾选了“不再提示”
    @PermissionDenied
    public void testDenied() {
        Toast.makeText(this, "权限被拒绝(用户勾选了 不再提示)，注意：你必须要去设置中打开此权限，否则功能无法使用", Toast.LENGTH_SHORT).show();
    }
}
