package com.example.pai.features.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.pai.R
import com.example.pai.databinding.LoginFragmentBinding
import com.example.pai.features.login.LoginFragmentDirections
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : Fragment() {

    private lateinit var binding: LoginFragmentBinding

    private val viewModel: LoginViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.login_fragment,
            container,
            false
        )
        // Inflate the layout for this fragment

        binding.vm = viewModel

        binding.lifecycleOwner = this

        binding.loginButton.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToProductsFragment())
        }

//        viewModel.eventGameFinish.observe(this, Observer {
//            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomePageFragment())
//            viewModel.onGameFinishComplete()
//        })

        return binding.root
    }

}
