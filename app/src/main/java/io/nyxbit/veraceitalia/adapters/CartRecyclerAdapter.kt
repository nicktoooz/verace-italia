import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.nyxbit.veraceitalia.databinding.RecyclerCartItemBinding
import io.nyxbit.veraceitalia.models.Item
import io.nyxbit.veraceitalia.viewholders.CartViewHolder


class CartRecyclerAdapter(
    val dataset: MutableList<Item> = mutableListOf(),
    private val onDataClick: (data: Item) -> Unit,
) : RecyclerView.Adapter<CartViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        return CartViewHolder(RecyclerCartItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(dataset[position], onDataClick)
    }

    fun updateItems(newItems: List<Item>) {
        dataset.clear()
        dataset.addAll(newItems)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        if (position >= 0 && position < dataset.size) {
            dataset.removeAt(position)
            notifyItemRemoved(position)
        }
    }
}
