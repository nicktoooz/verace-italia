package io.nyxbit.veraceitalia

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.nyxbit.veraceitalia.adapters.ReceiptRecyclerAdapter
import io.nyxbit.veraceitalia.databinding.FragmentReceiptBinding
import io.nyxbit.veraceitalia.viewmodels.CartViewModel


class Receipt : Fragment() {

    private lateinit var _binding : FragmentReceiptBinding
    private lateinit var cartViewModel : CartViewModel
    private lateinit var receiptAdapter : ReceiptRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cartViewModel = ViewModelProvider(requireActivity())[CartViewModel::class.java]

    }

    @SuppressLint("DefaultLocale")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReceiptBinding.inflate(inflater,container,false)

        receiptAdapter = cartViewModel.list.value?.let { ReceiptRecyclerAdapter(it){} }!!
        val taxRate = 0.008

        val subtotal = cartViewModel.subtotal.value ?: 0

        val taxAmount = subtotal * taxRate
        val grandTotal = subtotal + taxAmount
        _binding.subtotal.text = subtotal.toString()
        _binding.tax.text = String.format("%.2f", taxAmount)
        _binding.grandTotal.text = String.format("%.2f", grandTotal)


        _binding.receiptList.adapter = receiptAdapter
        return _binding.root
    }
}