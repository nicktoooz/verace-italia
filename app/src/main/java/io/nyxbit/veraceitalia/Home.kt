package io.nyxbit.veraceitalia

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import io.nyxbit.veraceitalia.databinding.FragmentHomeBinding


class Home : Fragment() {

    private lateinit var _binding : FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater,container,false)

        _binding.btnOrderNow.setOnClickListener{
            findNavController().navigate(R.id.action_home2_to_productList)
        }

        return _binding.root
    }

}