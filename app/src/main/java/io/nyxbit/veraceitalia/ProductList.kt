package io.nyxbit.veraceitalia

import android.graphics.Canvas
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import io.nyxbit.veraceitalia.adapters.ItemRecyclerAdapter
import io.nyxbit.veraceitalia.adapters.SelectionRecyclerAdapter
import io.nyxbit.veraceitalia.databinding.FragmentProductListBinding
import io.nyxbit.veraceitalia.models.Item
import io.nyxbit.veraceitalia.utils.AndroidUtils
import io.nyxbit.veraceitalia.viewmodels.SelectionViewModel
import java.util.Locale
import kotlin.math.abs

class ProductList : Fragment() {

    private lateinit var _binding: FragmentProductListBinding
    private lateinit var listAdapter: ItemRecyclerAdapter
    private lateinit var cartAdapter: SelectionRecyclerAdapter
    private lateinit var selectionViewModel: SelectionViewModel
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<CardView>
    private var dataset = mutableListOf<Item>()
    private var isCollapsed = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        selectionViewModel =
            ViewModelProvider(requireActivity()).get(SelectionViewModel::class.java)
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
        selectionViewModel.clear()

        dataset.add(Item(name = "Panna Cotta", price = 9.90f, image = R.drawable.menu_panna_cotta))
        dataset.add(Item(name = "Tiramisu", price = 8.75f, image = R.drawable.menu_tiramisu))
        dataset.add(Item(name = "Caprese Salad", price = 12.50f, image = R.drawable.menu_caprese_salad))
        dataset.add(Item(name = "Spaghetti Carbonara", price = 18.25f, image = R.drawable.menu_spaghetti_carbonara))
        dataset.add(Item(name = "Margherita Pizza", price = 14.80f, image = R.drawable.menu_margherita_pizza))
        dataset.add(Item(name = "Osso Buco", price = 22.40f, image = R.drawable.menu_osso_buco))
        dataset.add(Item(name = "Bruschetta", price = 10.30f, image = R.drawable.menu_bruschetta))
        dataset.add(Item(name = "Pasta ’Ncasciata", price = 15.99f, image = R.drawable.menu_pasta_ncasciata))
        dataset.add(Item(name = "Lasagna", price = 19.60f, image = R.drawable.menu_lasagna))
        dataset.add(Item(name = "Risotto al Funghi", price = 17.75f, image = R.drawable.menu_risotto_al_funghi))
        dataset.add(Item(name = "Gnocchi", price = 16.45f, image = R.drawable.menu_gnocchi))
        dataset.add(Item(name = "Frittura Mista", price = 20.20f, image = R.drawable.menu_frittura_mista))
        dataset.add(Item(name = "Agnolotti del Plin", price = 14.20f, image = R.drawable.menu_agnolotti_del_plin))

        setupBottomSheetBehavior()
        setupRecyclerViewAdapters()
        setupCartSwipeController()
        setupObservers()


        val randint = (0..<dataset.size).random()
        val temp = dataset[0]
        dataset[0] = dataset[randint]
        dataset[randint] = temp

        _binding.next.setOnClickListener {
            if (selectionViewModel.list.value?.isEmpty()!!) Toast.makeText(
                requireContext(), "Select some dishes first", Toast.LENGTH_LONG
            ).show()
            else isCollapsed = if (isCollapsed) {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                false
            } else {
                findNavController().navigate(R.id.action_productList_to_receipt2)
                true
            }
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
        selectionViewModel.subtotal.observe(viewLifecycleOwner) {
            if (it > 0) {
                _binding.rvSelection.visibility = View.VISIBLE
                _binding.emptySelection.visibility = View.GONE

            } else {
                _binding.rvSelection.visibility = View.GONE
                _binding.emptySelection.visibility = View.VISIBLE
            }
            _binding.subtotal.text =
                if (it > 0) String.format(Locale.UK, "£ %.2f", it) else "£ ----"
        }

        selectionViewModel.list.observe(viewLifecycleOwner) { items ->
            cartAdapter.updateItems(items)
            listAdapter.notifyDataSetChanged()
        }
    }

    private fun setupRecyclerViewAdapters() {
        listAdapter = ItemRecyclerAdapter(requireContext(), dataset) { item ->
            if (item.quantity > 0) {
                if (!selectionViewModel.exists(item)) {
                    selectionViewModel.add(item)
                } else {
                    selectionViewModel.replace(item, item)
                }
            } else {
                selectionViewModel.remove(item)
            }
            listAdapter.notifyDataSetChanged()
        }


        cartAdapter = SelectionRecyclerAdapter()
        val gridLayoutManager = GridLayoutManager(requireContext(), 2).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (position == 0) 2 else 1
                }
            }
        }

        _binding.rvSelection.adapter = cartAdapter
        _binding.rvList.adapter = listAdapter
        _binding.rvList.layoutManager = gridLayoutManager
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
                _binding.mainLayout.foreground =
                    ColorDrawable(AndroidUtils.getColorWithAlpha((slideOffset * 0.50 * 255).toInt()))
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
                    val item = cartAdapter.getDataset().getOrNull(position)
                    item?.let { it ->
                        selectionViewModel.remove(it)
                        val index = listAdapter.getDataset().indexOfFirst { it.name == item.name }
                        if (index >= 0) {
                            val listItem = listAdapter.getDataset()[index]
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
                    c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive
                )
            }

            override fun clearView(
                recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder
            ) {
                super.clearView(recyclerView, viewHolder)
                viewHolder.itemView.alpha = 1.0f
            }
        })
        controller.attachToRecyclerView(_binding.rvSelection)
    }
}
