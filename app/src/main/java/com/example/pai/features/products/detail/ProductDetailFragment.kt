package com.example.pai.features.products.detail


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.pai.R
import com.example.pai.databinding.ProductDetailFragmentBinding
import com.example.pai.domain.Product

/**
 * A simple [Fragment] subclass.
 */
class ProductDetailFragment : Fragment() {

    private lateinit var binding: ProductDetailFragmentBinding

    var product: Product? = null

    private val viewModel: ProductDetailViewModel by lazy {
        val activity = requireNotNull(this.activity)
        ViewModelProviders.of(
            this,
            ProductDetailViewModel.Factory(
                activity.application,
                product
            )
        )
            .get(ProductDetailViewModel::class.java)
    }

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
        product = ProductDetailFragmentArgs.fromBundle(arguments!!).product
        binding.vm = viewModel
        setOnClickListeners()
        return binding.root
    }

    private fun setOnClickListeners() {
        binding.saveButton.setOnClickListener {
            val action =
                ProductDetailFragmentDirections.actionProductDetailFragmentToEditProductFragment(
                    false
                ).setProduct(product)
            findNavController().navigate(action)
        }

        binding.deleteButton.setOnClickListener {
            viewModel.deleteProduct()
        }
    }
}
