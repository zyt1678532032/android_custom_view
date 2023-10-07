package com.example.wanandroid.util

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager

object ScreenUtil {

    @JvmStatic
    fun getScreenWidth(context: Context): Int {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val displayMetric = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetric)
        return displayMetric.widthPixels
    }
}