package com.example.pai.features.products.detail


import android.annotation.SuppressLint
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
import com.shreyaspatil.MaterialDialog.MaterialDialog
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 */
class ProductDetailFragment : Fragment() {

    private lateinit var binding: ProductDetailFragmentBinding

    var product: Product? = null

    private val viewModel: ProductDetailViewModel by lazy {
        Timber.i("ProductDetailViewModel called")
        ViewModelProviders.of(
            this,
            ProductDetailViewModel.Factory(
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

    @SuppressLint("RestrictedApi")
    private fun setObservers() {

//        viewModel.navigateToEditProduct.observe(viewLifecycleOwner, Observer<Boolean> {
//            if (it) {
//                val action =
//                    ProductDetailFragmentDirections.actionProductDetailFragmentToEditProductFragment(
//                        false
//                    ).setProduct(product)
//                findNavController(this).navigate(action)
//                Timber.i("navigate to edit product screen")
//                viewModel.navigateToEditProductFinish()
//            }
//        })

        viewModel.deleteResponse.observe(viewLifecycleOwner, Observer<Boolean> {
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

        viewModel.showDeleteDialog.observe(viewLifecycleOwner, Observer {
            if (it) {
                val mDialog: MaterialDialog = MaterialDialog.Builder(requireActivity())
                    .setTitle("Delete?")
                    .setMessage("Are you sure want to delete this product?")
                    .setCancelable(true)
                    .setPositiveButton(
                        "Delete", R.drawable.ic_delete_24px
                    ) { dialogInterface, _ ->
                        viewModel.deleteProduct()
                        dialogInterface?.dismiss()
                    }
                    .setNegativeButton(
                        "Cancel", R.drawable.ic_close_24px
                    ) { dialogInterface, _ -> dialogInterface?.dismiss() }
                    .build()

                mDialog.show()
            }
        })
    }

}
