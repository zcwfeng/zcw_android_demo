// IWebviewProcessToMainProcessInterface.aidl
package top.zcwfeng.webview;
import top.zcwfeng.webview.ICallbackFromMainprocessToWebViewProcessInterface;

// Declare any non-default types here with import statements

interface IWebviewProcessToMainProcessInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
//    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
//            double aDouble, String aString);
      void handleWebCommand(String commandName,String jsonParam,in ICallbackFromMainprocessToWebViewProcessInterface callback);

}
