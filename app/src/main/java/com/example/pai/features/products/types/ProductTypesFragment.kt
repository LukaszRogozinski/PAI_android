package com.example.pai.features.products.types


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment

import com.example.pai.R
import com.example.pai.databinding.ProductTypesFragmentBinding
import com.example.pai.utils.Utils
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class ProductTypesFragment : Fragment() {

    private lateinit var binding: ProductTypesFragmentBinding

    private val viewModel: ProductTypesViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.product_types_fragment,
            container,
            false
        )
        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        // Inflate the layout for this fragment

        viewModel.eventNetworkError.observe(viewLifecycleOwner, Observer<Boolean> {
            if (it) {
                onNetworkError()
            }
        })
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
                val action = ProductTypesFragmentDirections.actionProductTypesFragmentToUserDetailFragment(loggedUser.user)
                NavHostFragment.findNavController(this).navigate(action)
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

    private fun onNetworkError() {
        if (!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }
}
