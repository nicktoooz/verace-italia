package io.nyxbit.veraceitalia.viewholders

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import io.nyxbit.veraceitalia.databinding.RecyclerSelectionItemBinding
import io.nyxbit.veraceitalia.models.Item
import java.util.Locale

class SelectionViewHolder (private var _binding : RecyclerSelectionItemBinding): RecyclerView.ViewHolder(_binding.root) {
    fun bind(data:Item){
        _binding.tvName.text = data.name
        _binding.tvQuantity.text = data.quantity.toString()
        _binding.tvTotalPrice.text = String.format(Locale.UK,"£ %.2f", data.subtotal)
    }

}