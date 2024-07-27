package io.nyxbit.veraceitalia

import android.animation.ObjectAnimator
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import io.nyxbit.veraceitalia.adapters.ProductsAdapter
import io.nyxbit.veraceitalia.databinding.FragmentProductListBinding
import io.nyxbit.veraceitalia.models.Item


class ProductList : Fragment() {

    private lateinit var _binding : FragmentProductListBinding
    private lateinit var listAdapter : ProductsAdapter
    private var dataset = mutableListOf<Item>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductListBinding.inflate(inflater,container,false)
        listAdapter = ProductsAdapter(dataset){}
        var isCollapsed = false

        dataset.add(Item(name="Lorem Ipsum", price = 199))
        dataset.add(Item(name="Lorem Ipsum Dolor Amet", price = 499))

        _binding.next.setOnClickListener{
            var subtotal = 0
            for(item in dataset){
                subtotal += item.subtotal
            }
            Log.d("Subtotal", subtotal.toString())
        }

        _binding.summary.setOnClickListener {
            isCollapsed = if (isCollapsed){
                animSummary(0f)
                false
            } else{
                animSummary(400f)
                true
            }
        }

        _binding.rvList.adapter = listAdapter
        return _binding.root
    }

    private fun animSummary(pos:Float){
        _binding.summary.setOnClickListener{
            _binding.summary.animate()
                .translationY(AndroidUtils.dpToPx(requireContext(), pos))
                .setDuration(1000)
                .start()
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