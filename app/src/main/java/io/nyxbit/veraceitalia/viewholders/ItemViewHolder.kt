package io.nyxbit.veraceitalia.viewholders

import android.graphics.Color
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import io.nyxbit.veraceitalia.R
import io.nyxbit.veraceitalia.databinding.RecyclerItemBinding
import io.nyxbit.veraceitalia.models.Item

class ItemViewHolder(private var _binding: RecyclerItemBinding) : RecyclerView.ViewHolder(_binding.root) {
    fun bind(data: Item, onDataClick: (data: Item) -> Unit, onQuantityChange: (data: Item) -> Unit) {
        val currentPrice = data.price
        _binding.tvName.text = data.name
        _binding.tvPrice.text = "₱${data.price}"
        _binding.counter.text = data.quantity.toString()
        _binding.imageView.setImageResource(data.image)
        _binding.increment.setOnClickListener {
            if (data.quantity < 99) {
                data.quantity++
                data.subtotal = data.quantity * currentPrice
                _binding.counter.text = data.quantity.toString()
            }
            updateCardUI(data.quantity)
            onQuantityChange.invoke(data)
        }

        _binding.decrement.setOnClickListener {
            if (data.quantity > 0) {
                data.quantity--
                data.subtotal = data.quantity * currentPrice
                _binding.counter.text = data.quantity.toString()
            }
            updateCardUI(data.quantity)
            onQuantityChange.invoke(data)
        }

        itemView.setOnClickListener { onDataClick.invoke(data) }
    }
    private fun updateCardUI(quantity: Int) {
        if (quantity > 0) {
            _binding.background.setBackgroundResource(R.drawable.recycler_item_active_bg)
        } else {
            _binding.background.setBackgroundResource(0)
        }
    }
}
