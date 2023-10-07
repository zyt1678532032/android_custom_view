package com.example.wanandroid.util

import android.content.Context

object UiUtils {

    @JvmStatic
    fun dp2px(context: Context, dpValue: Float): Int {
        val scale = context.resources
            .displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }
}