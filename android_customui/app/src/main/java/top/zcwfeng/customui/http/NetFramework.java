package top.zcwfeng.customui.http;

public class NetFramework {
    /**
     * 请求，接受
     * @param url
     * @param requestParams
     * @param response
     * @param <T>
     * @param <M>
     */
    public static<T,M> void sendJsonRequest(String url,T requestParams,Class<M> response,IDataListener iDataListener){
        IHttpRequest iHttpRequest = new JsonHttpRequest();
        IHttpListener iHttpListener = new JsonHttpListener<>(response,iDataListener);
        HttpTask httpTask = new HttpTask(url,requestParams,iHttpRequest,iHttpListener);
        ThreadManager.getInstance().addTask(httpTask);
    };

    public static void sendXmlRequest() {

    }

    public static void sendProtobufRequest() {

    }
}
