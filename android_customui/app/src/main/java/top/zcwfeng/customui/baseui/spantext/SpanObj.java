package top.zcwfeng.customui.baseui.spantext;

/**
 * 作者：zcw on 2019-10-30
 */
public class SpanObj {

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    private String color;
    private int size = 14;
    private String text;
}
