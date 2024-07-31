package io.nyxbit.veraceitalia.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.nyxbit.veraceitalia.databinding.RecyclerSelectionItemBinding
import io.nyxbit.veraceitalia.models.Item
import io.nyxbit.veraceitalia.viewholders.SelectionViewHolder


class SelectionRecyclerAdapter(
    private val dataset: MutableList<Item> = mutableListOf(),
) : RecyclerView.Adapter<SelectionViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectionViewHolder {
        return SelectionViewHolder(RecyclerSelectionItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount(): Int = dataset.size

    override fun onBindViewHolder(holder: SelectionViewHolder, position: Int) {
        holder.bind(dataset[position])
    }

    fun updateItems(newItems: List<Item>) {
        dataset.clear()
        dataset.addAll(newItems)
        notifyDataSetChanged()
    }
    fun getDataset(): MutableList<Item> = dataset
}
