package top.zcwfeng.usedatas;

import com.google.gson.annotations.Since;
import com.google.gson.annotations.Until;

public class UserSinceUntil {
    @Since(1.4)
    private String a;

    @Since(1.6)
    private String b;

    @Since(1.8)
    private String c;

    @Until(1.6)
    private String d;

    @Until(2.0)
    private String e;

    public UserSinceUntil(String a, String b, String c, String d, String e) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
    }

    @Override
    public String toString() {
        return "User{" +
                "a='" + a + '\'' +
                ", b='" + b + '\'' +
                ", c='" + c + '\'' +
                ", d='" + d + '\'' +
                ", e='" + e + '\'' +
                '}';
    }
}
