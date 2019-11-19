package com.example.pai.features.users


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders

import com.example.pai.R
import com.example.pai.databinding.UsersFragmentBinding

/**
 * A simple [Fragment] subclass.
 */
class UsersFragment : Fragment() {

    private lateinit var binding: UsersFragmentBinding

    private val viewModel: UsersViewModel by lazy {
        val activity = requireNotNull(this.activity) {
        }
        ViewModelProviders.of(this, UsersViewModel.Factory(activity.application))
            .get(UsersViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.users_fragment,
            container,
            false
        )
        binding.vm = viewModel
        // Inflate the layout for this fragment
        return binding.root
    }

//    override fun onDestroy() {
//        super.onDestroy()
//        val callback = requireActivity().onBackPressedDispatcher.addCallback(this)
//    }

//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        val callback = requireActivity().onBackPressedDispatcher.addCallback(this)
//        // requireActivity().onBackPressedDispatcher.addCallback(this)
//    }



//    override fun onDestroy() {
//        super.onDestroy()
//        activity?.finish()
//    }
}

private fun OnBackPressedDispatcher.addCallback(usersFragment: UsersFragment) {
    Log.e("TAG","onbackpressedDispatcher")
    usersFragment.requireActivity().finish()
}
