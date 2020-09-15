package top.zcwfeng.customwebview;

// Declare any non-default types here with import statements
import top.zcwfeng.customwebview.IWebAidlCallback;

interface IWebAidlInterface {
     /**
      * actionName: 不同的action， jsonParams: 需要根据不同的action从map中读取并依次转成其他
      */
      void handleWebAction(int level, String actionName, String jsonParams, in IWebAidlCallback callback);
}
