package com.example.pai.features.products.detail


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.example.pai.R
import com.example.pai.databinding.ProductDetailFragmentBinding
import com.example.pai.domain.Product
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 */
class ProductDetailFragment : Fragment() {

    private lateinit var binding: ProductDetailFragmentBinding

    var product: Product? = null

    private val viewModel: ProductDetailViewModel by lazy {
        Timber.i("ProductDetailViewModel called")
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
        Timber.i("onCreateView called")
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.product_detail_fragment,
            container,
            false
        )
        product = ProductDetailFragmentArgs.fromBundle(arguments!!).product
        binding.vm = viewModel
        setObservers()
        return binding.root
    }

    private fun setObservers() {

        viewModel.navigateToEditProduct.observe(this, Observer<Boolean> {
            if (it) {
                val action =
                    ProductDetailFragmentDirections.actionProductDetailFragmentToEditProductFragment(
                        false
                    ).setProduct(product)
                findNavController(this).navigate(action)
                Timber.i("navigate to edit product screen")
                viewModel.navigateToEditProductFinish()
            }
        })

        viewModel.deleteResponse.observe(this, Observer<Boolean> {
            if (it) {
                Timber.i("item deleted")
                val action =
                    ProductDetailFragmentDirections.actionProductDetailFragmentToProductsFragment()
                Toast.makeText(requireContext(), "item deleted", Toast.LENGTH_LONG).show()
                findNavController(this).navigate(action)
                Timber.i("navigate to products fragment")
                viewModel.onDeleteResponseFinish()
            }
        })
    }

}
