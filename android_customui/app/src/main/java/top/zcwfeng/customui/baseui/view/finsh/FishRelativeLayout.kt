package top.zcwfeng.customui.baseui.view.finsh

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.annotation.RequiresApi
import kotlin.random.Random
import kotlin.random.nextInt

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class FishRelativeLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int = 0, defStyleRes: Int = 0)
    : RelativeLayout(context, attrs, defStyleAttr, defStyleRes) {
    private var mAlpha: Int = 100
    private var ripple: Float = 0.0f
        set(value) {
            mAlpha = ((1 - value) * 100).toInt()
            field = value
        }

    private val fishDrawable = FishDrawable2()
    private val ivFish = ImageView(context)
    private val mPaint = Paint()
    private var touchX: Float = 0f
    private var touchY: Float = 0f

    private val mPath = Path()

    init {
        setWillNotDraw(false)
        ivFish.layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        ivFish.setImageDrawable(fishDrawable)
        mPaint.strokeWidth = 8f
        mPaint.isDither = true
        mPaint.style = Paint.Style.STROKE
        ivFish.setBackgroundColor(Color.GREEN)
        addView(ivFish)


    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        touchX = event.x
        touchY = event.y
        mPaint.alpha = mAlpha

        val animator: ObjectAnimator = ObjectAnimator
                .ofFloat(this, "ripple", 0f, 1f)
                .setDuration(1000)
        animator.start()

        makeTrail()

        return super.onTouchEvent(event)

    }

    private fun makeTrail() {

        // 鱼的重心：相对ImageView坐标
        val fishRelativeMiddle = fishDrawable.middlePoint
        // 鱼的重心：绝对坐标 --- 起始点
        val fishMiddle = PointF(ivFish.x + fishRelativeMiddle.x, ivFish.y + fishRelativeMiddle.y)

        // 鱼头圆心的坐标 -- 控制点1
        val fishHead = PointF(ivFish.x + fishDrawable.headPoint.x, ivFish.y + fishDrawable.headPoint.y)
        // 点击坐标 -- 结束点
        val touch = PointF(touchX, touchY)

        Log.e("fish:::","touchx--->$touchX")
        Log.e("fish:::","touchy--->$touchY")

        val angle = includeAngle(fishMiddle, fishHead, touch) / 2
        val delta = includeAngle(fishMiddle, PointF(fishMiddle.x + 1, fishMiddle.y), fishHead)
        // 控制点2 的坐标
        val controlPoint = fishDrawable.caculatePoint(fishMiddle,
                fishDrawable.HEAD_RADIUS * 1.6f, angle + delta)

        mPath.reset()
        mPath.moveTo(fishMiddle.x - fishRelativeMiddle.x, fishMiddle.y - fishRelativeMiddle.y)
        mPath.cubicTo(fishHead.x - fishRelativeMiddle.x, fishHead.y - fishRelativeMiddle.y,
                controlPoint.x - fishRelativeMiddle.x, controlPoint.y - fishRelativeMiddle.y,
                touch.x - fishRelativeMiddle.x, touch.y - fishRelativeMiddle.y)

        //动画启动和结束时设置鱼鳍摆动动画，同时控制鱼身摆动频率
        val objAnim:ObjectAnimator = ObjectAnimator.ofFloat(ivFish,"x","y",mPath)
        objAnim.addListener(object:AnimatorListenerAdapter(){
            override fun onAnimationStart(animation: Animator?) {
                super.onAnimationStart(animation)
                fishDrawable.frequence = 3f
                val objAnim = fishDrawable.finsObjAnim
//                fishDrawable.finsAngle = Random(1).nextFloat() + 1
                objAnim.repeatCount = 3
                objAnim.duration = (500).toLong()
                objAnim.start()
                Log.e("zcw:::","finsObjAnim.start()----FishDrawableLayout")


            }

            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                fishDrawable.frequence = 1f
            }
        })
        objAnim.duration = 2000

        val tan = floatArrayOf(0f,0f)



        val pathMeasure = PathMeasure(mPath,false)
        objAnim.addUpdateListener(AnimatorUpdateListener {animation->
            // 执行了整个周期的百分之多少
            val fraction = animation.animatedFraction
            pathMeasure.getPosTan(pathMeasure.length * fraction,null,tan)
            val angle = Math.toDegrees(Math.atan2((-tan[1]).toDouble(), tan[0].toDouble())).toFloat()
            fishDrawable.fishMainAngel = angle
        })



        objAnim.start()


    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mPaint.alpha = mAlpha
        canvas.drawCircle(touchX, touchY, ripple * 150, mPaint)        // 点击坐标 -- 结束点

        invalidate()
    }

    /**
     * 求夹角
     */
    fun includeAngle(o: PointF, a: PointF, b: PointF): Float {
        // cosAOB
        // OA*OB=(Ax-Ox)(Bx-Ox)+(Ay-Oy)*(By-Oy)
        val aob: Float = (a.x - o.x) * (b.x - o.x) + (a.y - o.y) * (b.y - o.y)
        // 两边长
        val oaLength = Math.sqrt(Math.pow((a.x - o.x).toDouble(), 2.0) + Math.pow((a.y - o.y).toDouble(), 2.0))
        val obLength = Math.sqrt(Math.pow((b.x - o.x).toDouble(), 2.0) + Math.pow((b.y - o.y).toDouble(), 2.0))
        // 余弦函数
        val cosAOB = aob / (oaLength * obLength)
        // 反余弦
        val angleAOB = Math.toDegrees(Math.acos(cosAOB))

        // AB连线与X的夹角的tan值 - OB与x轴的夹角的tan值
        val direction = (a.y - b.y) / (a.x - b.x) - (o.y - b.y) / (o.x - b.x)

        return if (direction == 0f) {
            if (aob >= 0) {
                0f
            } else {
                180f
            }
        } else {
            if (direction > 0) {
                (-angleAOB).toFloat()
            } else {
                angleAOB.toFloat()
            }
        }

    }
}
