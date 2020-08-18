package top.zcwfeng.customui.baseui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class SimpleColorChangeView : AppCompatTextView {
    val text = "我要爆炸"
    private val paint = Paint()

    private var percent: Float = 0.0f
        set(value) {
            field = value
            invalidate()
        }


    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        paint.reset()
        paint.textSize = 80.0f
        val baseline = 100f;
        canvas?.drawText(text, 0f, baseline, paint)


        paint.textAlign = Paint.Align.CENTER
        val x: Float = (width / 2).toFloat()
        canvas?.drawText(text, x, baseline + paint.fontSpacing, paint)

        paint.textAlign = Paint.Align.RIGHT
        canvas?.drawText(text, x, baseline + paint.fontSpacing * 2, paint)


        drawCenterLineX(canvas)
        drawCenterLineY(canvas)

        drawCenterText(canvas)
        drawCenterText1(canvas)

    }

    private fun drawCenterText(canvas: Canvas?) {
        canvas?.save();
        paint.reset()
        paint.textSize = 80.0f
        paint.style = Paint.Style.FILL
        paint.isAntiAlias = true

        val textWidth = paint.measureText(text)
        paint.textAlign = Paint.Align.LEFT
        val left = width /2 - textWidth/2
        val left_x = left + textWidth * percent
        val fontMetrics = paint.fontMetrics
        val baseline = height/2 + (fontMetrics.ascent - fontMetrics.descent) /2
        val rect = Rect(left_x.toInt(),0,width,height)
        canvas?.clipRect(rect)
        canvas?.drawText(text, left, baseline, paint)
        canvas?.restore();

    }


    private fun drawCenterText1(canvas: Canvas?) {
        canvas?.save();
        paint.reset()
        paint.textSize = 80.0f
        paint.style = Paint.Style.FILL
        paint.isAntiAlias = true
        paint.color = Color.RED
        val textWidth = paint.measureText(text)
        paint.textAlign = Paint.Align.LEFT
        val left = width /2 - textWidth/2
        val right = left + textWidth * percent
        val fontMetrics = paint.fontMetrics
        val baseline = height/2 + (fontMetrics.ascent - fontMetrics.descent) /2
        val rect = Rect(left.toInt(),0,right.toInt(),height)
        canvas?.clipRect(rect)
        canvas?.drawText(text, left, baseline, paint)
        canvas?.restore();

    }


    private fun drawCenterLineX(canvas: Canvas?) {
        paint.reset()
        paint.color = Color.RED
        paint.strokeWidth = 3f
        paint.style = Paint.Style.FILL
        canvas?.drawLine(0f, (height / 2).toFloat(), width.toFloat(), (height / 2).toFloat(), paint)
    }

    private fun drawCenterLineY(canvas: Canvas?) {
        paint.reset()
        paint.color = Color.BLUE
        paint.strokeWidth = 3f
        paint.style = Paint.Style.FILL
        canvas?.drawLine((width / 2).toFloat(), 0f, (width / 2).toFloat(), height.toFloat(), paint)
    }

}