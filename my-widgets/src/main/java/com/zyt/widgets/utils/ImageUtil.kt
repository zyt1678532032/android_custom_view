package com.zyt.widgets.utils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.VectorDrawable
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat

object ImageUtil {

    fun getBitmapByDrawable(
        drawable: Drawable,
        width: Int = drawable.intrinsicWidth,
        height: Int = drawable.intrinsicHeight
    ): Bitmap {
        return when (drawable) {
            is BitmapDrawable -> drawable.bitmap // jpeg、jpg、png
            is VectorDrawable, is VectorDrawableCompat -> {
                val bitmap = Bitmap.createBitmap(
                    drawable.intrinsicWidth,
                    drawable.intrinsicHeight,
                    Bitmap.Config.ARGB_8888
                )
                val canvas = Canvas(bitmap)
                drawable.setBounds(0, 0, canvas.width, canvas.height)
                drawable.draw(canvas)
                bitmap
            }

            else -> {
                val bitmap = if (drawable.intrinsicWidth > 0 && drawable.intrinsicHeight > 0) {
                    Bitmap.createBitmap(
                        drawable.intrinsicWidth,
                        drawable.intrinsicHeight,
                        Bitmap.Config.ARGB_8888
                    )
                } else {
                    Bitmap.createBitmap(
                        width,
                        height,
                        Bitmap.Config.ARGB_8888
                    )
                }
                val canvas = Canvas(bitmap)
                drawable.setBounds(0, 0, canvas.width, canvas.height)
                drawable.draw(canvas)
                bitmap
            }
        }
    }

    /**
     * 缩放至指定尺寸的正方形
     */
    fun scaleToSquare(bitmap: Bitmap, width: Int, height: Int): Bitmap {
        return if ((bitmap.width.toFloat() / bitmap.height) != (width.toFloat() / height)) {
            Bitmap.createScaledBitmap(centerCropToSquare(bitmap), width, height, false)
        } else {
            Bitmap.createScaledBitmap(bitmap, width, height, false)
        }
    }

    /**
     * 按照原图尺寸比例裁剪，裁剪结果为正方形
     */
    fun centerCropToSquare(bitmap: Bitmap): Bitmap {
        var startX = 0
        var startY = 0
        return if (bitmap.width > bitmap.height) {
            startX = (bitmap.width - bitmap.height) / 2
            Bitmap.createBitmap(bitmap, startX, startY, bitmap.height, bitmap.height)
        } else if (bitmap.width < bitmap.height) {
            startY = (bitmap.height - bitmap.width) / 2
            Bitmap.createBitmap(bitmap, startX, startY, bitmap.width, bitmap.width)
        } else {
            bitmap
        }
    }
}