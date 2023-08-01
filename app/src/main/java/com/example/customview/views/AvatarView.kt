package com.example.customview.views

import android.content.Context
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Paint.Style
import android.graphics.Shader
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.annotation.IntDef
import androidx.appcompat.widget.AppCompatImageView
import com.example.customview.R
import com.example.customview.utils.ImageUtil

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
    var labelDrawable: Drawable? = null
        set(value) {
            field = value
            invalidate()
        }

    // 头像边框
    private var borderPaint: Paint
    private val borderStrokeWidth = 8f

    var isShowBorder: Boolean = true

    private var labelType: Int = LabelType.TYPE_NORMAL

    init {
        attributes?.let {
            context.obtainStyledAttributes(it, R.styleable.AvatarView).apply {
                labelType = getInt(R.styleable.AvatarView_avatar_type, LabelType.TYPE_NORMAL)
            }
        }

        avatarPaint = Paint()

        labelPaint = Paint()

        borderPaint = Paint().apply {
            style = Style.STROKE
            strokeWidth = borderStrokeWidth
            color = Color.GRAY
        }

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        // 自定义View的控件大小
        val elementWidth = avatarSize.toInt()
        val elementHeight = avatarSize.toInt()
        setMeasuredDimension(elementWidth, elementHeight)
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        drawAvatar(canvas)
        drawBorder(canvas)
        drawLabel(canvas)
    }

    private fun drawAvatar(canvas: Canvas?) {
        avatarDrawable?.let {
            val width = avatarSize.toInt()
            val height = avatarSize.toInt()
            val bitmap = ImageUtil.centerCropToSquare(
                ImageUtil.scaleToSquare(
                    ImageUtil.getBitmapByDrawable(it), width, height
                )
            )

            val avatarShader = BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
            avatarPaint.shader = avatarShader


            val radius = (avatarSize - borderStrokeWidth * 2) / 2
            val cx = avatarSize / 2
            val cy = avatarSize / 2
            canvas?.drawCircle(cx, cy, radius, avatarPaint)
        }
    }

    private fun drawLabel(canvas: Canvas?) {
        labelDrawable?.let {
            val width = labelSize.toInt()
            val height = labelSize.toInt()
            val bitmap = ImageUtil.centerCropToSquare(
                ImageUtil.scaleToSquare(
                    ImageUtil.getBitmapByDrawable(it), width, height
                )
            )

            val radius = labelSize / 2
            val cx = avatarSize - radius - 5f
            val cy = avatarSize - radius - 5f

            val labelShader =
                BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP).apply {
                    // 用Matrix进行图形变换，比如平移、缩放、旋转等操作
                    // 通过postTranslate()方法对图像进行平移操作
                    Matrix().apply {
                        reset()
                        // 以左上角为基准进行平移操作
                        postTranslate(
                            cx - radius,
                            cy - radius
                        )
                        setLocalMatrix(this)
                    }
                }
            labelPaint.shader = labelShader
            labelPaint.color = Color.RED

            canvas?.drawCircle(cx, cy, radius, labelPaint)
        }
    }

    private fun drawBorder(canvas: Canvas?) {
        if (!isShowBorder) {
            return
        }

        val cx = avatarSize / 2
        val cy = avatarSize / 2

        val borderRadius = avatarSize / 2 - borderStrokeWidth
        canvas?.drawCircle(cx, cy, borderRadius, borderPaint)

    }

    @IntDef(
        LabelType.TYPE_NORMAL,
        LabelType.TYPE_DEPOSIT,
        LabelType.TYPE_OWNER
    )
    @Target(
        AnnotationTarget.TYPE_PARAMETER,
        AnnotationTarget.FUNCTION,
        AnnotationTarget.FIELD
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