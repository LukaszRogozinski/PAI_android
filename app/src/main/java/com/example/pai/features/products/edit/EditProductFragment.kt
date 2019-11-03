package com.example.pai.features.products.edit


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil

import com.example.pai.R
import com.example.pai.databinding.EditProductFragmentBinding

/**
 * A simple [Fragment] subclass.
 */
class EditProductFragment : Fragment() {

    private lateinit var binding: EditProductFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.edit_product_fragment,
            container,
            false
        )

        val categories = arrayListOf("Automobile","Travel","Personal","Education","Computers","Business Services" )

        val dataAdapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, categories)
        binding.warehouseTypeSpinner.adapter = dataAdapter
        // Inflate the layout for this fragment
        return binding.root
    }


}
