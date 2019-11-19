package com.example.pai.features.products


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment.findNavController

import com.example.pai.R
import com.example.pai.databinding.ProductsFragmentBinding
import com.example.pai.domain.Product

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
        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setObservers()

        return binding.root
    }

    private fun setObservers() {

        viewModel.navigateToSelectedProduct.observe(this, Observer<Product> {
            if (it != null) {
                val action =
                    ProductsFragmentDirections.actionProductsFragmentToProductDetailFragment()
                        .setProduct(it)
                findNavController(this).navigate(action)
                viewModel.navigateToSelectedProductDone()
            }
        })

        viewModel.navigateToEditProduct.observe(this, Observer<Boolean> {
            if (it) {
                val action =
                    ProductsFragmentDirections.actionProductsFragmentToEditProductFragment(true)
                findNavController(this).navigate(action)
                viewModel.navigateToEditProductFinish()
            }
        })

        viewModel.eventNetworkError.observe(this, Observer<Boolean> {
            if (it) {
                onNetworkError()
            }
        })
    }

    fun onNetworkError() {
        if (!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }
}

