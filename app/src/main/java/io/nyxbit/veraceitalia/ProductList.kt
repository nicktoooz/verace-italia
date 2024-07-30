package io.nyxbit.veraceitalia

import CartRecyclerAdapter
import android.graphics.Canvas
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import io.nyxbit.veraceitalia.adapters.ItemRecyclerAdapter
import io.nyxbit.veraceitalia.databinding.FragmentProductListBinding
import io.nyxbit.veraceitalia.models.Item
import io.nyxbit.veraceitalia.viewmodels.CartViewModel
import kotlin.math.abs

class ProductList : Fragment() {

    private lateinit var _binding: FragmentProductListBinding
    private lateinit var listAdapter: ItemRecyclerAdapter
    private lateinit var cartAdapter: CartRecyclerAdapter
    private lateinit var cartViewModel: CartViewModel
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<CardView>
    private var dataset = mutableListOf<Item>()
    private var isCollapsed = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cartViewModel = ViewModelProvider(requireActivity()).get(CartViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    isCollapsed = if (!isCollapsed) {
                        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                        true
                    } else {
                        requireActivity().supportFragmentManager.popBackStack()
                        false
                    }
                }
            })
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductListBinding.inflate(inflater, container, false)
        dataset.clear()
        cartViewModel.clear()

        setupBottomSheetBehavior()
        setupRecyclerViewAdapters()
        setupCartSwipeController()
        setupObservers()

        dataset.add(Item(name = "Lorem Ipsum Dolor Amet", price = 15.99f))
        dataset.add(Item(name = "Tiramisu", price = 8.75f, image = R.drawable.menu_tiramisu))
        dataset.add(Item(name = "Caprese Salad", price = 12.50f))
        dataset.add(Item(name = "Spaghetti Carbonara", price = 18.25f))
        dataset.add(Item(name = "Margherita Pizza", price = 14.80f))
        dataset.add(Item(name = "Osso Buco", price = 22.40f))
        dataset.add(Item(name = "Panna Cotta", price = 9.90f))
        dataset.add(Item(name = "Bruschetta", price = 10.30f))
        dataset.add(Item(name = "Lasagna", price = 19.60f))
        dataset.add(Item(name = "Risotto al Funghi", price = 17.75f))
        dataset.add(Item(name = "Gnocchi", price = 16.45f))
        dataset.add(Item(name = "Frittura Mista", price = 20.20f))

        _binding.next.setOnClickListener {
            if (cartViewModel.list.value?.isEmpty()!!)
                Toast.makeText(requireContext(), "Pick some dishes first", Toast.LENGTH_LONG).show()
            else
                findNavController().navigate(R.id.action_productList_to_receipt2)
        }

        _binding.button.setOnClickListener {
            isCollapsed = if (isCollapsed) {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                false
            } else {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                true
            }
        }

        return _binding.root
    }

    private fun setupObservers() {
        cartViewModel.subtotal.observe(viewLifecycleOwner) {
            _binding.subtotal.text = if (it > 0) String.format("£ %.2f", it) else "£ ----"
        }

        cartViewModel.list.observe(viewLifecycleOwner) { items ->
            cartAdapter.updateItems(items)
            listAdapter.notifyDataSetChanged()
        }

    }

    private fun setupRecyclerViewAdapters() {
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
            listAdapter.notifyDataSetChanged()
        })

        cartAdapter = CartRecyclerAdapter {}
        _binding.rvCart.adapter = cartAdapter
        _binding.rvList.adapter = listAdapter
    }

    private fun setupBottomSheetBehavior() {
        bottomSheetBehavior = BottomSheetBehavior.from(_binding.summary)

        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        isCollapsed = false
                    }

                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        isCollapsed = true
                    }

                    else -> {}
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                _binding.stateIcon.rotation = slideOffset * 180
            }
        })
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    private fun setupCartSwipeController() {
        val controller = ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = cartAdapter.dataset.getOrNull(position)
                    item?.let { it ->
                        cartViewModel.remove(it)
                        val index = listAdapter.dataset.indexOfFirst { it.name == item.name }
                        if (index >= 0) {
                            val listItem = listAdapter.dataset[index]
                            listItem.quantity = 0
                            listAdapter.notifyItemChanged(index)
                        }
                    }
                }
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                val alpha = 1.0f - (abs(dX) / recyclerView.width) * 1.7f
                viewHolder.itemView.alpha = alpha.coerceAtLeast(0.0f)
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }

            override fun clearView(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ) {
                super.clearView(recyclerView, viewHolder)
                viewHolder.itemView.alpha = 1.0f
            }
        })
        controller.attachToRecyclerView(_binding.rvCart)
    }
}
