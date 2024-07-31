package io.nyxbit.veraceitalia.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import io.nyxbit.veraceitalia.databinding.RecyclerItemBinding
import io.nyxbit.veraceitalia.models.Item
import io.nyxbit.veraceitalia.utils.AndroidUtils.Companion.dp
import io.nyxbit.veraceitalia.utils.AndroidUtils.Companion.sp
import io.nyxbit.veraceitalia.viewholders.ItemViewHolder

class ItemRecyclerAdapter(
    private val context: Context,
    private val dataset: MutableList<Item> = mutableListOf(),
    private val onQuantityChange: (data: Item) -> Unit,

    ) : RecyclerView.Adapter<ItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(RecyclerItemBinding.inflate(LayoutInflater.from(parent.context)))
    }
    override fun getItemCount(): Int = dataset.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        if (position == 0){
            holder.container.layoutParams.height = 270.dp(context).toInt()
            holder.image.layoutParams.height = 170.dp(context).toInt()
            holder.name.textSize = 12.sp(context)
            holder.name.setSingleLine()
        }else{
            holder.container.layoutParams.height = 240.dp(context).toInt()
            holder.image.layoutParams.height = 120.dp(context).toInt()
            holder.name.textSize = 7.sp(context)
        }
        holder.bind(dataset[position], onQuantityChange)
    }

    fun getDataset(): MutableList<Item> = dataset

}
