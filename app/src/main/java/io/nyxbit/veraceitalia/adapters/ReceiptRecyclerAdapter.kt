package io.nyxbit.veraceitalia.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.nyxbit.veraceitalia.databinding.RecyclerItemBinding
import io.nyxbit.veraceitalia.databinding.RecyclerReceiptItemBinding
import io.nyxbit.veraceitalia.models.Item
import io.nyxbit.veraceitalia.viewholders.ItemViewHolder
import io.nyxbit.veraceitalia.viewholders.ReceiptViewHolder

class ReceiptRecyclerAdapter(
    private val dataset: MutableList<Item> = mutableListOf(),
    private val onDataClick: (data: Item) -> Unit,

    ) : RecyclerView.Adapter<ReceiptViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReceiptViewHolder {
        return ReceiptViewHolder(RecyclerReceiptItemBinding.inflate(LayoutInflater.from(parent.context)))
    }
    override fun getItemCount(): Int {
        return dataset.size
    }
    override fun onBindViewHolder(holder: ReceiptViewHolder, position: Int) {
        holder.bind(dataset[position], onDataClick)
    }

}
