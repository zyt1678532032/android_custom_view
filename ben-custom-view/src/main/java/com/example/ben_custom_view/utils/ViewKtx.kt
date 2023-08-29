package com.example.ben_custom_view.utils

import android.content.res.Resources

val Int.dp: Float
    get() {
        val scale = Resources.getSystem().displayMetrics.density
        return this * scale
    }


