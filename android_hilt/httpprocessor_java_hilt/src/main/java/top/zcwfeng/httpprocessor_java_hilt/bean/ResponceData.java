package top.zcwfeng.httpprocessor_java_hilt.bean;

public class ResponceData {
    /**
     * result : null
     * reason : 当前可请求的次数不足
     * error_code : 10012
     * resultcode : 112
     */
    private String result;
    private String reason;
    private int error_code;
    private String resultcode;

    public void setResult(String result) {
        this.result = result;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }

    public String getResult() {
        return result;
    }

    public String getReason() {
        return reason;
    }

    public int getError_code() {
        return error_code;
    }

    public String getResultcode() {
        return resultcode;
    }
}
