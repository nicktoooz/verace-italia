package io.nyxbit.veraceitalia.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import io.nyxbit.veraceitalia.databinding.RecyclerItemBinding
import io.nyxbit.veraceitalia.models.Item

class ItemViewHolder (private var _binding : RecyclerItemBinding): RecyclerView.ViewHolder(_binding.root) {
    fun bind(data:Item, onDataClick:(data:Item)-> Unit){
        val currentPrice : Int = data.price
        _binding.tvName.text = data.name
        _binding.tvPrice.text = data.price.toString()
        _binding.counter.text = data.quantity.toString()

        _binding.increment.setOnClickListener {
            if (data.quantity < 99) {
                data.quantity++
                _binding.counter.text = data.quantity.toString()
            }
        }
        _binding.decrement.setOnClickListener{
            if (data.quantity > 0) {
                data.quantity--
                _binding.counter.text = data.quantity.toString()
            }
        }
     itemView.setOnClickListener{ onDataClick.invoke(data)}
    }

}