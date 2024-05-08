package com.zyt.widgets.utils

import android.content.res.Resources

/**
 * px: 像素
 * dp(device independent pixels): 与设备像素密度无关的单位
 *
 * dp * density = px
 * dp * (densityDpi / 160) = px
 */
val Int.dp: Float
    get() {
        val scale = Resources.getSystem().displayMetrics.density
        return (this * scale + 0.5f)
    }

val Int.px: Float
    get() {
        val scale = Resources.getSystem().displayMetrics.density
        return (this / scale + 0.5f)
    }


