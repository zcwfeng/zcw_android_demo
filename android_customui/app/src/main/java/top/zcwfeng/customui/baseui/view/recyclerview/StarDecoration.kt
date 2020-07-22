package top.zcwfeng.customui.baseui.view.recyclerview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import top.zcwfeng.customui.utils.dp2px


class StarDecoration(val context: Context) : RecyclerView.ItemDecoration() {
    private val groupHeaderHeight: Int = dp2px(context,
            100f)
    private val headPaint: Paint = Paint()
    private val textPaint: Paint = Paint()
    private val textRect: Rect = Rect()

    init {
        headPaint.color = Color.RED
        textPaint.textSize = 50f
        textPaint.color = Color.WHITE
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        if (parent.adapter is StarAdapter) {
            val starAdapter = parent.adapter as StarAdapter
            val left = parent.paddingLeft
            val right = parent.width - parent.paddingRight
            var count = parent.childCount
            for (i in 0 until count) {
                // curView
                val view = parent.getChildAt(i)
                val position = parent.getChildAdapterPosition(view)
                val isGroupHead = starAdapter.isGroupHeader(position)
                if (isGroupHead && view.top - groupHeaderHeight - parent.paddingTop >= 0) {
                    c.drawRect(left.toFloat(), (view.top - groupHeaderHeight).toFloat(), right.toFloat(), view.bottom.toFloat(), headPaint)
                    val groupName = starAdapter.getGroupName(position)
                    textPaint.getTextBounds(groupName, 0, groupName.length, textRect);
                    c.drawText(groupName, (left + 20).toFloat(),
                            (view.top - groupHeaderHeight / 2 + textRect.height() / 2).toFloat(), textPaint)
                } else if(view.top - groupHeaderHeight - parent.paddingTop >= 0){
                    c.drawRect(left.toFloat(), (view.top - 4).toFloat(), right.toFloat(), view.top.toFloat(), headPaint);
                }

            }
        }
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        if (parent.adapter is StarAdapter) {
            val starAdapter = parent.adapter as StarAdapter
            val position = (parent.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
            val itemView: View? = parent.findViewHolderForAdapterPosition(position)?.itemView
            val left = parent.paddingLeft
            val right = parent.width - parent.paddingRight
            val top = parent.paddingTop
            val isGroupHead = starAdapter.isGroupHeader(position + 1)
            if (isGroupHead) {
                val groupName = starAdapter.getGroupName(position)
                val bottom: Int = Math.min(groupHeaderHeight, itemView?.bottom
                        ?: 0 - parent.paddingTop)
                c.drawRect(left.toFloat(), top.toFloat(), right.toFloat(),
                        (bottom + top).toFloat(), headPaint)
                textPaint.getTextBounds(groupName, 0, groupName.length, textRect);
                c.drawText(groupName, (left + 20).toFloat(),
                        (top - groupHeaderHeight / 2 + textRect.height() / 2 + bottom).toFloat(),
                        textPaint)

            } else {
                val groupName = starAdapter.getGroupName(position)
                c.drawRect(left.toFloat(), top.toFloat(), right.toFloat(),
                        (top + groupHeaderHeight).toFloat(), headPaint)
                textPaint.getTextBounds(groupName, 0, groupName.length, textRect);
                c.drawText(groupName, (left + 20).toFloat(),
                        (top + groupHeaderHeight / 2 + textRect.height() / 2).toFloat(), textPaint)
            }

        }
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        if (parent.adapter is StarAdapter) {
            val starAdapter = parent.adapter as StarAdapter
            val position: Int = parent.getChildLayoutPosition(view)
            val isGroupHeader = starAdapter.isGroupHeader(position)
            if (isGroupHeader) {
                outRect.set(0, groupHeaderHeight, 0, 0)
            } else {
                outRect.set(0, 0, 4, 0)
            }

        }
    }
}