package io.nyxbit.veraceitalia.viewholders

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import io.nyxbit.veraceitalia.databinding.RecyclerReceiptItemBinding
import io.nyxbit.veraceitalia.models.Item
import java.util.Locale

class ReceiptViewHolder (private var _binding : RecyclerReceiptItemBinding): RecyclerView.ViewHolder(_binding.root) {
    fun bind(data:Item){
        _binding.tvName.text = data.name
        _binding.tvPrice.text = String.format(Locale.UK,"£ %.2f", data.subtotal)
        _binding.tvQuantity.text = data.quantity.toString()
    }

}