package top.zcwfeng.usedatas;

import com.google.gson.annotations.Expose;

public class UserExpose {

    @Expose(serialize = true, deserialize = true)   //序列化和反序列化都生效
    private String a;

    @Expose(serialize = false, deserialize = true)  //序列化时不生效，反序列化时生效
    private String b;

    @Expose(serialize = true, deserialize = false)  //序列化时生效，反序列化时不生效
    private String c;

    @Expose(serialize = false, deserialize = false) //序列化和反序列化都不生效，和不写注解一样
    private String d;

    private String e;

    public UserExpose(String a, String b, String c, String d, String e) {
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
