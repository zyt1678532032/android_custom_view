package com.zyt.widgets

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import com.zyt.widgets.utils.UiUtils
import java.lang.invoke.ConstantCallSite

class CustomTextView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    private var parentPaint: Paint = Paint()
    private var textPaint: Paint = Paint().apply {
        textSize = UiUtils.dp2px(context, 24f).toFloat()
        color = ContextCompat.getColor(context, R.color.black)
    }

    private var linePaint: Paint = Paint().apply {
        strokeWidth = UiUtils.dp2px(context, 1f).toFloat()
        color = ContextCompat.getColor(context, R.color.black)
        style = Paint.Style.STROKE
    }
    private var parentRect = Rect()

    var color: Int = Color.RED
        set(value) {
            field = value
            parentPaint.color = field
            invalidate()
        }

    var size: Int = 100
        set(value) {
            field = value
            parentRect.set(0, 0, field, field)
            invalidate()
        }

    var text: String = "00:47 / 10:24"
        set(value) {
            field = value
            invalidate()
        }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        Log.i(TAG, "onMeasure: ${MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.AT_MOST}")

        setMeasuredDimension(
            MeasureSpec.getSize(widthMeasureSpec),
            MeasureSpec.getSize(heightMeasureSpec)
        )
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas == null) return

        drawParentView(canvas)
        Log.i(
            TAG,
            "onDraw: getX():${this.x} getY():${this.y};height:${this.height} width:${width};getLeft(): $left getRight(): $right"
        )

    }

    private fun drawParentView(canvas: Canvas) {
        val text = text
        val rect = Rect()
        textPaint.getTextBounds(text, 0, text.length, rect)

        Log.i(TAG, "drawParentView: ${rect.left} ${rect.top} ${rect.right} ${rect.bottom}")
        val x = (measuredWidth / 2).toFloat() - (rect.right - rect.left) / 2
        val y = UiUtils.dp2px(context, 24f).toFloat()
        canvas.drawText(
            text,
            x,
            y,
            textPaint
        )
        // canvas.drawRect(
        //     rect.left + x, rect.top + y,
        //     x + rect.right, rect.bottom.toFloat() + y,
        //     linePaint
        // )
        canvas.drawLine(
            0f,
            UiUtils.dp2px(context, 30f).toFloat(),
            width.toFloat(),
            UiUtils.dp2px(context, 30f).toFloat(),
            linePaint
        )
    }


    companion object {
        const val TAG = "TwoElementView"
    }

}