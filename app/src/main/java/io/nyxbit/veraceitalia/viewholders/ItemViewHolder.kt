package io.nyxbit.veraceitalia.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import io.nyxbit.veraceitalia.databinding.RecyclerItemBinding
import io.nyxbit.veraceitalia.models.Item

class ItemViewHolder(private var _binding: RecyclerItemBinding) : RecyclerView.ViewHolder(_binding.root) {
    fun bind(data: Item, onDataClick: (data: Item) -> Unit, onQuantityChange: (data: Item) -> Unit) {

        val currentPrice = data.price
        _binding.tvName.text = data.name
        _binding.tvPrice.text = String.format("£ %.2f", data.price)
        _binding.counter.text = data.quantity.toString()
        _binding.imageView.setImageResource(data.image)

        _binding.background.setBackgroundResource(data.getBackgroundResource())

        _binding.increment.setOnClickListener {
            if (data.quantity < 99) {
                data.quantity++
                data.subtotal = data.quantity * currentPrice
                _binding.counter.text = data.quantity.toString()
                _binding.background.setBackgroundResource(data.getBackgroundResource())
            }
            onQuantityChange.invoke(data)
        }

        _binding.decrement.setOnClickListener {
            if (data.quantity > 0) {
                data.quantity--
                data.subtotal = data.quantity * currentPrice
                _binding.counter.text = data.quantity.toString()
                _binding.background.setBackgroundResource(data.getBackgroundResource())
            }
            onQuantityChange.invoke(data)
        }

        itemView.setOnClickListener { onDataClick.invoke(data) }
    }
}
