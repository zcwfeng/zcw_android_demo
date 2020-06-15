import org.junit.Test;

import top.zcwfeng.customui.http.IDataListener;
import top.zcwfeng.customui.http.NetFramework;

public class MyTest {
    @Test
    public void TestHttp(){

        String url ="https://www.baidu.com";
        NetFramework.sendJsonRequest(url, "", String.class, new IDataListener<String>() {


            @Override
            public void onSuccess(String s) {
                System.out.println(s);
            }

            @Override
            public void onFailed() {
                System.out.println("--------onFailed");
            }
        });
        System.out.println("sdfasf");

    }
}
