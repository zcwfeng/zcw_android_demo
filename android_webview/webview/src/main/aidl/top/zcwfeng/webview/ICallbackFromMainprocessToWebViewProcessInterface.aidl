// ICallbackFromMainprocessToWebViewProcessInterface.aidl
package top.zcwfeng.webview;

// Declare any non-default types here with import statements

interface ICallbackFromMainprocessToWebViewProcessInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
//    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
//            double aDouble, String aString);
    void onResult(String callbackname,String response);
}
