package com.example.pai.features.products


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders

import com.example.pai.R
import com.example.pai.databinding.ProductsFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class ProductsFragment : Fragment() {

    private val viewModel: ProductsViewModel by lazy {
        val activity = requireNotNull(this.activity) {
        }
        ViewModelProviders.of(this, ProductsViewModel.Factory(activity.application))
            .get(ProductsViewModel::class.java)
    }

    private lateinit var binding: ProductsFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.products_fragment,
            container,
            false
        )
        viewModel
        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.loadAllProducts()
        // Inflate the layout for this fragment
        return binding.root
    }


}
