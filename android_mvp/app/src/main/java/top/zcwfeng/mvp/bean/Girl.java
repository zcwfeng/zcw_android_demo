package top.zcwfeng.mvp.bean;

public class Girl {
    private String name;

    public Girl(String name,String url){
        this.name = name;
        this.url = url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    private String url;
}
