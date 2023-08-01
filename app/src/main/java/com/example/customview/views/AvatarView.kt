package com.example.customview.views

import android.content.Context
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Shader
import android.graphics.drawable.Drawable
import android.media.Image
import android.util.AttributeSet
import android.view.View
import androidx.annotation.IntDef
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.drawToBitmap
import com.example.customview.R
import com.example.customview.utils.ImageUtil

/**
 * 自定义头像视图 AvatarView
 * @author
 */
class AvatarView(context: Context, attributes: AttributeSet? = null) :
    AppCompatImageView(context, attributes) {


    // 头像资源
    private var avatarSize: Float = 100f
    private var avatarPaint: Paint
    var avatarDrawable: Drawable? = null
        set(value) {
            field = value
            invalidate()
        }

    // 标签资源
    private var labelSize: Float = 20f
    private var labelPaint: Paint
    var labelDrawable: Drawable? = null
        set(value) {
            field = value
            invalidate()
        }

    // 头像边框
    private var borderPaint: Paint? = null

    var isShowBorder: Boolean? = false

    private var avatarType: Int = LabelType.TYPE_NORMAL

    init {
        attributes?.let {
            context.obtainStyledAttributes(it, R.styleable.AvatarView).apply {
                avatarType = getInt(R.styleable.AvatarView_avatar_type, LabelType.TYPE_NORMAL)
            }
        }

        avatarPaint = Paint()

        labelPaint = Paint()

        borderPaint = Paint()

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        // 自定义View的控件大小
        val elementWidth = (avatarSize * 2).toInt()
        val elementHeight = (avatarSize * 2).toInt()
        setMeasuredDimension(elementWidth, elementHeight)
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        drawAvatar(canvas)
    }

    private fun drawAvatar(canvas: Canvas?) {
        avatarDrawable?.let {
            val width = (avatarSize * 2).toInt()
            val height = (avatarSize * 2f).toInt()
            val bitmap = ImageUtil.centerCropToSquare(
                ImageUtil.scaleToSquare(
                    ImageUtil.getBitmapByDrawable(it), width, height
                )
            )

            val avatarShader = BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
            avatarPaint.shader = avatarShader

            val cx = avatarSize
            val cy = avatarSize
            val radius = avatarSize
            canvas?.drawCircle(cx, cy, radius, avatarPaint)
        }
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