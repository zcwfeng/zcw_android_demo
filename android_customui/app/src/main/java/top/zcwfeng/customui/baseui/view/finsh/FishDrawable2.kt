package top.zcwfeng.customui.baseui.view.finsh

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.animation.LinearInterpolator
import kotlin.random.Random

class FishDrawable2 : Drawable() {

    private val mPath: Path = Path()
    private val mPaint: Paint = Paint()

    var frequence = 1f

    // 大鱼的重心,身体上的中心点
    val middlePoint: PointF

    // 鱼头的半径
    val HEAD_RADIUS: Float = 100f

    // 大鱼身体长度
    private val BODY_LENGTH = 3.2f * HEAD_RADIUS

    // 透明度
    private val OTHER_ALPHA = 110

    // 找鱼鳍起始点坐标的线长
    private val FIND_FINS_LENGTH = 0.9f * HEAD_RADIUS

    // 鱼鳍根部的宽度
    private val FINS_LENGTH = 1.3f * HEAD_RADIUS

    // 大圆的半径
    public val BIG_CIRCLE_RADIUS = 0.7f * HEAD_RADIUS

    // 中圆的半径
    private val MIDDLE_CIRCLE_RADIUS = 0.6f * BIG_CIRCLE_RADIUS

    // 小圆半径
    private val SMALL_CIRCLE_RADIUS = 0.4f * MIDDLE_CIRCLE_RADIUS

    // --寻找尾部中圆圆心的线长
    private val FIND_MIDDLE_CIRCLE_LENGTH = BIG_CIRCLE_RADIUS * (0.6f + 1)

    // --寻找尾部小圆圆心的线长
    private val FIND_SMALL_CIRCLE_LENGTH = MIDDLE_CIRCLE_RADIUS * (0.4f + 2.7f)

    // --寻找大三角形底边中心点的线长
    private val FIND_TRIANGLE_LENGTH = MIDDLE_CIRCLE_RADIUS * 2.7f

    // 鱼头朝向的角度
    var fishMainAngel: Float = 90f;//40

    private var currentValue = 0f

    public lateinit var headPoint: PointF

    // 鱼鳍振幅角度
    var finsAngle: Float = 1f
        set(value) {
            Log.e("zcw:::","Float::$value")
            field = value * 30
        }
    var finsObjAnim: ObjectAnimator

    init {
        mPaint.isAntiAlias = true
        mPaint.style = Paint.Style.FILL
        // 防止抖动
        mPaint.isDither = true
        mPaint.setARGB(OTHER_ALPHA, 255, 0, 0)
        middlePoint = PointF(4.19f * HEAD_RADIUS, 4.19f * HEAD_RADIUS)

        //鱼鳍动画
        finsObjAnim = ObjectAnimator.ofFloat(this, "finsAngle", 0f, 1f, 0f)
        finsObjAnim.repeatMode = ValueAnimator.REVERSE
        finsObjAnim.repeatCount = 10


        val valueAnimator = ValueAnimator.ofFloat(0f, 3600f);
        valueAnimator.duration = 15 * 1000;
        valueAnimator.repeatMode = ValueAnimator.RESTART
        valueAnimator.repeatCount = ValueAnimator.INFINITE
        valueAnimator.interpolator = LinearInterpolator()
        valueAnimator.addUpdateListener {
            currentValue = it.animatedValue as Float

            invalidateSelf();
        }
        valueAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationRepeat(animation: Animator?) {
                super.onAnimationRepeat(animation)
                finsObjAnim.start()
                Log.e("zcw:::","finsObjAnim.start()----FishDrawable2")
            }
        })

        valueAnimator.start()

    }

    override fun draw(canvas: Canvas) {

        var fishAngel = fishMainAngel + Math.sin(Math.toRadians(currentValue * 1.2 * frequence)).toFloat() * 10
        // 鱼头中心点
        headPoint = caculatePoint(middlePoint, BODY_LENGTH / 2, fishAngel)
        canvas.drawCircle(headPoint.x, headPoint.y, HEAD_RADIUS, mPaint)

        // 画右鱼鳍
        val rightFinsPoint = caculatePoint(headPoint, FIND_FINS_LENGTH, fishAngel - 110)
        makefins(canvas, rightFinsPoint, fishAngel, true)

        // 画左鱼鳍
        val leftFinsPoint = caculatePoint(headPoint, FIND_FINS_LENGTH, fishAngel + 110)
        makefins(canvas, leftFinsPoint, fishAngel, false)

        // 节肢 梯形底边终点
        val bodyBottomCenterPoint = caculatePoint(headPoint, BODY_LENGTH, fishAngel - 180)
        // 节肢1
        val segmentSmallCirclePoint = makeSegment(canvas, bodyBottomCenterPoint, BIG_CIRCLE_RADIUS, MIDDLE_CIRCLE_RADIUS, FIND_MIDDLE_CIRCLE_LENGTH, fishAngel, true)
        // 节肢2
        makeSegment(canvas, segmentSmallCirclePoint, MIDDLE_CIRCLE_RADIUS, SMALL_CIRCLE_RADIUS, FIND_SMALL_CIRCLE_LENGTH, fishAngel, false)

        // 尾巴
        val findEdgeLength = Math.abs(Math.sin(Math.toRadians(currentValue * 1.5 * frequence)) * BIG_CIRCLE_RADIUS).toFloat()


        makeTriangle(canvas, segmentSmallCirclePoint,
                FIND_TRIANGLE_LENGTH, findEdgeLength, fishAngel)
        makeTriangle(canvas, segmentSmallCirclePoint,
                FIND_TRIANGLE_LENGTH - 10, BIG_CIRCLE_RADIUS - 20, fishAngel)

        makeBody(canvas, headPoint, segmentSmallCirclePoint, fishAngel)

    }

    private fun makeBody(canvas: Canvas, headPoint: PointF, segmentSmallCirclePoint: PointF, fishAngel: Float) {
        // 画贝塞尔曲线，求身体四个点
        val topLeftPoint = caculatePoint(headPoint, BIG_CIRCLE_RADIUS, fishAngel + 90)
        val topRightPoint = caculatePoint(headPoint, BIG_CIRCLE_RADIUS, fishAngel - 90)
        val bottomLeftPoint = caculatePoint(segmentSmallCirclePoint,
                MIDDLE_CIRCLE_RADIUS, fishAngel + 90)
        val bottomRightPoint = caculatePoint(segmentSmallCirclePoint,
                MIDDLE_CIRCLE_RADIUS, fishAngel - 90)

        // 找贝塞尔曲线控制点

        val leftControllPoint = caculatePoint(headPoint, BODY_LENGTH * 0.6f, fishAngel + 130)
        val rightControllPoint = caculatePoint(headPoint, BODY_LENGTH * 0.6f, fishAngel - 130)

        mPath.reset()
        mPath.moveTo(topLeftPoint.x, topLeftPoint.y)
        mPath.quadTo(leftControllPoint.x, leftControllPoint.y, bottomLeftPoint.x, bottomLeftPoint.y)
        mPath.lineTo(bottomRightPoint.x, bottomRightPoint.y)
        mPath.quadTo(rightControllPoint.x, rightControllPoint.y, topRightPoint.x, topRightPoint.y)
        canvas.drawPath(mPath, mPaint)

    }

    private fun makeTriangle(canvas: Canvas, startPoint: PointF, findCenterLen: Float, findEdgeLen: Float, fishAngel: Float) {

        val triangleAngel = fishAngel + Math.sin(Math.toRadians(currentValue * 1.5 * frequence)).toFloat() * 35.0f
        // 三角形底边终点
        val centerPoint = caculatePoint(startPoint, findCenterLen, triangleAngel - 180)
        val leftPoint = caculatePoint(centerPoint, findEdgeLen, triangleAngel + 90)
        val rightPoint = caculatePoint(centerPoint, findEdgeLen, triangleAngel - 90)

        mPath.reset()
        mPath.moveTo(startPoint.x, startPoint.y)
        mPath.lineTo(leftPoint.x, leftPoint.y)
        mPath.lineTo(rightPoint.x, rightPoint.y)
        canvas.drawPath(mPath, mPaint)

    }

    private fun makeSegment(canvas: Canvas, bottomCenterPoint: PointF, bigRadias: Float, smallRadias: Float, findSmallCircleLength: Float, fishAngel: Float, hasBig: Boolean): PointF {

        val setmentAngel = when (hasBig) {
            true -> fishAngel + Math.cos(Math.toRadians(currentValue * 1.5 * frequence)).toFloat() * 15
            else -> fishAngel + Math.sin(Math.toRadians(currentValue * 1.5 * frequence)).toFloat() * 35
        }


        // 截肢梯形顶边中心点,底部圆心
//        val upperCeterPoint = caculatePoint(bottomCenterPoint,bigRadias+ smallRadias,fishAngel -180)
        val upperCeterPoint = caculatePoint(bottomCenterPoint, findSmallCircleLength, setmentAngel - 180)
        // 梯形十个点
        val bottomLeftPoint = caculatePoint(bottomCenterPoint, bigRadias, setmentAngel + 90)
        val bottomRightPoint = caculatePoint(bottomCenterPoint, bigRadias, setmentAngel - 90)
        val upLeftPoint = caculatePoint(upperCeterPoint, smallRadias, setmentAngel + 90)
        val upRightPoint = caculatePoint(upperCeterPoint, smallRadias, setmentAngel - 90)

        if (hasBig) {
            canvas.drawCircle(bottomCenterPoint.x, bottomCenterPoint.y, bigRadias, mPaint)
        }

        canvas.drawCircle(upperCeterPoint.x, upperCeterPoint.y, smallRadias, mPaint)

        mPath.reset()
        mPath.moveTo(bottomLeftPoint.x, bottomLeftPoint.y)
        mPath.lineTo(bottomRightPoint.x, bottomRightPoint.y)
        mPath.lineTo(upRightPoint.x, upRightPoint.y)
        mPath.lineTo(upLeftPoint.x, upLeftPoint.y)
        canvas.drawPath(mPath, mPaint)

        return upperCeterPoint;


    }

    private fun makefins(canvas: Canvas, startPoint: PointF, fishAngel: Float, isRight: Boolean) {
        val controlAngel = 115
        // 鱼鳍的终点---二阶贝塞尔曲线的终点
        val endPoint = when (isRight) {
            true -> caculatePoint(startPoint, FINS_LENGTH,
                    fishAngel - 180 - finsAngle)
            else ->
                caculatePoint(startPoint, FINS_LENGTH,
                        fishAngel + 180 + finsAngle)
        }
        // 控制点
        val controllPoint =
                when (isRight) {
                    true -> caculatePoint(startPoint, FINS_LENGTH * 1.8f,
                            fishAngel - controlAngel - finsAngle)
                    else ->
                        caculatePoint(startPoint, FINS_LENGTH * 1.8f,
                                fishAngel + controlAngel + finsAngle)
                }


        //绘制开始
        mPath.reset()
        //移动到起始点
        mPath.moveTo(startPoint.x, startPoint.y)
        // 画二阶贝塞尔曲线
        mPath.quadTo(controllPoint.x, controllPoint.y, endPoint.x, endPoint.y)



        canvas.drawPath(mPath, mPaint)
//        Log.e("zcw:::","finsAngel::$fishAngel")


    }

    override fun setAlpha(alpha: Int) {
        mPaint.alpha = alpha
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        mPaint.colorFilter = colorFilter
    }

    override fun getIntrinsicHeight(): Int {
        return (8.38f * HEAD_RADIUS).toInt()
    }

    override fun getIntrinsicWidth(): Int {
        return (8.38f * HEAD_RADIUS).toInt()
    }

    /**
     * @param startPoint 起始点坐标
     * @param length     要求的点到起始点的直线距离 -- 线长
     * @param angle      鱼当前的朝向角度
     * @return
     */
    fun caculatePoint(startPoint: PointF, length: Float, angle: Float): PointF {
//        // x 坐标
//        val detalX = cos(angle) * length
//        // y 坐标
//        val detalY = sin(angle - 180) * length
//        return PointF(startPoint.x + detalX, startPoint.y + detalY)

        // x坐标

        // x坐标
        val deltaX = (Math.cos(Math.toRadians(angle.toDouble())) * length).toFloat()
        // y坐标
        // y坐标
        val deltaY = (Math.sin(Math.toRadians(angle - 180.toDouble())) * length).toFloat()

        return PointF(startPoint.x + deltaX, startPoint.y + deltaY)
    }


}