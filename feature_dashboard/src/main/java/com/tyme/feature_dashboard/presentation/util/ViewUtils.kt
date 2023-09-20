package com.tyme.feature_dashboard.presentation.util

import android.content.Context
import android.util.TypedValue

object ViewUtils {
    fun getDimensionBasedOnHeight(context: Context, originalValue: Float): Int {
        val displayMetrics = context.resources.displayMetrics
        val screenHeight = displayMetrics.heightPixels

        // Pixel XL has a height of 2560 pixels. Adjust this if necessary.
        if (screenHeight <= 2560) {
            return originalValue.dpToPx(context).toInt()
        }

        // Adjust this scaling factor if you want more/less scaling
        val scalingFactor = screenHeight.toFloat() / 2560f
        return (originalValue * scalingFactor).dpToPx(context).toInt()
    }
    private fun Float.dpToPx(context: Context): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this,
            context.resources.displayMetrics
        )
    }
}
