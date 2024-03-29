package com.zyt.widgets.views

import android.content.Context
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Paint.Style
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.Shader
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.annotation.IntDef
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.res.ResourcesCompat
import com.zyt.widgets.R
import com.zyt.widgets.utils.ImageUtil

/**
 * 自定义头像视图 AvatarView
 * @author
 */
class AvatarView(context: Context, attributes: AttributeSet? = null) :
    AppCompatImageView(context, attributes) {

    // 头像资源
    private var avatarSize: Float = 200f
    private var avatarPaint: Paint
    var avatarDrawable: Drawable? = null
        set(value) {
            field = value
            invalidate()
        }

    // 标签资源
    private var labelSize: Float = 40f
    private var labelPaint: Paint
    private val matrix = Matrix()
    private var labelDrawable: Drawable? = null
        set(value) {
            field = value
            invalidate()
        }

    // 头像边框
    private var borderPaint: Paint
    private val borderStrokeWidth = 8f

    var isShowBorder: Boolean = false

    private var labelType: Int = LabelType.TYPE_NORMAL

    init {
        attributes?.let {
            context.obtainStyledAttributes(it, R.styleable.AvatarView).run {
                labelType = getInt(R.styleable.AvatarView_avatar_type, LabelType.TYPE_NORMAL).run {
                    setLabelType(this)
                    this
                }
                avatarDrawable = getDrawable(R.styleable.AvatarView_src)
                // 在获取完所需要的属性值后调用recycle()方法，可以确保TypedArray对象被及时回收，避免出现异常和内存泄漏问题。
                recycle()
            }
        }

        avatarPaint = Paint().apply {
            style = Style.FILL
        }

        labelPaint = Paint().apply {
            style = Style.FILL
        }

        borderPaint = Paint().apply {
            style = Style.STROKE
            strokeWidth = borderStrokeWidth
            color = Color.GRAY
        }

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        // 自定义View的控件大小
        // val elementWidth = avatarSize.toInt()
        // val elementHeight = avatarSize.toInt()
        // layoutParams.width = elementWidth
        // layoutParams.height = elementHeight
        setMeasuredDimension(
            MeasureSpec.getSize(widthMeasureSpec),
            MeasureSpec.getSize(heightMeasureSpec)
        )
    }

    override fun onDraw(canvas: Canvas) {
        // canvas.drawCircle(50f, 50f, 50f, Paint())
        drawAvatar(canvas)
        drawBorder(canvas)
        drawLabel(canvas)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        avatarSize = w.toFloat()
    }

    private fun drawAvatar(canvas: Canvas) {
        avatarDrawable?.let {
            val width = avatarSize.toInt()
            val height = avatarSize.toInt()
            val bitmap = ImageUtil.centerCropToSquare(
                bitmap = ImageUtil.scaleToSquare(
                    ImageUtil.getBitmapByDrawable(it), width, height
                )
            )

            val avatarShader = BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
            avatarPaint.shader = avatarShader

            val radius = (avatarSize - borderStrokeWidth * 2) / 2
            val cx = avatarSize / 2
            val cy = avatarSize / 2
            canvas.drawCircle(cx, cy, radius, avatarPaint)
        }
    }

    private fun drawLabel(canvas: Canvas) {
        labelDrawable?.let {
            val width = labelSize.toInt()
            val height = labelSize.toInt()
            val bitmap = ImageUtil.centerCropToSquare(
                bitmap = ImageUtil.scaleToSquare(
                    ImageUtil.getBitmapByDrawable(it), width, height
                )
            )

            val radius = labelSize / 2
            val cx = avatarSize - radius - 5f
            val cy = avatarSize - radius - 5f

            // val labelShader = BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
            // matrix.run {
            //     reset()
            //     setTranslate(cx - radius, cy - radius)
            // }
            // labelShader.setLocalMatrix(matrix)
            // labelPaint.shader = labelShader
            // labelPaint.color = Color.RED

            canvas.drawBitmap(
                bitmap,
                null,
                RectF(
                    avatarSize - labelSize,
                    avatarSize - labelSize,
                    avatarSize,
                    avatarSize
                ),
                labelPaint
            )
            // canvas.drawCircle(avatarSize / 2, avatarSize / 2, radius, labelPaint)
        }
    }

    private fun drawBorder(canvas: Canvas) {
        if (!isShowBorder) {
            return
        }

        val cx = avatarSize / 2
        val cy = avatarSize / 2

        val borderRadius = avatarSize / 2 - borderStrokeWidth
        canvas.drawCircle(cx, cy, borderRadius, borderPaint)
    }

    fun setLabelType(@LabelType labelType: Int) {
        when (labelType) {
            LabelType.TYPE_NORMAL -> {
                labelDrawable = null
            }

            LabelType.TYPE_DEPOSIT -> {
                labelDrawable =
                    ResourcesCompat.getDrawable(resources, R.drawable.icon_avatar_deposit, null)
            }

            LabelType.TYPE_OWNER -> {
                labelDrawable =
                    ResourcesCompat.getDrawable(resources, R.drawable.icon_avatar_owner, null)
            }
        }
        this.labelType = labelType
    }

    @IntDef(
        LabelType.TYPE_NORMAL,
        LabelType.TYPE_DEPOSIT,
        LabelType.TYPE_OWNER
    )
    @Target(
        AnnotationTarget.TYPE_PARAMETER,
        AnnotationTarget.FUNCTION,
        AnnotationTarget.FIELD,
        AnnotationTarget.VALUE_PARAMETER
    )
    @Retention(AnnotationRetention.SOURCE)
    annotation class LabelType {
        companion object {
            const val TYPE_NORMAL = 0 // 普通用户
            const val TYPE_DEPOSIT = 1 // 待定用户
            const val TYPE_OWNER = 2 // 授权用户
        }
    }

}