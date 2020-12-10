package top.zcwfeng.opengl.face;

/**
 * 所有的xy坐标都是我们送去定位的图片，的宽高（480x640我们这里）
 */
public class Face {

    public int width;
    public int height;
    public int imgWidth;
    public int imgHeight;
    public int x;
    public int y;
    public float left_x;
    public float left_y;
    public float right_x;
    public float right_y;


    public float nose_x;
    public float nose_y;
    public float mouseLeft_x;
    public float mouseLeft_y;
    public float mouseRight_x;
    public float mouseRight_y;


    public Face(int width, int height, int imgWidth, int imgHeight, int x, int y, float left_x, float left_y, float right_x, float right_y) {
        this.width = width;
        this.height = height;
        this.imgWidth = imgWidth;
        this.imgHeight = imgHeight;
        this.x = x;
        this.y = y;
        this.left_x = left_x;
        this.left_y = left_y;
        this.right_x = right_x;
        this.right_y = right_y;
    }

    @Override
    public String toString() {
        return "Face{" +
                "width=" + width +
                ", height=" + height +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
