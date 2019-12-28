package com.example.pai.features.products


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment.findNavController

import com.example.pai.R
import com.example.pai.databinding.ProductsFragmentBinding
import com.example.pai.domain.Product
import com.example.pai.utils.Utils
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class ProductsFragment : Fragment() {

    private val viewModel: ProductsViewModel by viewModel()
//    {
//        ViewModelProviders.of(this, ProductsViewModel.Factory())
//            .get(ProductsViewModel::class.java)
//    }
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
                val action = ProductsFragmentDirections.actionProductsFragmentToUserDetailFragment(loggedUser.user)
                findNavController(this).navigate(action)
                true
            }
            R.id.logout_menu -> {
                Utils.logOutDialog(requireActivity(), viewModel.sessionRepository)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            Utils.onBackPressed(requireActivity(), viewModel.sessionRepository)
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

