package io.nyxbit.veraceitalia.utils

import android.content.Context
import android.graphics.Color
import android.util.TypedValue
import io.nyxbit.veraceitalia.utils.AndroidUtils.Companion.dp

class AndroidUtils {
    companion object {
        fun Int.sp(context: Context): Float = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, this.toFloat(), context.resources.displayMetrics)
        fun Int.dp(context: Context): Float = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), context.resources.displayMetrics)
        fun getColorWithAlpha(alpha:Int):Int= (alpha shl 24) or (0x000000)
    }
}
