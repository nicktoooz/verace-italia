package io.nyxbit.veraceitalia

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import io.nyxbit.veraceitalia.adapters.ReceiptRecyclerAdapter
import io.nyxbit.veraceitalia.databinding.FragmentReceiptBinding
import io.nyxbit.veraceitalia.viewmodels.SelectionViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class Receipt : Fragment() {

    private lateinit var _binding: FragmentReceiptBinding
    private lateinit var selectionViewModel: SelectionViewModel
    private lateinit var receiptAdapter: ReceiptRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        selectionViewModel = ViewModelProvider(requireActivity())[SelectionViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {}
            })
    }

    @SuppressLint("DefaultLocale", "SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReceiptBinding.inflate(inflater, container, false)

        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val currentDate = dateFormat.format(Date())
        _binding.date.text = currentDate

        val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        val currentTime = timeFormat.format(Date())
        _binding.time.text = currentTime

        receiptAdapter = selectionViewModel.list.value?.let { ReceiptRecyclerAdapter(it) }!!
        val taxRate = 0.008f

        val subtotal = selectionViewModel.subtotal.value ?: 0f
        val taxAmount = subtotal * taxRate
        val grandTotal = subtotal + taxAmount
        _binding.subtotal.text = String.format("£ %.2f", subtotal)
        _binding.tax.text = String.format("£ %.2f", taxAmount)
        _binding.grandTotal.text = String.format("£ %.2f", grandTotal)

        _binding.receiptList.adapter = receiptAdapter

        _binding.newOrder.setOnClickListener {
            findNavController().popBackStack(R.id.home2, false)
        }

        return _binding.root
    }
}