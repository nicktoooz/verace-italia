package io.nyxbit.veraceitalia.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.nyxbit.veraceitalia.databinding.RecyclerItemBinding
import io.nyxbit.veraceitalia.models.Item
import io.nyxbit.veraceitalia.viewholders.ItemViewHolder

class ItemRecyclerAdapter(
    private val dataset: MutableList<Item> = mutableListOf(),
    private val onDataClick: (data: Item) -> Unit,
    private val onQuantityChange: (data: Item) -> Unit,

    ) : RecyclerView.Adapter<ItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(RecyclerItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(dataset[position], onDataClick, onQuantityChange)
    }
}
