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
import com.example.pai.utils.Utils

/**
 * A simple [Fragment] subclass.
 */
class ProductsFragment : Fragment() {

    private val viewModel: ProductsViewModel by lazy {
        ViewModelProviders.of(this, ProductsViewModel.Factory())
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            Utils.onBackPressedCallback(requireActivity(), requireContext())
        )
    }

    private fun setObservers() {
        viewModel.navigateToSelectedProduct.observe(viewLifecycleOwner, Observer<Product> {
            if (it != null) {
                val action =
                    ProductsFragmentDirections.actionProductsFragmentToProductDetailFragment()
                        .setProduct(it)
                findNavController(this).navigate(action)
                viewModel.navigateToSelectedProductDone()
            }
        })

//        viewModel.navigateToEditProduct.observe(viewLifecycleOwner, Observer<Boolean> {
//            if (it) {
//                val action =
//                    ProductsFragmentDirections.actionProductsFragmentToEditProductFragment(true)
//                findNavController(this).navigate(action)
//                viewModel.navigateToEditProductFinish()
//            }
//        })

        viewModel.eventNetworkError.observe(viewLifecycleOwner, Observer<Boolean> {
            if (it) {
                onNetworkError()
            }
        })
    }

    private fun onNetworkError() {
        if (!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }
}

