package io.nyxbit.veraceitalia

import android.animation.ObjectAnimator
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import io.nyxbit.veraceitalia.adapters.CartRecyclerAdapter
import io.nyxbit.veraceitalia.adapters.ItemRecyclerAdapter
import io.nyxbit.veraceitalia.databinding.FragmentProductListBinding
import io.nyxbit.veraceitalia.models.Item
import io.nyxbit.veraceitalia.viewmodels.CartViewModel

class ProductList : Fragment() {

    private lateinit var _binding: FragmentProductListBinding
    private lateinit var listAdapter: ItemRecyclerAdapter
    private lateinit var cartAdapter: CartRecyclerAdapter
    private lateinit var cartViewModel: CartViewModel
    private var dataset = mutableListOf<Item>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cartViewModel = ViewModelProvider(requireActivity())[CartViewModel::class.java]
        cartViewModel.clear()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductListBinding.inflate(inflater, container, false)
        var isCollapsed = false

        cartViewModel.getSubtotal().observe(viewLifecycleOwner) {
            _binding.subtotal.text = "₱$it"
        }

        listAdapter = ItemRecyclerAdapter(dataset, {}, { item ->
            if (item.quantity > 0) {
                if (!cartViewModel.exists(item)) {
                    cartViewModel.add(item)
                } else {
                    cartViewModel.replace(item, item)
                }
            } else {
                cartViewModel.remove(item)
            }
            cartAdapter.notifyDataSetChanged()
        })

        cartAdapter = CartRecyclerAdapter(cartViewModel.getList(), {})

        dataset.add(Item(name = "Lorem Ipsum Dolor Amet", price = 199))
        dataset.add(Item(name = "Tiramisu", price = 299, image = R.drawable.menu_tiramisu))

        _binding.next.setOnClickListener {
            var subtotal = 0
            for (item in dataset) {
                subtotal += item.subtotal
            }
            Log.d("Subtotal", subtotal.toString())
        }

        _binding.cartLayout.button.setOnClickListener {
            isCollapsed = if (!isCollapsed) {
                animSummary(0f)
                _binding.cartLayout.stateIcon.setImageResource(R.drawable.collapse)
                true
            } else {
                _binding.cartLayout.rvCart.post {
                    val rvCartHeight = AndroidUtils.pxToDp(requireContext(), _binding.cartLayout.rvCart.height.toFloat())
                    val labelHeight = AndroidUtils.pxToDp(requireContext(), _binding.cartLayout.button.height.toFloat())
                    animSummary(rvCartHeight + labelHeight - 55)
                    _binding.cartLayout.stateIcon.setImageResource(R.drawable.expand)
                }
                false
            }
        }

        _binding.cartLayout.rvCart.adapter = cartAdapter
        _binding.rvList.adapter = listAdapter

        return _binding.root
    }

    private fun animSummary(targetDp: Float) {
        val targetPx = AndroidUtils.dpToPx(requireContext(), targetDp)
        ObjectAnimator.ofFloat(_binding.cartLayout.summary, "translationY", targetPx).apply {
            duration = 300
            start()
        }
    }

    object AndroidUtils {
        /**
         * Converts dp (density-independent pixels) to px (pixels).
         * @param context The context to get resources and display metrics.
         * @param dp The value in density-independent pixels to be converted.
         * @return The equivalent value in pixels.
         */
        fun dpToPx(context: Context, dp: Float): Float {
            val density = context.resources.displayMetrics.density
            return dp * density
        }

        /**
         * Converts px (pixels) to dp (density-independent pixels).
         * @param context The context to get resources and display metrics.
         * @param px The value in pixels to be converted.
         * @return The equivalent value in density-independent pixels.
         */
        fun pxToDp(context: Context, px: Float): Float {
            val density = context.resources.displayMetrics.density
            return px / density
        }

        /**
         * Converts dp (density-independent pixels) to px (pixels) and returns an integer value.
         * @param context The context to get resources and display metrics.
         * @param dp The value in density-independent pixels to be converted.
         * @return The equivalent value in pixels as an integer.
         */
        fun dpToPxInt(context: Context, dp: Float): Int {
            return dpToPx(context, dp).toInt()
        }
    }
}
