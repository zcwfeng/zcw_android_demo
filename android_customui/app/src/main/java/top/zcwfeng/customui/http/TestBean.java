package top.zcwfeng.customui.http;

import com.google.gson.annotations.SerializedName;

public class TestBean {

    /**
     * args : {}
     * headers : {"Accept":"application/json","Host":"www.httpbin.org","User-Agent":"curl/7.54.0","X-Amzn-Trace-Id":"Root=1-5edf1187-209c6d919bd120432ed84a36"}
     * origin : 111.193.21.74
     * url : http://www.httpbin.org/get
     */

    private ArgsBean args;
    private HeadersBean headers;
    private String origin;
    private String url;

    public ArgsBean getArgs() {
        return args;
    }

    public void setArgs(ArgsBean args) {
        this.args = args;
    }

    public HeadersBean getHeaders() {
        return headers;
    }

    public void setHeaders(HeadersBean headers) {
        this.headers = headers;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static class ArgsBean {
    }

    public static class HeadersBean {
        /**
         * Accept : application/json
         * Host : www.httpbin.org
         * User-Agent : curl/7.54.0
         * X-Amzn-Trace-Id : Root=1-5edf1187-209c6d919bd120432ed84a36
         */

        private String Accept;
        private String Host;
        @SerializedName("User-Agent")
        private String UserAgent;
        @SerializedName("X-Amzn-Trace-Id")
        private String XAmznTraceId;

        public String getAccept() {
            return Accept;
        }

        public void setAccept(String Accept) {
            this.Accept = Accept;
        }

        public String getHost() {
            return Host;
        }

        public void setHost(String Host) {
            this.Host = Host;
        }

        public String getUserAgent() {
            return UserAgent;
        }

        public void setUserAgent(String UserAgent) {
            this.UserAgent = UserAgent;
        }

        public String getXAmznTraceId() {
            return XAmznTraceId;
        }

        public void setXAmznTraceId(String XAmznTraceId) {
            this.XAmznTraceId = XAmznTraceId;
        }
    }

    @Override
    public String toString() {
        return "TestBean{" +
                "args=" + args +
                ", headers=" + headers +
                ", origin='" + origin + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
