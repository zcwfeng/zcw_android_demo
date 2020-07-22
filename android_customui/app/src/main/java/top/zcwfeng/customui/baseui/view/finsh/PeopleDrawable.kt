package top.zcwfeng.customui.baseui.view.finsh

import android.graphics.*
import android.graphics.drawable.Drawable
import kotlin.math.cos
import kotlin.math.sin

class PeopleDrawable : Drawable() {
    private val HEAD_RADIUS = 100f
    private val OTHER_ALPHA = 160
    private var mPaint: Paint = Paint()
    private var mPath: Path = Path()
    private var middlePoint: PointF
    private var neckPointCenter: PointF
    private var earthPoint: PointF //

    //    private val mainAngle = Random().nextFloat() * 360 //角度表示的角
    private val mainAngle = 0f //角度表示的角


    init {
        mPaint.style = Paint.Style.FILL
        mPaint.strokeWidth = 3f
        mPaint.isDither = true //防抖
        mPaint.color = Color.argb(OTHER_ALPHA, 244, 92, 71)
        middlePoint = PointF(4.18f * HEAD_RADIUS, 4.18f * HEAD_RADIUS)
        //地球
        earthPoint = calculatPoint(middlePoint, HEAD_RADIUS * 1.8f, mainAngle)
        // 月球
        neckPointCenter = calculatPoint(middlePoint, HEAD_RADIUS * 1.2f, mainAngle - 60)

    }

    override fun draw(canvas: Canvas) {

        mPaint.color = Color.RED
        canvas.drawCircle(middlePoint.x,middlePoint.y,HEAD_RADIUS * 0.5f,mPaint)
        mPaint.color = Color.GRAY
        canvas.drawCircle(neckPointCenter.x,neckPointCenter.y,HEAD_RADIUS * 0.3f,mPaint)
        mPaint.color = Color.BLUE
        canvas.drawCircle(earthPoint.x,earthPoint.y,HEAD_RADIUS * 0.45f,mPaint)

        // 贝塞尔曲线的两个点
        val left = calculatPoint(middlePoint,HEAD_RADIUS * 3.4f,mainAngle - 130)
        val leftControllPoint = calculatPoint(left,HEAD_RADIUS * 4.1f,mainAngle - 60)
        val rightControllPoint = calculatPoint(left,HEAD_RADIUS * 5.0f,mainAngle + 110)

        mPaint.color = Color.GREEN
        mPaint.style = Paint.Style.STROKE
        mPath.reset()
        mPath.moveTo(left.x,left.y)
        mPath.quadTo(leftControllPoint.x,leftControllPoint.y,earthPoint.x,earthPoint.y)
        mPath.quadTo(rightControllPoint.x,rightControllPoint.y,left.x,left.y)
        canvas.drawPath(mPath,mPaint)


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
        // x坐标
        val deltaX = (Math.cos(Math.toRadians(angle.toDouble())) * length).toFloat()
        // y坐标
        // y坐标
        val deltaY = (Math.sin(Math.toRadians(angle - 180.toDouble())) * length).toFloat()

        return PointF(startPoint.x + deltaX, startPoint.y + deltaY)
    }
}