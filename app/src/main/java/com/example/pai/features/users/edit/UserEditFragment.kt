package com.example.pai.features.users.edit


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import org.koin.androidx.viewmodel.ext.android.viewModel

import com.example.pai.R
import com.example.pai.databinding.UserEditFragmentBinding

/**
 * A simple [Fragment] subclass.
 */
class UserEditFragment : Fragment() {

    private lateinit var binding: UserEditFragmentBinding
    private val args by navArgs<UserEditFragmentArgs>()

    private val viewModel: UserEditViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.user_edit_fragment,
            container,
            false
        )
        val user = args.user
        viewModel.user = user
        binding.vm = viewModel

        // Inflate the layout for this fragment
        return binding.root
    }


}
