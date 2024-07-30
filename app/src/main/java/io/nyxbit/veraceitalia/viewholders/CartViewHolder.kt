package io.nyxbit.veraceitalia.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import io.nyxbit.veraceitalia.databinding.RecyclerCartItemBinding
import io.nyxbit.veraceitalia.databinding.RecyclerItemBinding
import io.nyxbit.veraceitalia.models.Item

class CartViewHolder (private var _binding : RecyclerCartItemBinding): RecyclerView.ViewHolder(_binding.root) {
    fun bind(data:Item, onDataClick:(data:Item)-> Unit){
        _binding.tvName.text = data.name
        _binding.tvQuantity.text = data.quantity.toString()
        _binding.tvTotalPrice.text = String.format("£ %.2f", data.subtotal)
        itemView.setOnClickListener{ onDataClick.invoke(data)}
    }

}