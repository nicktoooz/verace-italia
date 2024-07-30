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
import androidx.navigation.fragment.findNavController
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
        val taxRate = 0.008f

        val subtotal = cartViewModel.subtotal.value ?: 0f

        val taxAmount = subtotal * taxRate
        val grandTotal = subtotal + taxAmount
        _binding.subtotal.text = String.format("£ %.2f", subtotal)
        _binding.tax.text = String.format("£ %.2f", taxAmount)
        _binding.grandTotal.text = String.format("£ %.2f", grandTotal)


        _binding.receiptList.adapter = receiptAdapter

        _binding.newOrder.setOnClickListener{
            findNavController().popBackStack(R.id.home2, false)
        }
        return _binding.root
    }
}