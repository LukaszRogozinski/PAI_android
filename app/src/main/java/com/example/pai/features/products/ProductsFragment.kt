package com.example.pai.features.products


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController

import com.example.pai.R
import com.example.pai.databinding.ProductsFragmentBinding
import com.example.pai.domain.Product
import com.example.pai.features.products.detail.ProductDetailFragmentArgs
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class ProductsFragment : Fragment(), ProductsView {

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

        setOnClickListeners()

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel.view = this
    }

    override fun onStop() {
        super.onStop()
        viewModel.view = null
    }

    private fun setOnClickListeners() {
        binding.floatingActionButton.setOnClickListener {
            val action = ProductsFragmentDirections.actionProductsFragmentToEditProductFragment(true)
            findNavController().navigate(action)
        }
    }

    override fun navigateToProductDetails(product: Product) {
        val bundle = ProductDetailFragmentArgs.Builder()
            .setProduct(product)
            .build().toBundle()
        findNavController().navigate(R.id.productDetailFragment, bundle)
    }
}

interface ProductsView {
    fun navigateToProductDetails(product: Product)
}

interface OnProductClickedListener {
    fun onProductClicked(product: Product)
}
