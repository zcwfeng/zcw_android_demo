package top.zcwfeng.materialdesign.coordinator.bean;


import java.util.ArrayList;
import java.util.List;

import top.zcwfeng.materialdesign.R;


public class AuthorInfo {

    private int portrait = R.drawable.xiaoxin;

    private String nickName;

    private String motto;


    public int getPortrait() {
        return portrait;
    }

    public void setPortrait(int portrait) {
        this.portrait = portrait;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getMotto() {
        return motto;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }

    /**
     * 生成一些测试数据
     *
     * @return
     */
    public static List<AuthorInfo> createTestData() {
        List<AuthorInfo> authorInfoList = new ArrayList<>();
        for (int count = 0; count < 60; count++) {
            AuthorInfo authorInfo1 = new AuthorInfo();
            authorInfo1.setMotto("美女，喜欢吃青椒吗？");
            authorInfo1.setNickName("蜡笔小新");
            authorInfoList.add(authorInfo1);

            AuthorInfo authorInfo2 = new AuthorInfo();
            authorInfo2.setMotto("我是要成为火影的男人");
            authorInfo2.setNickName("鸣人");
            authorInfo2.setPortrait(R.drawable.mingren);
            authorInfoList.add(authorInfo2);

            AuthorInfo authorInfo3 = new AuthorInfo();
            authorInfo3.setMotto("触碰万物之理，能控制森罗万象");
            authorInfo3.setNickName("六道仙人");
            authorInfoList.add(authorInfo3);
        }
        return authorInfoList;
    }
}
