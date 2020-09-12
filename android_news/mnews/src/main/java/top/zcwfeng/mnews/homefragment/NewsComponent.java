package top.zcwfeng.mnews.homefragment;


import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.billy.cc.core.component.IComponent;

import top.zcwfeng.mnews.homefragment.headlinenews.HeadlineNewsFragment;


public
class NewsComponent implements IComponent {
    @Override
    public String getName() {
        return "Mnews";
    }

    @Override
    public boolean onCall(CC cc) {
       String actionName =  cc.getActionName();
        switch (actionName) {
            case "getHeadlineNewsFragment":
                CCResult result = new CCResult();
                result.addData("fragment", new HeadlineNewsFragment());
                CC.sendCCResult(cc.getCallId(), result);
                return true;
            default:
                //其它actionName当前组件暂时不能响应，可以通过如下方式返回状态码为-12的CCResult给调用方
                CC.sendCCResult(cc.getCallId(), CCResult.errorUnsupportedActionName());
                return false;
        }
    }
}
