package io.nyxbit.veraceitalia.models

import io.nyxbit.veraceitalia.R

class Item (
    var name : String? = null,
    var price : Int = 0,
    var quantity : Int = 0,
    var subtotal : Int = 0,
    var image : Int = R.mipmap.ic_launcher
)