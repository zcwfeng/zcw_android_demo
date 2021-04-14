package top.zcwfeng.jni;
import android.util.Log;
public class Dog {
    public Dog() {
        Log.d("Dog", "Dog init...");
    }

    public Dog(int a1) {
        Log.d("Dog", "Dog init...:"+a1);
    }

    public Dog(int a1,int a2) {
        Log.d("Dog", "Dog init..."+String.format("a1=%d,a2=%d", a1,a2));
    }

    public Dog(int a1,int a2,int a3) {
        Log.d("Dog", "Dog init..."+String.format("a1=%d,a2=%d,a3=%d", a1,a2,a3));
    }
}