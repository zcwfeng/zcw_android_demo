package top.zcwfeng.testflutterplugin;

import androidx.annotation.NonNull;

import io.flutter.embedding.android.FlutterFragment;

public class MyFlutterFragment extends FlutterFragment {
  @Override
  @NonNull
  public String getDartEntrypointFunctionName() {
    return "myMainDartMethod";
  }
}