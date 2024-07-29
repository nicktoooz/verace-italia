package io.nyxbit.veraceitalia.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.nyxbit.veraceitalia.databinding.RecyclerCartItemBinding
import io.nyxbit.veraceitalia.databinding.RecyclerItemBinding
import io.nyxbit.veraceitalia.models.Item
import io.nyxbit.veraceitalia.viewholders.CartViewHolder
import io.nyxbit.veraceitalia.viewholders.ItemViewHolder

class CartRecyclerAdapter(
    private val dataset: MutableList<Item> = mutableListOf(),
    private val onDataClick: (data: Item) -> Unit,
) : RecyclerView.Adapter<CartViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder{
        return CartViewHolder(RecyclerCartItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(dataset[position], onDataClick)
    }
}
