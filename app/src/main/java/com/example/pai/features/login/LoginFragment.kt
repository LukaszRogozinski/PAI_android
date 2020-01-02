package com.example.pai.features.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.example.pai.R
import com.example.pai.databinding.LoginFragmentBinding
import com.example.pai.utils.Utils
import com.example.pai.utils.hideKeyboard
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class LoginFragment : Fragment() {

    private lateinit var binding: LoginFragmentBinding
    private val viewModel: LoginViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Timber.i("onCreateView called")
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.login_fragment,
            container,
            false
        )
        binding.vm = viewModel
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
        viewModel.navigateToProducts.observe(viewLifecycleOwner, Observer<Boolean> {
            if (it) {
                val view = requireActivity().findViewById<View>(android.R.id.content)
                view.hideKeyboard()
                Timber.i("Navigate to products fragment called")
                val action = LoginFragmentDirections.actionLoginFragmentToProductsFragment()
                findNavController(this).navigate(action)
                viewModel.onNavigateToProductsComplete()
            }
        })

        viewModel.showErrorMessage.observe(viewLifecycleOwner, Observer {
            if(!it.isNullOrEmpty()){
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            }
        })
    }
}
