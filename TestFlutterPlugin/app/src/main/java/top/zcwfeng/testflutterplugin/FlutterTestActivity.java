package top.zcwfeng.testflutterplugin;


import android.os.Bundle;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.engine.dart.DartExecutor;
import io.flutter.view.FlutterMain;

public class FlutterTestActivity extends FlutterActivity {

    @Override
    protected String getDartEntrypoint() {
        return "myMainDartMethod";
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FlutterEngine flutterEngine = new FlutterEngine(MyApplication.getInstance());
        flutterEngine
                .getDartExecutor()
                .executeDartEntrypoint(
                        new DartExecutor.DartEntrypoint(MyApplication.getInstance().getAssets(),
                                FlutterMain.findAppBundlePath(MyApplication.getInstance()),
                                "myMainDartMethod"
                        )
                );
        super.onCreate(savedInstanceState);



    }


}
