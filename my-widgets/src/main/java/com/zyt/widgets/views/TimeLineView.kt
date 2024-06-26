package com.zyt.widgets.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.zyt.widgets.R
import com.zyt.widgets.utils.dp

// 时间轴View
class TimeLineView(context: Context, attributeSet: AttributeSet? = null) :
    View(context, attributeSet) {

    private var data: List<TimeBean> = fakeData

    // 已到达节点的半径大小
    var reachedCircleRadius: Float = 24f

    // 未到达节点的半径大小
    private var unreachedCircleRadius: Float = reachedCircleRadius

    private val marginStart: Float = 20f // 文字内容的右边距
    private val marginBottom: Float = 40f
    private val circleMarginVertical: Float = 5.dp // 节点的上边距
    private val lineLength: Float = 40.dp

    private var startX: Float = reachedCircleRadius // 时间轴元素的起点X坐标
    private var startY: Float = reachedCircleRadius + 15.dp // 时间轴元素的起点Y坐标

    // FIXME: padding功能未添加
    var paddingStart: Float = 20.dp
    var paddingTop: Float = 0f

    @JvmField
    var textSize: Float = 14.dp
    private val textPaint: Paint
    private val circlePaint: Paint
    private val circleBorderPaint: Paint

    init {
        attributeSet?.let {
            context.obtainStyledAttributes(it, R.styleable.TimeLineView).apply {
                paddingStart = getDimension(R.styleable.TimeLineView_time_paddingStart, 20.dp)
                reachedCircleRadius = getFloat(R.styleable.TimeLineView_time_circleRadius, 24f)
            }
        }

        textPaint = Paint().apply {
            isAntiAlias = true
            color = Color.BLACK
            textSize = this@TimeLineView.textSize // 设置字体大小
            isAntiAlias = true
        }

        circlePaint = Paint().apply {
            color = Color.WHITE
        }

        circleBorderPaint = Paint().apply {
            color = Color.RED
            style = Paint.Style.STROKE
            strokeWidth = 10f
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val eleHeight = 2 * reachedCircleRadius + circleMarginVertical * 2 + lineLength
        val layoutWidth = resources.displayMetrics.widthPixels
        setMeasuredDimension(layoutWidth, (eleHeight * data.size).toInt())
    }

    override fun onDraw(canvas: Canvas) {
        data.forEachIndexed { index, ele ->
            drawTimeLine(canvas, ele, index)
        }
        Log.i("TimeLineView", "$startX----$startY")
        startX = reachedCircleRadius
        startY = reachedCircleRadius + 15.dp
    }

    private fun drawTimeLine(canvas: Canvas?, data: TimeBean, index: Int) {
        drawCircle(canvas, data)
        drawText(canvas, data)
        drawLine(canvas, index)
    }

    private fun drawCircle(canvas: Canvas?, data: TimeBean) {
        val cx = startX + paddingStart
        val cy = startY

        // 更新画笔状态
        when (data.status) {
            StatusType.UNREACHED -> {
                circlePaint.color = resources.getColor(R.color.time_line_unreached, null)
                circleBorderPaint.color = resources.getColor(R.color.white, null)
                circleBorderPaint.style = Paint.Style.STROKE
                canvas?.drawCircle(cx, cy, unreachedCircleRadius, circlePaint)
                canvas?.drawCircle(cx, cy, reachedCircleRadius, circleBorderPaint)
            }

            StatusType.CURRENT -> {
                circlePaint.color = Color.WHITE
                circleBorderPaint.color = resources.getColor(R.color.time_line_current, null)
                circleBorderPaint.style = Paint.Style.STROKE
                canvas?.drawCircle(cx, cy, reachedCircleRadius, circlePaint)
                canvas?.drawCircle(cx, cy, reachedCircleRadius, circleBorderPaint)
            }

            StatusType.REACHED -> {
                circlePaint.color = Color.WHITE
                circleBorderPaint.color = resources.getColor(R.color.time_line_reached, null)
                circleBorderPaint.style = Paint.Style.STROKE
                canvas?.drawCircle(cx, cy, reachedCircleRadius, circlePaint)
                canvas?.drawCircle(cx, cy, reachedCircleRadius, circleBorderPaint)
            }
        }
    }

    private fun drawText(canvas: Canvas?, data: TimeBean) {
        val cx = startX + reachedCircleRadius + marginStart + paddingStart
        var cy = startY + reachedCircleRadius / 2
        // 描述
        canvas?.drawText(data.description, cx, cy, textPaint)
        // 时间
        cy += textSize / 2 + marginBottom
        canvas?.drawText(data.time, cx, cy, textPaint)
    }

    private fun drawLine(canvas: Canvas?, index: Int) {
        val _startX = startX + paddingStart
        val _startY = startY + reachedCircleRadius + circleMarginVertical
        val endX = _startX
        val endY = _startY + lineLength
        startY = endY + reachedCircleRadius + circleMarginVertical

        if (index != data.size - 1) { // 最后一个不画线
            canvas?.drawLine(_startX, _startY, endX, endY, textPaint)
        }
    }

    enum class StatusType {
        REACHED, // 已到达节点
        UNREACHED, // 未到达节点
        CURRENT // 当前节点
    }

    data class TimeBean(
        val status: StatusType,
        val description: String,
        val time: String
    )

}

val fakeData = listOf(
    TimeLineView.TimeBean(
        status = TimeLineView.StatusType.UNREACHED,
        description = "This is an expository copy ",
        time = "2022/01/01, 00:00"
    ),
    TimeLineView.TimeBean(
        status = TimeLineView.StatusType.CURRENT,
        description = "This is an expository copy ",
        time = "2022/01/01, 00:00"
    ),
    TimeLineView.TimeBean(
        status = TimeLineView.StatusType.REACHED,
        description = "This is an expository copy ",
        time = "2022/01/01, 00:00"
    ),
    TimeLineView.TimeBean(
        status = TimeLineView.StatusType.REACHED,
        description = "This is an expository copy ",
        time = "2022/01/01, 00:00"
    ),
    TimeLineView.TimeBean(
        status = TimeLineView.StatusType.REACHED,
        description = "This is an expository copy ",
        time = "2022/01/01, 00:00"
    )
)

