package com.dohyeok.gulpgulp.util

import android.content.Context
import android.util.TypedValue

fun dpToPx(context: Context, dp: Float): Float {
    val density = context.resources.displayMetrics.density
    return dp * density
}

fun pxToDP(context: Context, px: Float): Float {
    val density = context.resources.displayMetrics.density
    return px / density
}