package com.example.pai.features.products.detail


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs

import com.example.pai.R
import com.example.pai.databinding.ProductDetailFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class ProductDetailFragment : Fragment() {

    private lateinit var binding: ProductDetailFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.product_detail_fragment,
            container,
            false
        )

        val product = ProductDetailFragmentArgs.fromBundle(arguments!!).product
        val viewModelFactory = ProductDetailViewModel.Factory(product)
        binding.vm = ViewModelProviders.of(this, viewModelFactory).get(ProductDetailViewModel::class.java)
        // Inflate the layout for this fragment
        return binding.root
    }
}
