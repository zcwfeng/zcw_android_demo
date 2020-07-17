package top.zcwfeng.customui.baseui.view.finsh

import android.graphics.*
import android.graphics.drawable.Drawable
import java.util.*
import kotlin.math.cos
import kotlin.math.sin

class PeopleDrawable : Drawable() {
    private val HEAD_RADIUS = 100f
    private val OTHER_ALPHA = 160
    private var mPaint: Paint = Paint()
    private var mPath: Path = Path()
    private var middlePoint: PointF
    private var neckPointCenter: PointF
    private var headPoint: PointF

    //    private val mainAngle = Random().nextFloat() * 360 //角度表示的角
    private val mainAngle = 0f //角度表示的角


    init {
        mPaint.style = Paint.Style.FILL
        mPaint.strokeWidth = 3f
        mPaint.isDither = true //防抖
        mPaint.color = Color.argb(OTHER_ALPHA, 244, 92, 71)
        middlePoint = PointF(4.18f * HEAD_RADIUS, 4.18f * HEAD_RADIUS)
        //头
        headPoint = calculatPoint(middlePoint, HEAD_RADIUS, mainAngle)
        // 脖子
        neckPointCenter = calculatPoint(middlePoint, HEAD_RADIUS * 2, mainAngle - 180)

    }

    override fun draw(canvas: Canvas) {

        canvas.drawCircle(headPoint.x, headPoint.y, HEAD_RADIUS, mPaint)

        val point1 = calculatPoint(headPoint, HEAD_RADIUS * 1.2f, mainAngle - 90);
        val point2 = calculatPoint(neckPointCenter, HEAD_RADIUS * 1.7f, mainAngle - 90);
        val point3 = calculatPoint(neckPointCenter, HEAD_RADIUS * 1.7f, mainAngle + 90);
        val point4 = calculatPoint(headPoint, HEAD_RADIUS * 1.2f, mainAngle + 90);

        val point5 = calculatPoint(point2,HEAD_RADIUS* 5f,mainAngle)
        val point6 = calculatPoint(point3,HEAD_RADIUS* 5f,mainAngle)
        val point7 = calculatPoint(point2,HEAD_RADIUS* 5f,mainAngle+150)
        val point8 = calculatPoint(point3,HEAD_RADIUS* 5f,mainAngle-150)
        mPath.moveTo(headPoint.x, headPoint.y)
        mPaint.color = Color.BLUE
        mPath.lineTo(point2.x, point2.y)
        mPath.lineTo(point3.x, point3.y)
        canvas.drawPath(mPath, mPaint)
        canvas.drawLine(point2.x,point2.y,point5.x,point5.y,mPaint)
        canvas.drawLine(point3.x,point3.y,point6.x,point6.y,mPaint)

        canvas.drawLine(point2.x-20,point2.y-20,point7.x,point7.y,mPaint)
        canvas.drawLine(point3.x-20,point3.y-20,point8.x,point8.y,mPaint)

    }

    override fun setAlpha(alpha: Int) {
        mPaint?.alpha = alpha
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        mPaint?.colorFilter = colorFilter
    }

    override fun getIntrinsicHeight(): Int {
        return (8.38f * HEAD_RADIUS).toInt()
    }

    override fun getIntrinsicWidth(): Int {
        return (8.38f * HEAD_RADIUS).toInt()
    }


    /**
     * 输入起点、长度、旋转角度计算终点
     * @param startPoint 起点
     * @param length 长度
     * @param angle 旋转角度
     * @return 计算结果点
     */
    private fun calculatPoint(startPoint: PointF, length: Float, angle: Float): PointF {
        val deltaX = cos(Math.toRadians(angle.toDouble())).toFloat() * length
        //符合Android坐标的y轴朝下的标准
        val deltaY = sin(Math.toRadians(angle - 180.toDouble())).toFloat() * length
        return PointF(startPoint.x + deltaX, startPoint.y + deltaY)
    }
}