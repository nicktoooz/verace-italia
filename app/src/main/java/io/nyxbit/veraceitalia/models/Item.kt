package io.nyxbit.veraceitalia.models

import android.graphics.Color
import io.nyxbit.veraceitalia.R

class Item (
    var name: String? = null,
    var price: Float = 0f,
    var quantity: Int = 0,
    var subtotal: Float = 0f,
    var image: Int = R.mipmap.ic_launcher
) {
    fun getBackgroundResource(): Int {
        return if (quantity > 0) {
            R.drawable.recycler_item_active_bg
        } else {
            0
        }
    }
}
