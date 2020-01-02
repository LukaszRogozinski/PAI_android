package com.example.pai.features.products.detail


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.pai.MainNavigationDirections
import com.example.pai.R
import com.example.pai.databinding.ProductDetailFragmentBinding
import com.example.pai.utils.Utils
import com.shreyaspatil.MaterialDialog.MaterialDialog
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 */
class ProductDetailFragment : Fragment() {

    private lateinit var binding: ProductDetailFragmentBinding
    private val args by navArgs<ProductDetailFragmentArgs>()
    //var product: Product? = null

    private val viewModel: ProductDetailViewModel by viewModel()

//    private val viewModel: ProductDetailViewModel by lazy {
//        Timber.i("ProductDetailViewModel called")
//        ViewModelProviders.of(
//            this,
//            ProductDetailViewModel.Factory(
//                product
//            )
//        )
//            .get(ProductDetailViewModel::class.java)
//    }

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
        val product = args.product
        viewModel.setProduct(product)

        binding.vm = viewModel
        setObservers()
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.logged_user_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.userDetailFragmentMenu -> {
                val loggedUser = viewModel.getLoggedUser()
                val action = MainNavigationDirections.actionGlobalUserDetailFragment(loggedUser)
                findNavController(this).navigate(action)
                true
            }
            R.id.logout_menu -> {
                Utils.logOutDialog(requireActivity(), viewModel.sessionRepository, this)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setObservers() {
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
                createDialog()
            }
        })
    }

    @SuppressLint("RestrictedApi")
    private fun createDialog() {
        val mDialog: MaterialDialog = MaterialDialog.Builder(requireActivity())
            .setTitle("Delete?")
            .setMessage("Are you sure want to delete this product?")
            .setCancelable(false)
            .setPositiveButton(
                "Delete", R.drawable.ic_delete_24px
            ) { dialogInterface, _ ->
                viewModel.deleteProduct()
                dialogInterface?.dismiss()
            }
            .setNegativeButton(
                "Cancel", R.drawable.ic_close_24px
            ) { dialogInterface, _ ->
                run {
                    dialogInterface?.dismiss()
                    viewModel.showDeleteDialogCanceled()
                }
            }
            .build()

        mDialog.show()
    }
}
