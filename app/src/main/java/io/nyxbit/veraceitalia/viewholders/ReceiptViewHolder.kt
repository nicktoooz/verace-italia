package io.nyxbit.veraceitalia.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import io.nyxbit.veraceitalia.databinding.RecyclerCartItemBinding
import io.nyxbit.veraceitalia.databinding.RecyclerItemBinding
import io.nyxbit.veraceitalia.databinding.RecyclerReceiptItemBinding
import io.nyxbit.veraceitalia.models.Item

class ReceiptViewHolder (private var _binding : RecyclerReceiptItemBinding): RecyclerView.ViewHolder(_binding.root) {
    fun bind(data:Item, onDataClick:(data:Item)-> Unit){
        _binding.tvName.text = data.name
        _binding.tvPrice.text = String.format("£ %.2f", data.subtotal)
        _binding.tvQuantity.text = data.quantity.toString()
        itemView.setOnClickListener{ onDataClick.invoke(data)}
    }

}